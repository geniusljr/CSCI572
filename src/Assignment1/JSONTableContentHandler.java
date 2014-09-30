package Assignment1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.ToTextContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.xpath.Matcher;
import org.apache.tika.sax.xpath.XPathParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONTableContentHandler extends ToTextContentHandler{
	
	JsonArray jArr;
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> values = new ArrayList<String>();
	Boolean isHead = false;
	Boolean isData = false;
	
    /**
     * Creates an JSON serializer that writes to the given byte stream
     *
     * @param stream output stream
     */
    public JSONTableContentHandler(OutputStream stream){
        super(stream);
    }
    public JSONTableContentHandler() {
        super();
    }
    
    /**
     * Create Json Object
     */
    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes atts)
            throws SAXException {
    	switch (localName){
    		case "table":
    			jArr = new JsonArray();
    			break;
    		case "tr":
    			isHead = false;
    			isData = false;
    			break;
    		case "th":
    			isHead = true;
    			break;
    		case "td":
    			isData = true;
    			break;
    		default:
    			isHead = false;
    			isData = false;
    			break;
    	}
    }
    

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	switch (localName){
			case "table":
				// end of the table
				write(jArr.toString());
				break;
			case "tr":
				// end of a line (th or td)
				// write(names.toString());
				// if end of td: 1. convert all values to curObj; 2. add to jArr
				if(values.size()>0){
					JsonObject curObj = Convert2Json(names, values);
					jArr.add(curObj);
					values.clear();
				}else{
					isHead = false;
				}
				break;
			case "th":
				// end of a header
				break;
			case "td":
				// end of a data
				break;
			default:
				break;
    	}
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    	// get current data
    	String curStr = String.valueOf(ch, start, length);
    	if(isHead){
    		names.add(curStr);
    		isHead = false;
    	}else if(isData){
    		values.add(curStr);
    		isData = false;
    	}
    }
    
    /**
     * Writes the given string of character as-is.
     *
     * @param string string of character to be written
     * @throws SAXException if the character string could not be written
     */
    protected void write(char ch) throws SAXException {
        super.characters(new char[] { ch }, 0, 1);
    }
    protected void write(String string) throws SAXException {
        super.characters(string.toCharArray(), 0, string.length());
    }
    
    protected JsonObject Convert2Json(ArrayList<String> names, ArrayList<String> values){
    	JsonObject curObj = new JsonObject();
    	for(int i=0; i<names.size(); i++){
    		curObj.addProperty(names.get(i), values.get(i));
    	}
    	return curObj;
    }
}
