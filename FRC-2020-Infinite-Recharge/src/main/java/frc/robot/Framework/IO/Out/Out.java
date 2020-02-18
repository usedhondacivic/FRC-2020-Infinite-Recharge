package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorWrapper;
import frc.robot.Framework.IO.Out.Solenoids.SolenoidWrapper;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.*;

public class Out {
    private static XMLParser parser;
    private static Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();

    private static class SubsystemCollection {
        public Map<String, MotorWrapper> motors = new HashMap<>();
        public Map<String, SolenoidWrapper> soleniods = new HashMap<>();
        private Element systemElement;

        public SubsystemCollection(Element system) {
            systemElement = system;
            NodeList children = system.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node currentChild = children.item(i);
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) currentChild;
                    if (childElement.getTagName().equals("motor")) {
                        String id = childElement.getAttribute("id");
                        motors.put(id, new MotorWrapper(childElement));
                    } else if (childElement.getTagName().equals("group")) {
                        String id = childElement.getAttribute("id");
                        motors.put(id, new MotorWrapper(childElement, true));
                    } else if (childElement.getTagName().equals("solenoid")) {
                        String id = childElement.getAttribute("id");
                        soleniods.put(id, new SolenoidWrapper(childElement));
                    }
                }
            }
        }

        public String getAttribute(String attribute){
            return systemElement.getAttribute(attribute);
        }
    }


    public static void Init(String xmlPath){
        parser = new XMLParser("/home/deploy/"+xmlPath);
        Element root = parser.getRootElement();
        NodeList subsystemList = root.getChildNodes();
        for(int i = 0; i < subsystemList.getLength(); i++){
            Node currentSubsystem = subsystemList.item(i);
            if(currentSubsystem.getNodeType() == Node.ELEMENT_NODE){
                Element systemElement = (Element)currentSubsystem;
                subsystemCollections.put(systemElement.getTagName(), new SubsystemCollection(systemElement));
            }
        }
    }

    private SubsystemID id;

    public Out(SubsystemID systemID){
        id = systemID;
    };

    public void setMotor(String name, double setpoint){
        SubsystemCollection requestedSystem = subsystemCollections.get(id.name());
        if(requestedSystem == null){
            System.out.println("Motor not found. Subsystem: "+id.name()+" not registered for output.");
            return;
        }
        MotorWrapper requestedMotor = requestedSystem.motors.get(name);
        if(requestedMotor == null){
            System.out.println("Motor not found. Subsystem: "+id.name()+" not registered for output.");
            return;
        }
        requestedMotor.set(setpoint);
    }

    public String getAttribute(String attribute){
        SubsystemCollection currentSystem = subsystemCollections.get(id.name());
        return currentSystem.getAttribute(attribute);
    }
}