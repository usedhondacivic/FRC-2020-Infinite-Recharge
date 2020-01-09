package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;

public class Shooter implements Subsystem{
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
        System.out.println("Shooter periodic");
    }
}