//Web-Technologie 2017 - Jula (115167), Jana (115753), Christopher (114602), Josef (115850)
<<<<<<< HEAD:Übung4/A5/SAX/SAXParserExample.java
package SAX;
=======
<<<<<<< HEAD:Übung4/A5/SAXParserExample.java

import SAX.SAXParserExampleHandler;
=======
package SAX;
>>>>>>> parent of 51dc59e... Add A5. Small changes because the files did not compile. :octocat::Übung4/A5/SAX/SAXParserExample.java
>>>>>>> f205b81... Did this work?:Übung4/A5/SAX/SAXParserExample.java
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;



public class SAXParserExample{
	public void load(String filename, DefaultHandler handler) throws SAXException, IOException {

        XMLReader xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(handler);
        xr.parse(filename);
}

public static void main(String[] args) throws SAXException, IOException{ 
	
	SAXParserExample spe = new SAXParserExample();
	DefaultHandler handler = new SAXParserExampleHandler("Erfurt");
	spe.load("./SAX/wiki_articles.xml", handler);
	}

}
