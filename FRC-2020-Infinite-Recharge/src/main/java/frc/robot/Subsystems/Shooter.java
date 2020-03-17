package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import frc.robot.Framework.Subsystem;
import frc.robot.Framework.Subsystems;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import frc.robot.Framework.Util.CommandMode;

public class Shooter implements Subsystem {
    Timer time = new Timer();
    private In input = new In(SubsystemID.SHOOTER);
    private Out output = new Out(SubsystemID.SHOOTER);

    private String speedTable = output.getAttribute("speed_table");
    private String angleTable = output.getAttribute("angle_table");
    private BufferedReader reader;
    private ArrayList<double[]> shotDistanceTable;
    private ArrayList<double[]> angleAdjustmentTable;

    private double shotAdjust = Double.parseDouble(output.getAttribute("shot_adjust_strength"));

    private double closeSpeed = Double.parseDouble(output.getAttribute("close_speed"));
    private double defaultSpeed = Double.parseDouble(output.getAttribute("default_speed"));
    private double turretUpper = Double.parseDouble(output.getAttribute("turret_upper"));
    private double turretLower = Double.parseDouble(output.getAttribute("turret_lower"));

    private NetworkTableInstance tableInst = NetworkTableInstance.getDefault();
    private NetworkTable cameraTable = tableInst.getTable("limelight");

    private double kP = 0.015f;
    private double minCommand = 0.05f;

    private boolean resetInAuto = false;

    private double targetFound = 0;

    private enum setShots {
        CLOSE, TRENCH, COLOR_WHEEL
    };

    setShots currentShot = setShots.TRENCH;

    public void robotInit(){
        System.out.println("Shooter init");
        output.setPID("SHOOTER_WHEEL", 0.001, 0, 0.5, 0.00019);
        shotDistanceTable = initTable(speedTable);
        angleAdjustmentTable = initTable(angleTable);
    }

    public void robotPeriodic() {
        targetFound = cameraTable.getEntry("tv").getDouble(0);
        SmartDashboard.putBoolean("Target found", targetFound == 1);

        SmartDashboard.putNumber("Turret position", output.getPosition("TURRET_AIM"));
        SmartDashboard.putNumber("Shooter velocity", output.getVelocity("SHOOTER_WHEEL"));
    }

    public void autonomousInit() {
        output.resetEncoder("TURRET_AIM");
        resetInAuto = true;
    }

    public void autonomousPeriodic() {
        if (Timer.getMatchTime() > 3) {
            output.setMotor("SHOOTER_WHEEL", getShotSpeed(), CommandMode.VELOCITY);

            if (targetFound == 1) {
                trackTarget();
                if (Timer.getMatchTime() < 13) {
                    ((Intake) Subsystems.getSubsystem(SubsystemID.INTAKE)).autoShoot();
                }
            } else {
                setTurret(0.2);
            }
        } else {
            output.setMotor("SHOOTER_WHEEL", 0);
        }
    }

    public void teleopInit() {
        if (!resetInAuto) {
            output.resetEncoder("TURRET_AIM");
        }
        resetInAuto = false;
    }

    public void teleopPeriodic() {
        if (input.getAxis("REV_UP", "OPERATOR") > 0.7f) {
            if (input.getButton("MANUAL_AIM", "OPERATOR")) {
                setTurret(input.getAxis("TURRET_AIM", "OPERATOR") * 0.5);
            } else if (targetFound == 1) {
                trackTarget();
            } else {
                setTurret(input.getAxis("TURRET_AIM", "OPERATOR") * 0.5);
            }

            output.setMotor("SHOOTER_WHEEL", getShotSpeed(), CommandMode.VELOCITY);
        } else {
            output.setMotor("SHOOTER_WHEEL", 0);
            setTurret(input.getAxis("TURRET_AIM", "OPERATOR") * 0.5);
        }

        findAngle();
        getDistance();
    }

    private void trackTarget() {
        double tx = cameraTable.getEntry("tx").getDouble(0);
        double steerAdjust = 0.0f;
        if (tx > 1.0) {
            steerAdjust = kP * tx + minCommand;
        } else if (tx < 1.0) {
            steerAdjust = kP * tx - minCommand;
        }
        setTurret(steerAdjust);
    }

    private double getShotSpeed() {
        double returnSpeed = defaultSpeed;

        if(input.getButton("HIGH_SHOT", "OPERATOR")) {
            output.setSolenoid("HOOD_ADJUST", true);
            returnSpeed = closeSpeed;
        }else{
            output.setSolenoid("HOOD_ADJUST", false);
            returnSpeed = getSpeedFromDistance(getDistance());
        }

        return returnSpeed + input.getAxis("SHOT_ADJUST", "OPERATOR") * shotAdjust;
    }

    private double getSpeedFromDistance(double distance){
        double speed = pointLerp(shotDistanceTable, distance, defaultSpeed);
        double angleAdjustment = pointLerp(angleAdjustmentTable, output.getPosition("TURRET_AIM"), 1);
        
        SmartDashboard.putNumber("Calculated speed", speed);
        SmartDashboard.putNumber("Calculated angle adjust", angleAdjustment);

        return speed * angleAdjustment;
    }

    private void setTurret(double value) {
        if (output.getPosition("TURRET_AIM") > turretUpper && value > 0) {
            output.setMotor("TURRET_AIM", 0);
        } else if (output.getPosition("TURRET_AIM") < turretLower && value < 0) {
            output.setMotor("TURRET_AIM", 0);
        } else {
            output.setMotor("TURRET_AIM", value);
        }
    }

    private ArrayList<double[]> initTable(String path) {
        ArrayList<double[]> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("/home/lvuser/deploy/"+path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                double[] entry = {Double.parseDouble(items[0]), Double.parseDouble(items[1])};
                list.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void findAngle(){
        double dist = 7;
        double goalHeight = 7;
        double cameraHeight = 1.9167;//0.9583; //23in
        double aTwo = cameraTable.getEntry("ty").getDouble(0);
        //49.477

        double value = ((Math.atan((goalHeight - cameraHeight) / dist) * 180) / Math.PI) - aTwo;
        SmartDashboard.putNumber("A2", aTwo);
        SmartDashboard.putNumber("A1", value);
    }

    private double getDistance(){
        double goalHeight = 7;
        double cameraHeight = 1.9167;//0.9583; //23in
        double aTwo = cameraTable.getEntry("ty").getDouble(0); //-8.68;
        double aOne = 24.9;

        double dist = ((goalHeight - cameraHeight)) / Math.tan((aOne + aTwo) * Math.PI / 180);

        SmartDashboard.putNumber("Distance", dist);

        return dist;
    }

    private double pointLerp(ArrayList<double[]> points, double value, double def){
        double fin = def;
        if(shotDistanceTable.size() < 2){
            return fin;
        }

        for(int i = 1; i < points.size(); i++){
            double[] pointTwo = points.get(i);
            if(pointTwo[0] < value){
                continue;
            }
            double[] pointOne = points.get(i-1);
            fin = mapRange(pointOne[0], pointTwo[0], pointOne[1], pointTwo[1], value);
            break;
        }

        if(value > points.get(points.size()-1)[0]){
            double[] pointTwo = points.get(points.size()-1);
            double[] pointOne = points.get(points.size()-2);
            fin = mapRange(pointOne[0], pointTwo[0], pointOne[1], pointTwo[1], value);
        }

        return fin;
    }

    private double mapRange(double a1, double a2, double b1, double b2, double s){
		return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
	}
}