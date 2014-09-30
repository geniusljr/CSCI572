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
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws IOException {
        Parser parser = new TSVParser();

        String inputPath = "/Users/lijiarui/Documents/workspace/CSCI572/dataset/computrabajo-ar-20121106.tsv";
        String outputPath = "/Users/lijiarui/Documents/workspace/CSCI572/dataset/a.xml";
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            inputStream = new BufferedInputStream(new FileInputStream(new File(inputPath)));
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputPath)));

            ContentHandler handler = new ToXMLContentHandler(outputStream, "utf-8");
            Metadata metadata = new Metadata();

            parser.parse(inputStream, handler, metadata, new ParseContext());

        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TikaException e) {
            // TODO Auto-generated catch block
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
}
