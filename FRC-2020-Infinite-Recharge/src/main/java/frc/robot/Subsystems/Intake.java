package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;

public class Intake implements Subsystem{
    private In input = new In(SubsystemID.INTAKE);
    private Out output = new Out(SubsystemID.INTAKE);

    private double intakeSpeed = Double.parseDouble(output.getAttribute("intake_speed"));
    private double hopperSpeed = Double.parseDouble(output.getAttribute("hopper_speed"));
    private double kickerSpeed = Double.parseDouble(output.getAttribute("kicker_speed"));

    @Override
    public void robotInit() {
        System.out.println("Intake init");
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
        if(input.getButton("RAISE", "OPERATOR")){
            output.setSolenoid("INTAKE_RAISE", true);
        }else{
            output.setSolenoid("INTAKE_RAISE", false);
        }

        if(input.getButton("INTAKE_IN", "DRIVE")){
            output.setMotor("INTAKE_WHEELS", intakeSpeed);
            output.setMotor("HOPPER", hopperSpeed);
        }else if(input.getButton("INTAKE_OUT", "DRIVE")){
            output.setMotor("INTAKE_WHEELS", -intakeSpeed);
        }else{
            output.setMotor("INTAKE_WHEELS", 0);
            output.setMotor("HOPPER", 0);
        }

        if(input.getButton("SHOOT", "OPERATOR")){
            output.setMotor("KICKER_WHEEL", kickerSpeed);
            output.setMotor("HOPPER", hopperSpeed);
        }else if(input.getButton("UNJAM", "OPERATOR")){
            output.setMotor("KICKER_WHEEL", -kickerSpeed);
            output.setMotor("HOPPER", -hopperSpeed);
        }else{
            output.setMotor("KICKER_WHEEL", 0);
            output.setMotor("HOPPER", 0);
        }
    }
}