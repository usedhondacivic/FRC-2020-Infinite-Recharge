package frc.robot.Framework.Util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLParser{
    private Document doc;

    public XMLParser(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
                    
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Element getRootElement(){
        return doc.getDocumentElement();
    }
}