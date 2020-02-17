package frc.robot.Framework.IO.Out.Motors;

import org.w3c.dom.Element;

public class MotorWrapper{

    private MotorBase motor;
    private Element motorElement;

    public MotorWrapper(MotorBase motorType, Element motorElement){
        this.motor = motorType;
        this.motorElement = motorElement;
    }

    public void set(double speed){
        motor.set(speed);
    }

    public String getAttribute(String attribute){
        return motorElement.getAttribute(attribute);
    }
}