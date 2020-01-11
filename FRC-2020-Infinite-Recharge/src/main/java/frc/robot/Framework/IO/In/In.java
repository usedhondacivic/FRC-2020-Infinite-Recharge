package frc.robot.Framework.IO;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Framework.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class In{
    private Map<String, Joystick> controllers = new HashMap<>();

    private SubsystemID systemID;

    public In(SubsystemID systemID, String XMLPath){
        this.systemID = systemID;

        XMLParser parser = new XMLParser("/home/deploy/"+XMLPath);
        Element root = parser.getRootElement();
        NodeList currentList= root.getElementsByTagName("controller");
        for (int i = 0; i < currentList.getLength(); i++){
            Node node = currentList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                setupController((Element) node);
            }
        }

        currentList = root.getElementsByTagName("button");
        for (int i = 0; i < currentList.getLength(); i++){
            Node node = currentList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                setupButton((Element) node);
            }
        }
    };

    private void setupController(Element controller){
        controllers.put(controller.getAttribute("id"), new Joystick(Integer.parseInt(controller.getAttribute("port"))));
        System.out.println("Controller: "+controller.getAttribute("id")+" connected on port: "+ Integer.parseInt(controller.getAttribute("port")));
    }

    private void setupButton(Element button){
        buttons.put(button.getAttribute("function"), buttonIDs.get(button.getAttribute("button")));
        System.out.println("Controller: "+controller.getAttribute("id")+" connected on port: "+ Integer.parseInt(controller.getAttribute("port")));
    }

    public boolean getButton(String controller, String button){
        Joystick stick = controllers.get(controller);
        return stick.getRawButton(buttons.get(button));
    }

    public boolean getAxis(String controller, String axis){
        Joystick stick = controllers.get(controller);
        return stick.getRawButton(axes.get(axis));
    }
}