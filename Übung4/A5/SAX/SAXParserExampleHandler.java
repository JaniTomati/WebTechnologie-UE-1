//Web-Technologie 2017 - Jula (115167), Jana (115753), Christopher (114602), Josef (115850)
package SAX;
import org.xml.sax.*;
import org.xml.sax.helpers.*;


public class SAXParserExampleHandler extends DefaultHandler{
    // Boolean state variables for modeling the states of the parser.
	boolean parseTitle, parseID;
	String title, id;
	String targetTitle;
	int counter = 0;


	public SAXParserExampleHandler(String title_){

		targetTitle = title_;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// Encode parse situation with the respective Boolean state variable.

		if(qName.equals("title")) {

			parseTitle = true;
		}


		if( qName.equals("parentid")) {

			parseID = true;
		}
	}


	public void characters(char[] ch, int start, int length){

		if(parseTitle){

			title = new String(ch, start, length);
			parseTitle = false;
		}

		if(parseID){

			id = new String(ch, start, length);
			parseID = false;
		}
	}


	public void endElement(String uri, String localName, String qName){


		if (qName.equals("page")){

			counter++;

	   		if (title.equals(targetTitle)){

	            System.out.println("[SAX] Title: " + targetTitle);
	            System.out.println("[SAX] ID: " + id);
	        }

	    	title = null;
	    	id = null;
		}

	}

	public void endDocument() throws SAXException {


		System.out.println("[SAX] Number of page elements: "+counter);


	}
}
