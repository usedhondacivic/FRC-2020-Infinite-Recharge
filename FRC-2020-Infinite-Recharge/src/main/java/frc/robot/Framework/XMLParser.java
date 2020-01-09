package frc.robot.Framework;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser{
    private String filePath;

    public XMLParser(String filePath){
        this.filePath = filePath;
    }

    public void print(){
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
        
            DefaultHandler handler = new DefaultHandler() {
            
                public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {

                    System.out.println("Start Element :" + qName);

                }
            
                public void endElement(String uri, String localName, String qName) throws SAXException {
            
                    System.out.println("End Element :" + qName);
            
                }
            
                public void characters(char ch[], int start, int length) throws SAXException {
                    
                }
            };
        
            saxParser.parse(filePath, handler);
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}