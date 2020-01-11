package frc.robot.Framework.IO.In;

public interface ControllerBase{

    public boolean getButton(Integer buttonID);

    public double getAxis(Integer axisID);
}