package frc.robot.Framework.IO.In;

import java.util.Map;
import java.util.HashMap;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Framework.Util.XMLParser;
import frc.robot.Subsystems.SubsystemID;

import org.w3c.dom.*;

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
                
            }
        }
    };
}