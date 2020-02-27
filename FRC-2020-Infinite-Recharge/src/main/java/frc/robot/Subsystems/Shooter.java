package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.CommandMode;

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
        output.setPID("SHOOTER_WHEEL", 0.001, 0, 0.5, 0.00019);
    }

    public void teleopPeriodic(){
        System.out.println(output.getVelocity("SHOOTER_WHEEL"));
        if(input.getAxis("REV_UP", "OPERATOR") > 0.7f){
            if(currentShot == setShots.CLOSE){
                output.setMotor("SHOOTER_WHEEL", closeSpeed);
            }else if(currentShot == setShots.TRENCH){
                output.setMotor("SHOOTER_WHEEL", trenchSpeed, CommandMode.VELOCITY);
            }else if(currentShot == setShots.COLOR_WHEEL){
                output.setMotor("SHOOTER_WHEEL", colorWheelSpeed);
            }
        }else{
            output.setMotor("SHOOTER_WHEEL", 0);
        }
        
        if(currentShot == setShots.CLOSE){
            output.setSolenoid("HOOD_ADJUST", true);
        }else if(currentShot == setShots.TRENCH){
            output.setSolenoid("HOOD_ADJUST", false);
        }else if(currentShot == setShots.COLOR_WHEEL){
            output.setSolenoid("HOOD_ADJUST", false);
        }else{
            output.setSolenoid("HOOD_ADJUST", false);
        }

        output.setMotor("TURRET_AIM", input.getAxis("TURRET_AIM", "OPERATOR"));
    }
}