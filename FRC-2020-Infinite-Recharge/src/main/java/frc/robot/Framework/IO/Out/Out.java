package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.IO.Out.Motors.MotorWrapper;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.SparkController;
import frc.robot.Framework.Util.XMLParser;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.*;

public class Out{
    private static Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();

    private static class SubsystemCollection{
        public Map<String, MotorWrapper> motors = new HashMap<>();
        public Map<String, String> soleniods = new HashMap<>();

        public SubsystemCollection(Element system){
            NodeList motorNodes = system.getElementsByTagName("motor");
            for(int i = 0; i < motorNodes.getLength(); i++){
                Node currentMotor = motorNodes.item(i);
                if(currentMotor.getNodeType() == Node.ELEMENT_NODE){
                    Element motorElement = (Element)currentMotor;
                    setupMotor(motorElement);
                }
            }

            /*NodeList soleniodNodes = system.getElementsByTagName("solenoid"); 
            for(int i = 0; i < axisNodes.getLength(); i++){
                Node currentAxis = axisNodes.item(i);
                if(currentAxis.getNodeType() == Node.ELEMENT_NODE){
                    Element axisElement = (Element)currentAxis;

                    //axes.put(axisElement.getAttribute("function"), axisElement.getAttribute("axis"));
                }
            }*/

            NodeList groupNodes = system.getElementsByTagName("group");
            for(int i = 0; i < groupNodes.getLength(); i++){
                Node currentGroup = groupNodes.item(i);
                if(currentGroup.getNodeType() == Node.ELEMENT_NODE){
                    Element groupElement = (Element)currentGroup;
                    NodeList groupMotorNodes = groupElement.getElementsByTagName("motor");
                }
            }
        }

        private void setupMotor(Element motorElement){
            String id = motorElement.getAttribute("id");
            int port = Integer.parseInt(motorElement.getAttribute("port"));
            MotorBase controllerType = getMotorType(motorElement.getAttribute("controller"), port);

            if(controllerType != null){
                motors.put(motorElement.getAttribute("id"), new MotorWrapper(controllerType, motorElement));
            }else{
                System.out.println("For motor: "+id+" motor controller type: "+controllerType+" was not found!");
            }
        }

        private MotorBase getMotorType(String controllerType, int port){
            if(controllerType.equals("SPARK")){
                return new SparkController(port);
            }else{
                return null;
            }
        }
    }


    public static void Init(String xmlPath){
        XMLParser parser = new XMLParser("/home/deploy/"+xmlPath);
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

    public Out(String XMLNode){
        
    };

    public void setMotor(String name, double speed){
        motors.get(name).set(speed);
    }
}