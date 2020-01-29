package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;

public class Chassis implements Subsystem{

    private In input = new In(SubsystemID.CHASSIS);

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
        System.out.println("Drive left speed: "+input.getAxis("LEFT_SPEED", "DRIVE")+".");
    }
}