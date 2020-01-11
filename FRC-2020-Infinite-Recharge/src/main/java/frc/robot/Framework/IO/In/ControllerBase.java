package frc.robot.Framework.IO.In;

public interface ControllerBase{

    public boolean getButton(String buttonID);

    public double getAxis(String axisID);
}