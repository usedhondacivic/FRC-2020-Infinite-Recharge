package frc.robot.Subsystems;

import frc.robot.Framework.Subsystem;
import frc.robot.Framework.IO.In.In;
import frc.robot.Framework.IO.Out.Out;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import com.revrobotics.ColorSensorV3;

public class ColorWheel implements Subsystem {
    private In input = new In(SubsystemID.PANEL);
    private Out output = new Out(SubsystemID.PANEL);

    private double spinnerSpeed = Double.parseDouble(output.getAttribute("speed"));

    @Override
    public void robotInit() {
        System.out.println("Color wheel init");
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
        if (input.getButton("ACTIVATE", "OPERATOR")) {
            output.setSolenoid("PANEL_RAISE", true);
            output.setMotor("PANEL_SPINNER", spinnerSpeed);
        } else {
            output.setSolenoid("PANEL_RAISE", false);
            output.setMotor("PANEL_SPINNER", 0);
        }
    }

    public static class FieldColor {
        static int INVALID = -1;
        int RED = 0;
        int YELLOW = 1;
        int BLUE = 2;
        int GREEN = 3;
    }

    static double[][] MinMax = { // min,max...
            { 0.38, 0.53, 0.33, 0.42, 0.13, 0.20 }, // red
            { 0.31, 0.33, 0.48, 0.565, 0.11, 0.175 }, // yellow
            { 0.11, 0.24, 0.42, 0.47, 0.29, 0.46 }, // blue
            { 0.16, 0.25, 0.51, 0.58, 0.22, 0.27 } // green
    };

    FieldColor color = new FieldColor();
    private char gameData;
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    public int colorCorrection() {

        Color detectedColor = colorSensor.getColor();
        int proximity = colorSensor.getProximity();
        // Push to smart dashboard
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Proximity", proximity);
        // RGB values for identfying the colors from a proximity of 3-13 inches about

        // Get game data from drivers station
        gameData = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
        // Checks the turn of the turret using gameData
        switch (gameData) {
        case 'R':
            // Blue case code
            return colorMovement(colorSensor(detectedColor), color.RED);

        case 'Y':
            // Green case code
            return colorMovement(colorSensor(detectedColor), color.YELLOW);

        case 'B':
            // Red case code
            return colorMovement(colorSensor(detectedColor), color.BLUE);

        case 'G':
            // Yellow case code
            return colorMovement(colorSensor(detectedColor), color.GREEN);

        default:

            return 0;
        }
    }

    // MOD math for movement modification
    public static int colorMovement(Integer sensorColor, Integer targetColor) {
        System.out.println(targetColor + ", " + sensorColor + " = " + (targetColor - sensorColor));
        System.out.println((targetColor - sensorColor) + " % 2 = " + (targetColor - sensorColor) % 2);
        if (sensorColor == FieldColor.INVALID) {
            System.out.println("Invalid");
            return 0;
        }
        int i = targetColor - sensorColor;
        if (i == 0) {
            System.out.println("Same Color");
            return 0;
        }
        if (i % 2 == 0) {
            System.out.println("Faster Either Way");
            return 1;
        }
        if (i == -1) {
            System.out.println("One Away Positive");
            return 1;
        }
        if (i == 1) {
            System.out.println("Own Away Negitive");
            return -1;
        }
        System.out.println("Basic Case");
        return i % 2;
    }

    public static int colorSensor(Color detectedColor) {
        // The minus length-2 might be an issue but in testing it doesnt work without it
        for (int i = 0; i < MinMax[0].length - 2; i++) {
            boolean red = detectedColor.red > MinMax[i][0] && detectedColor.red < MinMax[i][1];
            boolean green = detectedColor.green > MinMax[i][2] && detectedColor.green < MinMax[i][3];
            boolean blue = detectedColor.blue > MinMax[i][4] && detectedColor.blue < MinMax[i][5];
            if (red && green & blue) {
                return i;
            }
        }
        return FieldColor.INVALID;
    }
}