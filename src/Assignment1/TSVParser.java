package Assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TSVParser extends AbstractParser {

    /**
	 * 
	 */
    private static final long           serialVersionUID  = 2188310814783290636L;

    private static final Set<MediaType> SUPPORTED_TYPES   = Collections.singleton(MediaType
                                                                  .application("tsv"));
    public static final String          TSV_MIME_TYPE     = "application/tsv";
    public static final String          TSV_MIME_ENCODING = "utf-8";
    public static final String[]        tsvAttributes     = { "postedDate", "location",
            "department", "title", "salary", "start", "duration", "jobtype", "applications",
            "company", "contactPerson", "phoneNumber", "faxNumber", "location", "latitude",
            "longitude", "firstSeenDate", "url", "lastSeenDate" };

    @Override
    public Set<MediaType> getSupportedTypes(ParseContext arg0) {
        return SUPPORTED_TYPES;
    }

    @Override
    public void parse(InputStream istream, ContentHandler handler, Metadata metadata,
            ParseContext context) throws IOException, SAXException, TikaException {

        metadata.set(Metadata.CONTENT_TYPE, TSV_MIME_TYPE);
        metadata.set(Metadata.CONTENT_ENCODING, TSV_MIME_ENCODING);
        BufferedReader reader = new BufferedReader(new InputStreamReader(istream));

        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);

        xhtml.startDocument();
        xhtml.startElement("table");

        // table titles
        xhtml.startElement("tr");
        for (int i = 0; i < tsvAttributes.length; i++) {
            xhtml.startElement("th");
            xhtml.characters(tsvAttributes[i]);
            xhtml.endElement("th");
        }
        xhtml.endElement("tr");

        // table content
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] attributeValues = line.split("\t");
            xhtml.startElement("tr");
            for (int i = 0; i < tsvAttributes.length; i++) {
                xhtml.startElement("td");
                // There is one redundant tab after 4 tabs, need to be adjust.
                xhtml.characters(i <= 3 ? attributeValues[i] : attributeValues[i + 1]);
                xhtml.endElement("td");
            }
            xhtml.endElement("tr");
        }

        xhtml.endElement("table");
        xhtml.endDocument();
    }
}
