package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorBase;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.*;

public class Out{
    private static Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();

    private static class SubsystemCollection{
        public Map<String, String> motors = new HashMap<>();
        public Map<String, String> soleniods = new HashMap<>();

        public SubsystemCollection(Element system){
            NodeList buttonNodes = system.getElementsByTagName("button");
            for(int i = 0; i < buttonNodes.getLength(); i++){
                Node currentButton = buttonNodes.item(i);
                if(currentButton.getNodeType() == Node.ELEMENT_NODE){
                    Element buttonElement = (Element)currentButton;
                    buttons.put(buttonElement.getAttribute("function"), buttonElement.getAttribute("button"));
                }
            }

            NodeList axisNodes = system.getElementsByTagName("axis");
            for(int i = 0; i < axisNodes.getLength(); i++){
                Node currentAxis = axisNodes.item(i);
                if(currentAxis.getNodeType() == Node.ELEMENT_NODE){
                    Element axisElement = (Element)currentAxis;
                    axes.put(axisElement.getAttribute("function"), axisElement.getAttribute("axis"));
                }
            }
        }
    }

    public static void Init(String xmlPath){
        XMLParser parser = new XMLParser("/home/deploy/"+xmlPath);
        Element root = parser.getRootElement();
        NodeList subsystemList = root.getChildNodes();
        initControllers(controllerList);
    }

    public Out(String XMLNode){
        
    };

    public void setMotor(String name, double speed){
        motors.get(name).set(speed);
    }
}