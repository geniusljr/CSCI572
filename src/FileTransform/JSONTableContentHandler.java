package FileTransform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.tika.sax.ToTextContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import Deduplication.DeduplicatorExact;
import EmploymentJsonUtil.EmploymentJsonObject;

import com.google.gson.JsonObject;

public class JSONTableContentHandler extends ToTextContentHandler {

    // private JsonArray jsonArray;
    private ArrayList<String> names       = new ArrayList<String>();
    private ArrayList<String> values      = new ArrayList<String>();
    private boolean           headStarted = false;
    private boolean           dataStarted = false;
    private int               count       = 0;
    private String            outputPath;

    /**
     * Creates an JSON serializer that writes to the given byte stream
     *
     * @param stream
     *            output stream
     */
    public JSONTableContentHandler(OutputStream stream, String outputFolder, String filename) {
        super(stream);
        this.outputPath = outputFolder + filename;
    }

    public JSONTableContentHandler(String outputFolder, String filename) {
        super();
        this.outputPath = outputFolder + filename;
    }

    /**
     * Create Json Object
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts)
            throws SAXException {
        if (localName.equals("table")) {
            // jsonArray = new JsonArray();
        } else if (localName.equals("th")) {
            headStarted = true;
        } else if (localName.equals("td")) {
            dataStarted = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("table")) {
            System.out.println("After deduplication: " + DeduplicatorExact.primaryKeySet.size()
                    + " --- " + "Before: " + DeduplicatorExact.totalEmploymentNumber);
        } else if (localName.equals("tr")) {
            if (values.size() > 0) {
                DeduplicatorExact.totalEmploymentNumber++;
                JsonObject curObj = convert2Json(names, values);
                EmploymentJsonObject curEmploymentObj = new EmploymentJsonObject(curObj);
                int primaryKeyHash = curEmploymentObj.hashCode();
                if (!DeduplicatorExact.primaryKeySet.contains(primaryKeyHash)) {
                    // output curObj to json file
                    // count++;
                    DeduplicatorExact.primaryKeySet.add(primaryKeyHash);
                }

                values.clear();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // get current data
        String curStr = String.valueOf(ch, start, length);
        // For tags like <td \>, endElement will be called before characters,
        // so closing the headStarted and dataStarted should be finished within
        // this function instead of endElement.
        if (headStarted) {
            names.add(curStr);
            headStarted = false;
        } else if (dataStarted) {
            values.add(curStr);
            dataStarted = false;
        }
    }

    protected void write(char ch) throws SAXException {
        super.characters(new char[] { ch }, 0, 1);
    }

    /**
     * Writes the given string of character as-is.
     *
     * @param string
     *            string of character to be written
     * @throws SAXException
     *             if the character string could not be written
     */
    protected void write(String string) throws SAXException {
        super.characters(string.toCharArray(), 0, string.length());
    }

    protected JsonObject convert2Json(ArrayList<String> names, ArrayList<String> values) {
        JsonObject curObj = new JsonObject();
        for (int i = 0; i < names.size(); i++) {
            curObj.addProperty(names.get(i), values.get(i));
        }
        return curObj;
    }

    protected void write2Json(EmploymentJsonObject employmentJson) throws IOException {
        // use file's name
        // TODO when there is "/" in the primaryKeyName, then it will be parsed
        // as a directory, and then there will be exception.
        String outputFolderPath = outputPath + employmentJson.getPrimaryKeyName();
        File file = new File(outputFolderPath);
        if (!file.exists()) {
            file.mkdir();
        }
        BufferedWriter output = new BufferedWriter(new FileWriter(new File(outputFolderPath + "/"
                + count + ".json")));
        output.flush();
        output.write(employmentJson.toString().toCharArray());
        output.close();
    }
}
