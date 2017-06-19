//Web-Technologie 2017 - Jula (115167), Jana (115753), Christopher (114602), Josef (115850)
import java.io.File;
import java.util.GregorianCalendar;
import jaxb.Feed;
import jaxb.Entry;
import jaxb.Person;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.OutputStream;
import java.io.FileOutputStream;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;


/**
 * Manages access to the feed and holds an index of it.
 */
public class FeedModel {

    /* MEMBER */
    protected Feed feed_;

    /**
     * Contructor
     */
    public FeedModel() throws FileNotFoundException, JAXBException {
        this.feed_ = load("feed.xml");
        // feed_ = (Feed) JAXBContext.newInstance(Feed.class).createUnmarshaller().unmarshal(new StringReader(z));
    }

    public Feed getFeed() {
        return this.feed_;
    }

    /**
     * The constant file reference to the XML instance document.
     * Change if needed.
     */
    private final static File FEED_FILE = new File("feed.xml");
  
    /**
     * The constant file reference to the feed schema.
     * Change if needed.
     */
    private final static File SCHEMA_FILE = new File("atom.xsd");

    /**
     * Current date and time as an instance of XMLGregorianCalendar
     * @return current date
     */
    private static XMLGregorianCalendar getXMLGregorianCalendarNow() {
        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

            return datatypeFactory.newXMLGregorianCalendar(
                new GregorianCalendar()
            );
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(
                "DatatypeFactory was not properly configured.", ex
            );
        }
    }

    /**
     * Loads and instantiates the projects XML Schema file
     * @return reference to a Schema object
     */
    private static Schema loadSchema() {
        try {
            return SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(SCHEMA_FILE);
        } catch (SAXException ex) {
            throw new RuntimeException("Error during schema parsing", ex);
        } catch (NullPointerException ex) {
            throw new RuntimeException("Could not load Atom schema", ex);
        }
    }
    
    public static Feed load(String s) throws FileNotFoundException, JAXBException {        
        // Create JAXBContext.
        JAXBContext jc = JAXBContext.newInstance(Feed.class);

        // Create unmarshaller.
        Unmarshaller u = jc.createUnmarshaller();

        // Unmarshal an instance document into a tree of Java content objects
        // composed of classes from the xmlparser.generated.personen package.
        StringReader reader = new StringReader(s);
        Feed f = u.unmarshal(new StreamSource(s), Feed.class).getValue();
        return f;
    }

    public void save() throws FileNotFoundException, JAXBException {
        // Create JAXBContext.
        JAXBContext jc = JAXBContext.newInstance(Feed.class);

        // Create unmarshaller & marshaller
        Unmarshaller u = jc.createUnmarshaller();
        Marshaller m = jc.createMarshaller();

        // Marshal
        OutputStream os = new FileOutputStream("new_feed.xml");
        m.marshal(this.getFeed(), os);
    }

    public void addEntry(String title, String url, String summary, String author) {
        // new entry
        Entry e = new Entry();

        Person p = new Person();                    // new author
        p.setName(author);
        e.setAuthor(p);

        e.setTitle(title);                          // title
        
        e.setSummary(summary);                      // summary
        
        e.setId(String.valueOf(url.hashCode()));    // id
        
        // last modified
        e.setUpdated(getXMLGregorianCalendarNow());
        this.getFeed().setUpdated(getXMLGregorianCalendarNow());
        
        // add entry to the feed
        this.getFeed().getEntry().add(0, e);
    }

    public static void main(String[] args) throws FileNotFoundException, JAXBException {
        FeedModel fm = new FeedModel();
        fm.addEntry("Neue Pizza", "http://www.beaterie.com", "Inhalt zusammengefasst", "Josef");
        fm.addEntry("Salat", "test.com", "Inhalte bla", "Jana");
        fm.addEntry("Bitte NUR FLEISCH", "nice.de", "Inhalt blubb", "Josef");
        fm.save();
    }
}
