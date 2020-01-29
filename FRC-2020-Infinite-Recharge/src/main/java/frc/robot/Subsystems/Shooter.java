package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;

public class Shooter implements Subsystem{
    private In input = new In(SubsystemID.SHOOTER);

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
        if(input.getButton("INTAKE", "OPERATOR")){
            System.out.println("Intake button pressed.");
        }
    }
}