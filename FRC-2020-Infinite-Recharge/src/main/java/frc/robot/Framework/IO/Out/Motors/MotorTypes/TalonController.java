package frc.robot.Framework.IO.Out.Motors.MotorTypes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Framework.IO.Out.Motors.MotorBase;

public class TalonController implements MotorBase{
    private TalonSRX controller;

    public TalonController(int port){
        controller = new TalonSRX(port);
        controller.set(ControlMode.PercentOutput, 0);
    }

    public void set(double speed){
        controller.set(ControlMode.PercentOutput, 0);
    };

    public void setInverted(boolean invert){
        controller.setInverted(invert);
    }
}