package frc.robot.Framework.IO;

import java.util.Map;
import java.util.HashMap;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Framework.XMLParser;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class In{
    private Map<String, Joystick> controllers = new HashMap<>();
    private Map<String, Integer> buttons = new HashMap<>();
    private Map<String, Integer> axes = new HashMap<>();

    public In(String systemName, String XMLPath){
        XMLParser parser = new XMLParser(XMLPath);
        Element root = parser.getRootElement();
        NodeList nodeList= root.getElementsByTagName("controller");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element controller = (Element) node;
                controllers.put(controller.getAttribute("id"), new Joystick(Integer.parseInt(controller.getAttribute("port"))));
                System.out.println("Controller: "+controller.getAttribute("id")+" connected on port: "+ Integer.parseInt(controller.getAttribute("port")));
                
            }
        }
    };

    public boolean getButton(String controller, String button){
        Joystick stick = controllers.get(controller);
        return stick.getRawButton(buttons.get(button));
    }

    public boolean getAxis(String controller, String axis){
        Joystick stick = controllers.get(controller);
        return stick.getRawButton(axes.get(axis));
    }
}