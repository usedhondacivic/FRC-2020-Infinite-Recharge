package frc.robot.Framework.IO.Out;

import frc.robot.Framework.IO.Out.Motors.MotorBase;

import java.util.Map;
import java.util.HashMap;

public class Out{
    private Map<String, MotorBase> motors = new HashMap<>();

    public Out(String XMLNode){

    };

    public void setMotor(String name, double speed){
        motors.get(name).set(speed);
    }
}