package frc.robot.Framework.IO.In;

import java.util.Map;
import java.util.HashMap;

import frc.robot.Framework.IO.In.ControllerWrapper;
import frc.robot.Framework.IO.In.ControllerTypes.LogitechGamepad;
import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

public class In{
    private static Map<String, ControllerWrapper> controllers = new HashMap<>();

    public static void Init(String controlPath, String sensorPath){
        XMLParser parser = new XMLParser("/home/deploy/"+controlPath);
        Element root = parser.getRootElement();
        NodeList controllerList = root.getElementsByTagName("controller");
        initControllers(controllerList);

        NodeList sensorList = root.getElementsByTagName("sensor");
        initSensors(sensorList);
    };

    private static void initControllers(NodeList controllerList){
        for (int i = 0; i < controllerList.getLength(); i++){
            Node controllerNode = controllerList.item(i);
            if(controllerNode.getNodeType() == Node.ELEMENT_NODE){
                Element controllerElement = (Element) controllerNode;
                if(controllerElement.getAttribute("type") == "LOGITECH"){
                    Integer port = Integer.parseInt(controllerElement.getAttribute("port"));
                    String id = controllerElement.getAttribute("id");
                    controllers.put(id , new ControllerWrapper(new LogitechGamepad(port)));
                }
            }
        }
    }

    private static void initSensors(NodeList controllerList){

    }

    public static boolean getButton(String function, String controller, SubsystemID subsystem){
        ControllerWrapper requestedController = controllers.get(controller);
        return requestedController.getButton(function, subsystem);
    }

    public static double getAxis(String function, String controller, SubsystemID subsystem){
        ControllerWrapper requestedController = controllers.get(controller);
        return requestedController.getAxis(function, subsystem);
    }
}