package frc.robot.Framework.IO.In.Sensors.Encoders;

import frc.robot.Framework.Util.PID.PIDController;

public class EncoderWrapper{
    private EncoderBase encoder;
    public PIDController pidController = new PIDController();

    public EncoderWrapper(EncoderBase base){
        this.encoder = base;
    }
}