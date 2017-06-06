package SAX; 
import org.xml.sax.*;
import org.xml.sax.helpers.*;


public class SAXParserExampleHandler extends DefaultHandler{
    // Boolean state variables for modeling the states of the parser.
	boolean parseTitle, parseID;
	String title, id;
	String targetTitle, targetID;
	int counter = 0;
	// String old_id = "";
	// String new_id = "";

	
	public SAXParserExampleHandler(String title_, String id_){ 

		targetTitle = title_;
		targetID = id_;
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

		// int i = 0;
		// String[] ids = new String[3];
		// old_id= id;

		// if(old_id != new_id){

		// 	new_id = old_id;
		// 	System.out.println("new_id: " + new_id);
		// }


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