package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import java.util.ArrayList;

import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.IO.Out.Motors.MotorWrapper;

public class MotorGroup implements MotorBase{
    private ArrayList<MotorWrapper> motors = new ArrayList<>();

    public MotorGroup(){};

    public void addMotor(MotorWrapper newMotor){
        motors.add(newMotor);
    }

    public void set(double speed){
        for(int i = 0; i < motors.size(); i++){
            motors.get(i).set(speed);
        }
    }

    public void setInverted(boolean invert){
        for(int i = 0; i < motors.size(); i++){
            motors.get(i).setInverted(invert);
        }
    }
}