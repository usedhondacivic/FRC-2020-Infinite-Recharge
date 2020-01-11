package frc.robot.Framework.IO.Motors;

public interface MotorBase{
    public void init(int port);

    public void set(double speed);
}