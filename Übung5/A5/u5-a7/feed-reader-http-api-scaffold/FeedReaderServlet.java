import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import jaxb.Entry;
import jaxb.Feed;
import jaxb.Link;
import jaxb.Person;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.GregorianCalendar;

public class FeedReaderServlet extends HttpServlet {

	private final static File FEED_FILE = new File(FeedModel.class.getResource("feed.xml").getFile());

    String xsl_1 = "xsl/atom-to-html.xsl";
    String xsl_2 = String.format("<?xml-stylesheet type='text/xsl' href='%s'?>",xsl_1);

	public void init() throws ServletException {}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/xml");
		PrintWriter output = response.getWriter();

        FeedModel model = new FeedModel();
        Feed feed = model.getFeed();

        // Marshalling the stuff
        try {
	        JAXBContext context = JAXBContext.newInstance(Feed.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        Schema schema = model.loadSchema();
	        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", xsl_2);
	        marshaller.setSchema(schema);
	        marshaller.marshal(feed, FEED_FILE);
        }
        catch (JAXBException ex) {
            throw new ServletException("Marshalling-Error\n"+ex);
        }

		BufferedReader br = new BufferedReader(new FileReader(FEED_FILE));
		String line = null;
		while ((line = br.readLine()) != null) {
			output.println(line);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_SEE_OTHER);

        FeedModel model = new FeedModel();
        Feed feed = model.getFeed();

        String title = request.getParameter("title");
        String url = request.getParameter("url");
        String author = request.getParameter("author");
        String summary = request.getParameter("summary");

        model.addEntry(title, url, summary, author);

        response.setHeader("Location","Feed");

    }

}