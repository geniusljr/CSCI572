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
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.xpath.Matcher;
import org.apache.tika.sax.xpath.MatchingContentHandler;
import org.apache.tika.sax.xpath.XPathParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class Xhtml2Json {
	public static void main(String[] args) throws IOException, SAXException, TikaException {
		Parser parser = new AutoDetectParser(); 
		
		String inputPath = "dataset/index.html";
		String outputPath = "dataset/index.json";
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			
			inputStream = new BufferedInputStream(new FileInputStream(new File(inputPath)));
			outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputPath)));
			// TODO Implement the JSONTableContentHandler
	        XPathParser xhtmlParser = new XPathParser("xhtml", XHTMLContentHandler.XHTML);
	        Matcher divContentMatcher = xhtmlParser.parse(
	                "/xhtml:html/xhtml:body/descendant::node()");        
	        ContentHandler handler = new MatchingContentHandler(new JSONTableContentHandler(outputStream), divContentMatcher);
			// ContentHandler handler = new JSONTableContentHandler();
			// ContentHandler handler = new BodyContentHandler(outputStream);
			Metadata metadata = new Metadata();
			
			parser.parse(inputStream, handler, metadata, new ParseContext());

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			inputStream.close();
			outputStream.close();
		}
		
	}
}
