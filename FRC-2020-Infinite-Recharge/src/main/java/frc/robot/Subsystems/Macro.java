package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;

import frc.robot.Framework.IO.Out;
import frc.robot.Framework.IO.In;

public class Macro implements Subsystem{
    private Out output = new Out();
    private In input = new Input();

    public void robotInit(){
        System.out.println("Macro init");
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
        System.out.println("Macro periodic");
    }
}