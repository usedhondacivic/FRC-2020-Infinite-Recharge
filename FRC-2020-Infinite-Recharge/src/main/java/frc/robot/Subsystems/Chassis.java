package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
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

    private double kP = 0.03f;
    private double minCommand = 0.05f;

    private NetworkTableInstance tableInst = NetworkTableInstance.getDefault();
    private NetworkTable cameraTable = tableInst.getTable("limelight");

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
        double targetFound = cameraTable.getEntry("tv").getDouble(0);
        System.out.println("Target found: "+targetFound);
        if(input.getButton("CAMERA_AIM", "DRIVE") && targetFound == 1){
            double tx = cameraTable.getEntry("tx").getDouble(0);
            double steerAdjust = 0.0f;
            if(tx > 1.0){
                steerAdjust = kP*tx - minCommand;
            }else if(tx < 1.0){
                steerAdjust = kP*tx + minCommand;
            }
            output.setMotor("LEFT_SIDE", -steerAdjust);
            output.setMotor("RIGHT_SIDE", steerAdjust);
        }else{
            if(input.getAxis("MODE_SLOW", "DRIVE") > 0.7f){
                speedMod = slowSpeed;
            }else if(input.getAxis("MODE_FAST", "DRIVE") > 0.7f){
                speedMod = fastSpeed;
            }else{
                speedMod = normalSpeed;
            }

            output.setMotor("LEFT_SIDE", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
            output.setMotor("RIGHT_SIDE", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);
        }
        
        /*output.setMotor("LEFT_SIDE_FRONT", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
        output.setMotor("RIGHT_SIDE_FRONT", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);
        output.setMotor("LEFT_SIDE_BACK", input.getAxis("LEFT_SPEED", "DRIVE") * speedMod);
        output.setMotor("RIGHT_SIDE_BACK", input.getAxis("RIGHT_SPEED", "DRIVE") * speedMod);*/
    }
}