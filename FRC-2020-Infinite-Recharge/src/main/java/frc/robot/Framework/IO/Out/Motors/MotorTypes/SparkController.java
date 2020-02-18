package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import edu.wpi.first.wpilibj.Spark;
import frc.robot.Framework.IO.Out.Motors.MotorBase;

public class SparkController implements MotorBase{
    private Spark controller;

    public SparkController(int port){
        controller = new Spark(port);
    }

    public void set(double speed){
        controller.set(speed);
    };

    public void setInverted(boolean invert){
        controller.setInverted(invert);
    }
}