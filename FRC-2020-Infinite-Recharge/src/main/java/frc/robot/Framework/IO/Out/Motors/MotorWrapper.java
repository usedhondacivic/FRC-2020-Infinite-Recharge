package frc.robot.Framework.IO.Out.Motors;

public class MotorWrapper{

    private MotorBase motor;

    MotorWrapper(MotorBase motorType){

    }

    public void set(double speed){
        motor.set(speed);
    }
}