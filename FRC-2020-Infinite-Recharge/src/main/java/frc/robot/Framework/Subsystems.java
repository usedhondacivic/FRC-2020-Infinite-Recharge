package frc.robot.Framework;

import java.util.ArrayList;
import java.util.List;
import frc.robot.Framework.Subsystem;

public class Subsystems{
    private static List<Subsystem> subsystems = new ArrayList<>();

    public static void add(Subsystem newSubsystem){
        subsystems.add(newSubsystem);
    }

    public static void robotInit(){
        for(Subsystem subsystem : subsystems){
            subsystem.robotInit();
        }
    }
    
    public static void robotPeriodic(){
        for(Subsystem subsystem : subsystems){
            subsystem.robotPeriodic();
        }
    }

    public static void autonomousInit(){
        for(Subsystem subsystem : subsystems){
            subsystem.autonomousInit();
        }
    }

    public static void autonomousPeriodic(){
        for(Subsystem subsystem : subsystems){
            subsystem.autonomousPeriodic();
        }
    }

    public static void teleopInit(){
        for(Subsystem subsystem : subsystems){
            subsystem.teleopInit();
        }
    }

    public static void teleopPeriodic(){
        for(Subsystem subsystem : subsystems){
            subsystem.teleopPeriodic();
        }
    }
}