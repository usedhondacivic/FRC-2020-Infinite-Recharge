package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.CommandMode;

public class Shooter implements Subsystem{
    private In input = new In(SubsystemID.SHOOTER);
    private Out output = new Out(SubsystemID.SHOOTER);

    private double closeSpeed = Double.parseDouble(output.getAttribute("close_speed"));
    private double trenchSpeed = Double.parseDouble(output.getAttribute("trench_speed"));
    private double colorWheelSpeed = Double.parseDouble(output.getAttribute("color_wheel_speed"));

    private double turretUpper = Double.parseDouble(output.getAttribute("turret_upper"));
    private double turretLower = Double.parseDouble(output.getAttribute("turret_lower"));

    private NetworkTableInstance tableInst = NetworkTableInstance.getDefault();
    private NetworkTable cameraTable = tableInst.getTable("limelight");

    private double kP = 0.03f;
    private double minCommand = 0.1f;

    private boolean resetInAuto = false;

    private enum setShots{
        CLOSE,
        TRENCH,
        COLOR_WHEEL
    };

    setShots currentShot = setShots.TRENCH;

    public void robotInit(){
        System.out.println("Shooter init");
    }

    public void robotPeriodic(){

    }

    public void autonomousInit(){
        output.resetEncoder("TURRET_AIM");
        resetInAuto = true;
    }
    public void autonomousPeriodic(){

    }

    public void teleopInit(){
        output.setPID("SHOOTER_WHEEL", 0.001, 0, 0.5, 0.00019);
        if(!resetInAuto){
            output.resetEncoder("TURRET_AIM");
        }
        resetInAuto = false;
    }

    public void teleopPeriodic(){
        SmartDashboard.putNumber("Turret position", output.getPosition("TURRET_AIM"));
        SmartDashboard.putNumber("Shooter velocity",output.getVelocity("SHOOTER_WHEEL"));
        double targetFound = cameraTable.getEntry("tv").getDouble(0);
        SmartDashboard.putBoolean("Target found", targetFound == 1);
        if(input.getAxis("REV_UP", "OPERATOR") > 0.7f){
            if(targetFound == 1){
                double tx = cameraTable.getEntry("tx").getDouble(0);
                double steerAdjust = 0.0f;
                if(tx > 1.0){
                    steerAdjust = kP*tx - minCommand;
                }else if(tx < 1.0){
                    steerAdjust = kP*tx + minCommand;
                }
                setTurret(steerAdjust);
            }else{
                setTurret(input.getAxis("TURRET_AIM", "OPERATOR"));
            }
            //output.setMotor("LEFT_SIDE", -steerAdjust);
            //output.setMotor("RIGHT_SIDE", steerAdjust);
            if(currentShot == setShots.CLOSE){
                output.setMotor("SHOOTER_WHEEL", closeSpeed);
            }else if(currentShot == setShots.TRENCH){
                output.setMotor("SHOOTER_WHEEL", trenchSpeed, CommandMode.VELOCITY);
            }else if(currentShot == setShots.COLOR_WHEEL){
                output.setMotor("SHOOTER_WHEEL", colorWheelSpeed);
            }
        }else{
            output.setMotor("SHOOTER_WHEEL", 0);
            setTurret(input.getAxis("TURRET_AIM", "OPERATOR"));
        }
        
        if(currentShot == setShots.CLOSE){
            output.setSolenoid("HOOD_ADJUST", true);
        }else if(currentShot == setShots.TRENCH){
            output.setSolenoid("HOOD_ADJUST", false);
        }else if(currentShot == setShots.COLOR_WHEEL){
            output.setSolenoid("HOOD_ADJUST", false);
        }else{
            output.setSolenoid("HOOD_ADJUST", false);
        }
    }

    private void setTurret(double value){
        if(output.getPosition("TURRET_AIM") > turretUpper && value > 0){
            output.setMotor("TURRET_AIM", 0);
        }else if(output.getPosition("TURRET_AIM") < turretLower && value < 0){
            output.setMotor("TURRET_AIM", 0);
        }else{
            output.setMotor("TURRET_AIM", value);
        }
    }
}