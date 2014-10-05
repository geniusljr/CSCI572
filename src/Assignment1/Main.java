package Assignment1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.xpath.Matcher;
import org.apache.tika.sax.xpath.MatchingContentHandler;
import org.apache.tika.sax.xpath.XPathParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import FileTransform.JSONTableContentHandler;
import FileTransform.TSVParser;

public class Main {

    public static void TSV2XHTML(String inputPath, String outputPath) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputPath)));
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputPath)));
            ContentHandler handler = new ToXMLContentHandler(outputStream, "utf-8");
            Metadata metadata = new Metadata();
            Parser parser = new TSVParser();
            parser.parse(inputStream, handler, metadata, new ParseContext());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static void XHTML2JSON(String inputPath, String outputPath, String filename)
            throws IOException {
        Parser parser = new AutoDetectParser();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputPath)));
            // outputStream = new BufferedOutputStream(new FileOutputStream(new
            // File(outputPath)));
            // Implement the JSONTableContentHandler
            XPathParser xhtmlParser = new XPathParser("xhtml", XHTMLContentHandler.XHTML);
            Matcher divContentMatcher = xhtmlParser
                    .parse("/xhtml:html/xhtml:body/descendant::node()");
            ContentHandler handler = new MatchingContentHandler(new JSONTableContentHandler(
                    outputPath), divContentMatcher);
            Metadata metadata = new Metadata();
            parser.parse(inputStream, handler, metadata, new ParseContext());

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static void TSV2JSON(String datasetDir) throws IOException {
        File tsvDir = new File(datasetDir + "/tsv/");
        File[] tsvFiles = tsvDir.listFiles();
        File xhtmlDir = new File(datasetDir + "/xhtml/");
        String jsonDir = datasetDir + "/json/";
        for (int i = 0; i < tsvFiles.length; i++) {
            String xhtmlFilePath = (xhtmlDir.getAbsolutePath() + "/" + tsvFiles[i].getName() + ".xhtml");
            System.out.println(xhtmlFilePath);
            TSV2XHTML(tsvFiles[i].getAbsolutePath(), xhtmlFilePath);
            XHTML2JSON(xhtmlFilePath, jsonDir, tsvFiles[i].getName());
        }
    }

    public static void main(String[] args) throws SAXException, TikaException, IOException {
        TSV2JSON("dataset");
    }

}
