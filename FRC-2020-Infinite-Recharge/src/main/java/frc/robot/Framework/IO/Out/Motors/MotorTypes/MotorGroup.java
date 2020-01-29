package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.IO.Out.Motors.MotorWrapper;

public class MotorGroup implements MotorBase{
    private MotorWrapper motorOne;
    private MotorWrapper motorTwo;

    public MotorGroup(MotorWrapper motorOne, MotorWrapper motorTwo){
        this.motorOne = motorOne;
        this.motorTwo = motorTwo;
    }

    public void set(double speed){
        motorOne.set(speed);
        motorTwo.set(speed);
    }
}