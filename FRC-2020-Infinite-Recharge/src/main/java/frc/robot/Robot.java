package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Framework.Subsystems;
import frc.robot.Subsystems.Chassis;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.Climber;
import frc.robot.Subsystems.Macro;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    Subsystems.add(new Chassis());
    Subsystems.add(new Climber());
    Subsystems.add(new Shooter());
    Subsystems.add(new Macro());
    Subsystems.robotInit();
  }

  @Override
  public void robotPeriodic() {
    Subsystems.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    Subsystems.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    Subsystems.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    Subsystems.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    Subsystems.teleopPeriodic();
  }

  @Override
  public void testPeriodic() {
  }
}