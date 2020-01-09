package frc.robot.Framework.IO;

import frc.robot.Framework.IO.Motors.MotorBase;

import java.util.Map;
import java.util.HashMap;
import edu.wpi.first.wpilibj.Joystick;

public class In{
    private Map<String, Joystick> controllers = new HashMap<>();
    private Map<String, Integer> buttons = new HashMap<>();
    private Map<String, Integer> axes = new HashMap<>();

    public In(String XMLNode){

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