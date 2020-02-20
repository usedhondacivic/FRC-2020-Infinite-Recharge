package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Shooter implements Subsystem{
    private In input = new In(SubsystemID.SHOOTER);
    private Out output = new Out(SubsystemID.SHOOTER);

    private double closeSpeed = Double.parseDouble(output.getAttribute("close_speed"));
    private double trenchSpeed = Double.parseDouble(output.getAttribute("trench_speed"));
    private double colorWheelSpeed = Double.parseDouble(output.getAttribute("color_wheel_speed"));

    private enum setShots{
        CLOSE,
        TRENCH,
        COLOR_WHEEL
    };

    setShots currentShot = setShots.TRENCH;

    public void robotInit(){
        System.out.println("Shooter init");
    }

    public void robotPeriodic(){

    }

    public void autonomousInit(){

    }
    public void autonomousPeriodic(){

    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){
        if(input.getButton("REV_UP", "OPERATOR")){
            if(currentShot == setShots.CLOSE){
                output.setMotor("SHOOTER_WHEEL", closeSpeed);
                output.setSolenoid("HOOD_ADJUST", false);
            }else if(currentShot == setShots.TRENCH){
                output.setMotor("SHOOTER_WHEEL", trenchSpeed);
                output.setSolenoid("HOOD_ADJUST", true);
            }else if(currentShot == setShots.COLOR_WHEEL){
                output.setMotor("SHOOTER_WHEEL", colorWheelSpeed);
                output.setSolenoid("HOOD_ADJUST", true);
            }else{
                output.setSolenoid("HOOD_ADJUST", true);
            }
        }else{
            output.setMotor("SHOOTER_WHEEL", 0);
        }
    }
}