package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Chassis implements Subsystem{

    private In input = new In(SubsystemID.CHASSIS);
    private Out output = new Out(SubsystemID.CHASSIS);

    private double slowSpeed = Double.parseDouble(output.getAttribute("slow"));
    private double normalSpeed = Double.parseDouble(output.getAttribute("normal"));
    private double fastSpeed = Double.parseDouble(output.getAttribute("fast"));
    private double speedMod = normalSpeed;

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
        if(input.getAxis("MODE_SLOW", "DRIVE") < -0.7f){
            speedMod = slowSpeed;
        }else if(input.getAxis("MODE_FAST", "DRIVE") < -0.7f){
            speedMod = fastSpeed;
        }else{
            speedMod = normalSpeed;
        }
        
        output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
        output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);
    }
}