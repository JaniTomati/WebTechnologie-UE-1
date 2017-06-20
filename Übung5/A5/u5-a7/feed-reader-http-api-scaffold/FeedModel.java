import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import jaxb.Entry;
import jaxb.Feed;
import jaxb.Link;
import jaxb.Person;


/**
 * Manages access to the feed and holds an index of it.
 */
public class FeedModel {

////////////////////////////////////////////////////////////////////////////////
//                               START CHANGES                                //
////////////////////////////////////////////////////////////////////////////////

    /**
     * The constant file reference to the XML instance document.
     * Change if needed.
     */
    private final static File FEED_FILE =
        new File("WEB-INF/classes/feed.xml");
  
    /**
     * The constant file reference to the feed schema.
     * Change if needed.
     */
    private final static File SCHEMA_FILE =
        new File("WEB-INF/classes/atom.xsd");

    public Feed getFeed() {
      return this.feed;
    }

////////////////////////////////////////////////////////////////////////////////
//                                END CHANGES                                 //
////////////////////////////////////////////////////////////////////////////////

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
    public static Schema loadSchema() {
        try {
            return SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(SCHEMA_FILE);
        } catch (SAXException ex) {
            throw new IllegalArgumentException("Error during schema parsing", ex);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Could not load Atom schema", ex);
        }
    }

////////////////////////////////////////////////////////////////////////////////
//                             SOLUTION START (b)                             //
////////////////////////////////////////////////////////////////////////////////


    private static Feed unserializeFeed(Schema schema) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Feed.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            // Unserialize XML data into new Java content trees
            // This will also validate the XML data when the schema is set
            return unmarshaller
                .unmarshal(new StreamSource(FEED_FILE), Feed.class)
                .getValue();
        } catch (JAXBException ex) {
            throw new IllegalArgumentException("Could not unserialize feed.", ex);
        }
    }

    /**
     * Creates a properly configured Marshaller for serializing XML
     */
    private static Marshaller createMarshaller(Schema schema) {
        try {
            JAXBContext context = JAXBContext.newInstance(Feed.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setSchema(schema);
            return marshaller;
        } catch (JAXBException ex) {
            throw new IllegalArgumentException("Could not create Marshaller.", ex);
        }
    }


    // Constructor and member variables
    private final Schema schema;
    private final Feed feed;
    private final Marshaller marshaller;

    /**
     * Default constructor
     */
    public FeedModel() {
        schema = loadSchema();
        // Unserialize XML data into new Java content trees
        // This will also validate the XML data when the schema is set
        feed = unserializeFeed(schema);
        marshaller = createMarshaller(schema);
    }

////////////////////////////////////////////////////////////////////////////////
//                             SOLUTION START (c)                             //
////////////////////////////////////////////////////////////////////////////////

    /**
     * Composes a new entry and adds it to the feed via `addEntry(entry)`
     */
    public void addEntry(String title, String url, String summary, String author)
    throws FileNotFoundException, IOException {
        if (title == null) {
            throw new IllegalArgumentException("Entry is not valid (title is null)");
        }

        // Generate ID
        String id = Integer.toHexString(url.hashCode());
        // Get current date
        XMLGregorianCalendar updated = getXMLGregorianCalendarNow();
        // Create link
        Link link = new Link();
        link.setHref(url);
        // Create person
        Person person = new Person();
        person.setName(author);
        // Create entry
        Entry entry = new Entry();
        entry.setId(id);
        entry.setTitle(title);
        entry.setUpdated(updated);
        entry.setAuthor(person);
        entry.setLink(link);
        entry.setSummary(summary);

        validateEntry(entry);

        feed.getEntry().add(0, entry);

        feed.setUpdated(updated);

        serializeFeed();
    }

    private void validateEntry(Entry entry) throws IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
            JAXBSource jaxbSource = new JAXBSource(jaxbContext, entry);
            Validator validator = schema.newValidator();
            validator.validate(jaxbSource);
        } catch (JAXBException ex) {
            throw new IllegalArgumentException("Could not validate object", ex);
        } catch (SAXException ex) {
            throw new IllegalArgumentException("Entry is not valid", ex);
        }
    }

    private void serializeFeed() {
        try {
            marshaller.marshal(feed, FEED_FILE);
        } catch (JAXBException ex) {
            throw new RuntimeException("Could not serialize feed to feed file");
        }
    }


////////////////////////////////////////////////////////////////////////////////
//                                TESTS START                                 //
////////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {
        FeedModel model = new FeedModel();
        Feed feed = model.feed;

        System.out.println("Start ...");
        System.out.println("Latest change: " + feed.getUpdated());

        System.out.println("Feed title:    " + feed.getTitle());
        feed.setTitle(feed.getTitle() + " (updated)");
        System.out.println("Feed title:    " + feed.getTitle());
        Entry testEntry = feed.getEntry().get(0);
        System.out.println("Entry title:   " + testEntry.getTitle());
        testEntry.setTitle(testEntry.getTitle() + " (updated)");
        System.out.println("Entry title:   " + testEntry.getTitle());

        model.addEntry(
            "Hyperlink.cool",
            "https://hyperlink.cool",
            "Provide quick access to study-related links for Bauhaus-University Weimar's Computer Science students.",
            "Philipp Rudloff"
        );
        Entry newEntry = feed.getEntry().get(0);
        System.out.println("Entry title:   " + newEntry.getTitle());
        System.out.println("Entry link:    " + newEntry.getLink().getHref());
        System.out.println("Entry summary: " + newEntry.getSummary());
        System.out.println("Entry author:  " + newEntry.getAuthor().getName());

        System.out.println("Latest change: " + feed.getUpdated());
        
        try {
            model.addEntry(
                null,
                "https://hyperlink.cool",
                "Provide quick access to study-related links for Bauhaus-University Weimar's Computer Science students.",
                "Philipp Rudloff"
            );
        } catch (IllegalArgumentException ex) {
            System.out.println("Entry did indeed not validate");
        }

        // model.serializeFeed(feed);
        System.out.println("Done.");
    }

}
