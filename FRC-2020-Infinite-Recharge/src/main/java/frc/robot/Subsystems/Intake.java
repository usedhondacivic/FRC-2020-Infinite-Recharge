package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Intake implements Subsystem{
    private In input = new In(SubsystemID.INTAKE);
    private Out output = new Out(SubsystemID.INTAKE);

    private double intakeSpeed = Double.parseDouble(output.getAttribute("intake_speed"));
    private double indexer_speed = Double.parseDouble(output.getAttribute("indexer_speed"));
    private double kicker_speed = Double.parseDouble(output.getAttribute("kicker_Speed"));

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        if(input.getButton("INTAKE_IN", "DRIVE")){

        }
    }
}