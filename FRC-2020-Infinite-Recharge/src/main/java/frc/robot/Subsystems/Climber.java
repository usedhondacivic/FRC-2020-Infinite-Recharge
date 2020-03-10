package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Climber implements Subsystem{
    private In input = new In(SubsystemID.CLIMBER);
    private Out output = new Out(SubsystemID.CLIMBER);

    private double climbSpeed = Double.parseDouble(output.getAttribute("speed"));

    public void robotInit(){
        System.out.println("Climber init");
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
        if(input.getButton("LEFT_EXTEND", "DRIVE")){
            output.setMotor("WINCH_LEFT", climbSpeed);
        }else if(input.getButton("LEFT_RETRACT", "DRIVE")){
            output.setMotor("WINCH_LEFT", -climbSpeed);
        }else{
            output.setMotor("WINCH_LEFT", 0);
        }

        if(input.getButton("RIGHT_EXTEND", "DRIVE")){
            output.setMotor("WINCH_RIGHT", climbSpeed);
        }else if(input.getButton("RIGHT_RETRACT", "DRIVE")){
            output.setMotor("WINCH_RIGHT", -climbSpeed);
        }else{
            output.setMotor("WINCH_RIGHT", 0);
        }
    }
}