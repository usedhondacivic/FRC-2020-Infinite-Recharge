package frc.robot.Framework.IO.Out.Motors;

import edu.wpi.first.wpilibj.Spark;

public class SparkController implements MotorBase{
    private Spark controller;

    public void init(int port){
        controller = new Spark(port);
    }

    public void set(double speed){
        controller.set(speed);
    };
}