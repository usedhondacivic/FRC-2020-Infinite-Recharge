package frc.robot.Framework.IO.In;

import java.util.Map;
import java.util.HashMap;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

public abstract class ControllerWrapper{
    private Map<String, Integer> buttonIDs = Map.ofEntries(
        Map.entry("A", 1),
        Map.entry("B", 2),
        Map.entry("X", 3),
        Map.entry("Y", 4),
        Map.entry("LEFT_SHOULDER", 5),
        Map.entry("RIGHT_SHOULDER", 6),
        Map.entry("BACK", 7),
        Map.entry("RESTART", 8),
        Map.entry("LEFT_JOYSTICK_IN", 9),
        Map.entry("RIGHT_JOYSTICK_IN", 10)
    );
    private Map<String, Integer> axisIDs = Map.ofEntries(
        Map.entry("LEFT_JOYSTICK_X", 0),
        Map.entry("LEFT_JOYSTICK_Y", 1),
        Map.entry("LEFT_TRIGGER", 2),
        Map.entry("RIGHT_TRIGGER", 3),
        Map.entry("RIGHT_JOYSTICK_X", 4),
        Map.entry("RIGHT_JOYSTICK_Y", 5)
    );

    private ControllerBase controller;
    private Map<String, SubsystemCollection> subsystemCollections = new HashMap<>();

    private class SubsystemCollection{
        public Map<String, Integer> buttons = new HashMap<>();
        public Map<String, Integer> axes = new HashMap<>();

        public SubsystemCollection(Element system){
            NodeList buttonNodes = system.getElementsByTagName("button");
            for(int i = 0; i < buttonNodes.getLength(); i++){
                Node currentButton = buttonNodes.item(i);
                if(currentButton.getNodeType() == Node.ELEMENT_NODE){
                    Element buttonElement = (Element)currentButton;
                    buttons.put(buttonElement.getAttribute("function"), buttonIDs.get(buttonElement.getAttribute("button")));
                }
            }

            NodeList axisNodes = system.getElementsByTagName("axis");
            for(int i = 0; i < axisNodes.getLength(); i++){
                Node currentAxis = axisNodes.item(i);
                if(currentAxis.getNodeType() == Node.ELEMENT_NODE){
                    Element axisElement = (Element)currentAxis;
                    axes.put(axisElement.getAttribute("function"), axisIDs.get(axisElement.getAttribute("axis")));
                }
            }
        }
    }

    public ControllerWrapper(Element controllerXML){
        NodeList subsystems = controllerXML.getChildNodes();
        for(int i = 0; i < subsystems.getLength(); i++){
            Node currentSubsystem = subsystems.item(i);
            if(currentSubsystem.getNodeType() == Node.ELEMENT_NODE){
                Element systemElement = (Element)currentSubsystem;
                subsystemCollections.put(systemElement.getTagName(), new SubsystemCollection(systemElement));
            }
        }
    }

    public boolean getButton(String buttonName, SubsystemID subsystemID){
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if(requestedSystem == null){
            System.out.println("Button not found. Subsystem not registered on this controller.");
            return false;
        }
        Integer requestedButton = requestedSystem.buttons.get(buttonName);
        if(requestedButton == null){
            System.out.println("Button not found. Button not registered for requested subsystem.");
            return false;
        }
        return controller.getButton(requestedButton);
    }

    public double getAxis(String axisName, SubsystemID subsystemID){
        SubsystemCollection requestedSystem = subsystemCollections.get(subsystemID.name());
        if(requestedSystem == null){
            System.out.println("Axis not found. Subsystem not registered on this controller.");
            return 0.0;
        }
        Integer requestedAxis = requestedSystem.axes.get(axisName);
        if(requestedAxis == null){
            System.out.println("Axis not found. Axis not registered for requested subsystem.");
            return 0.0;
        }
        return controller.getAxis(requestedAxis);
    }
}