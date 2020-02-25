package frc.robot.Framework.Util.PID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PIDController{
    private PIDMode profile = PIDMode.POSITION;
    private Map<PIDMode, PIDProfile> profiles = new HashMap<>();

    private double setpoint = 0;
    private double measuredValue = 0;

    ScheduledExecutorService scheduler =Executors.newSingleThreadScheduledExecutor();

    private static class PIDProfile extends Thread{
        private double kP, kI, kD, kF = 0;
        private double output = 0;
        private double measuredValue;

        public PIDProfile(double kP, double kI, double kD, double kF){
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = kF;
        }

        public void run(){
            //output
        }
    }

    public PIDController(){}

    public void addProfile(PIDMode type, double kP, double kI, double kD, double kF){
        profiles.put(type, new PIDProfile(kP, kI, kD, kF));
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
    }
}