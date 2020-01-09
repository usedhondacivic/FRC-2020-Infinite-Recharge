package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;

public class Chassis implements Subsystem{
    private Out output = new Out();
    private In input = new Input();

    public void robotInit(){
        System.out.println("Chassis init");
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
        System.out.println("Chassis periodic");
    }
}