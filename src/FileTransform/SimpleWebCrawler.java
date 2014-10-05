package FileTransform;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class SimpleWebCrawler {
	public Queue<String> urlQueue;
	private String startUrl;
	
	public SimpleWebCrawler(String start) {
		urlQueue = new LinkedList<String>();
		startUrl = start;
	}
	
	public void run() throws IOException, SAXException, TikaException {

	}
	
	public Queue<String> getNewLink() throws IOException, SAXException, TikaException{
		URL url = new URL(startUrl);
        InputStream input = url.openStream();
        LinkContentHandler linkHandler = new LinkContentHandler();
        ContentHandler textHandler = new BodyContentHandler();
        ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
        TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        Parser parser = new AutoDetectParser();
        parser.parse(input, teeHandler, metadata, parseContext);
        // Get all urls and put into queue
        for (Link link: linkHandler.getLinks()) {
        		urlQueue.add(link.getUri());
        }
        return urlQueue;
	}
}
