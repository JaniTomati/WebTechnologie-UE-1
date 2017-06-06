package SAX;
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
	DefaultHandler handler = new SAXParserExampleHandler("Erfurt", "1");
	spe.load("./SAX/wiki_articles.xml", handler);
	}

}


//Wie viele page-Elemente sind im Wikipedia-Dump enthalten?
//Welche Revisions-ID hat der Artikel mit Titel „Erfurt“ im Wikipedia-Dump?
//Wie viele verschiedene angemeldete Autoren hat der Wikipedia-Dump? Hinweis: Verwenden Sie die Klasse java.util.HashSet.