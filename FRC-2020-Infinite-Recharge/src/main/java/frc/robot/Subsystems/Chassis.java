package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Chassis implements Subsystem{
    private In input = new In(SubsystemID.CHASSIS);
    private Out output = new Out(SubsystemID.CHASSIS);

    private String driveType = input.getAttribute("type", "DRIVE");
    private double turningGain = Double.parseDouble(input.getAttribute("turning_gain", "DRIVE"));

    private double slowSpeed = Double.parseDouble(output.getAttribute("slow"));
    private double normalSpeed = Double.parseDouble(output.getAttribute("normal"));
    private double fastSpeed = Double.parseDouble(output.getAttribute("fast"));

    private double speedMod = normalSpeed;
    private boolean reversed = false;

    public void robotInit(){
        System.out.println("Chassis init");
    }

    public void robotPeriodic(){

    }

    public void autonomousInit(){

    }
    public void autonomousPeriodic(){
        if(Timer.getMatchTime() < 3){
            output.setMotor("LEFT_SIDE", -0.2);
            output.setMotor("RIGHT_SIDE", -0.2);
        }
    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){
        if(input.getAxis("REVERSE", "DRIVE") > 0.5f){
            reversed = true;
        }else{
            reversed = false;
        }

        if(input.getAxis("MODE_SLOW", "DRIVE") > 0.7f){
            speedMod = slowSpeed;
        }else if(input.getAxis("MODE_FAST", "DRIVE") > 0.7f){
            speedMod = fastSpeed;
        }else{
            speedMod = normalSpeed;
        }

        SmartDashboard.putNumber("Left side position", output.getPosition("LEFT_SIDE"));
        SmartDashboard.putNumber("Right side position", output.getPosition("RIGHT_SIDE"));

        if(driveType.equals("TANK")){
            output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
            output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);
        }else if(driveType.equals("DOUBLE_ARCADE")){
            output.setMotor("LEFT_SIDE", getArcadeLeftMotor() * speedMod);
            output.setMotor("RIGHT_SIDE", getArcadeRightMotor() * speedMod);
        }
        
        /*output.setMotor("LEFT_SIDE_FRONT", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
        output.setMotor("RIGHT_SIDE_FRONT", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);
        output.setMotor("LEFT_SIDE_BACK", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
        output.setMotor("RIGHT_SIDE_BACK", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);*/
    }

    private double getArcadeLeftMotor() {
        double leftMtr = input.getAxis("THROTTLE", "DRIVE") - input.getAxis("TURN", "DRIVE");
        double rightMtr = input.getAxis("THROTTLE", "DRIVE") + input.getAxis("TURN", "DRIVE");
        return leftMtr + skim(rightMtr);
    }

    private double getArcadeRightMotor() {
        double leftMtr = input.getAxis("THROTTLE", "DRIVE") - input.getAxis("TURN", "DRIVE");
        double rightMtr = input.getAxis("THROTTLE", "DRIVE") + input.getAxis("TURN", "DRIVE");
        return rightMtr + skim(leftMtr);            
    }

    private double skim(double v) {
        if (v > 1.0) {
            return -((v - 1.0) * turningGain);
        } else if (v < -1.0) {
            return -((v + 1.0) * turningGain);
        } return 0; 
    }
}