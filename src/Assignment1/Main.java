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

    public static void XHTML2JSON(String inputPath, String outputPath) throws IOException {
        Parser parser = new AutoDetectParser();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(inputPath)));
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputPath)));
            // TODO Implement the JSONTableContentHandler
            XPathParser xhtmlParser = new XPathParser("xhtml", XHTMLContentHandler.XHTML);
            Matcher divContentMatcher = xhtmlParser
                    .parse("/xhtml:html/xhtml:body/descendant::node()");
            ContentHandler handler = new MatchingContentHandler(new JSONTableContentHandler(
                    outputStream), divContentMatcher);
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
    
    public static void main(String[] args) {
        String tsvPath = "dataset/computrabajo-ar-20121106.tsv";
        String xhtmlPath = "dataset/1.html";
        String jsonPath = "dataset/1.json";
        try {
            TSV2XHTML(tsvPath, xhtmlPath);
            XHTML2JSON(xhtmlPath, jsonPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
