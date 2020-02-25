package frc.robot.Framework.IO.Out.Motors;

import org.w3c.dom.*;

import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderBase;
import frc.robot.Framework.IO.In.Sensors.Encoders.EncoderWrapper;
import frc.robot.Framework.IO.Out.Motors.MotorBase;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.MotorGroup;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.SparkController;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.SparkMaxController;
import frc.robot.Framework.IO.Out.Motors.MotorTypes.TalonController;
import frc.robot.Framework.Util.CommandMode;
import frc.robot.Framework.Util.PID.PIDController;

public class MotorWrapper implements MotorBase{

    private MotorBase motor;
    private Element motorElement;

    private EncoderWrapper encoder;

    private PIDController pidController = new PIDController();

    public MotorWrapper(Element element) {
        motorElement = element;
        String id = motorElement.getAttribute("id");
        int port = Integer.parseInt(motorElement.getAttribute("port"));
        motor = getMotorType(motorElement.getAttribute("controller"), port);

        if (motor == null) {
            System.out.println("For motor: " + id + " motor controller type: " + motorElement.getAttribute("controller") + " was not found!");
            return;
        }

        if (motorElement.hasAttribute("inverted")) {
            motor.setInverted(Boolean.parseBoolean(motorElement.getAttribute("inverted")));
        }
    }

    public MotorWrapper(Element groupElement, boolean groupFlag) {
        String groupID = groupElement.getAttribute("id");
        NodeList groupMotorNodes = groupElement.getElementsByTagName("motor");
        MotorGroup group = new MotorGroup();
        for (int o = 0; o < groupMotorNodes.getLength(); o++) {
            Node currentMotor = groupMotorNodes.item(o);
            if (currentMotor.getNodeType() == Node.ELEMENT_NODE) {
                Element motorElement = (Element) currentMotor;
                int port = Integer.parseInt(motorElement.getAttribute("port"));
                MotorBase controllerType = getMotorType(motorElement.getAttribute("controller"), port);

                if (controllerType != null) {
                    group.addMotor(new MotorWrapper(motorElement));
                } else {
                    System.out.println("For motor in group: " + groupID + " motor controller type: " + controllerType
                            + " was not found!");
                    continue;
                }
            }
        }
        motor = group;
        if (groupElement.hasAttribute("inverted")) {
            motor.setInverted(Boolean.parseBoolean(groupElement.getAttribute("inverted")));
        }
    }

    private MotorBase getMotorType(String controllerType, int port) {
        if (controllerType.equals("SPARK")) {
            return new SparkController(port);
        } else if (controllerType.equals("TALON")) {
            return new TalonController(port);
        } else if(controllerType.equals("SPARK_MAX")){
            return new SparkMaxController(port);
        }else{
            return null;
        }
    }

    private void parseEncoder(Element motor){
        NodeList encoders = motor.getElementsByTagName("encoder");
        for (int i = 0; i< encoders.getLength(); i++) {
            Node currentEncoder = encoders.item(i);
            if (currentEncoder.getNodeType() == Node.ELEMENT_NODE) {
                Element encoderElement = (Element) currentEncoder;
                String type = encoderElement.getAttribute("type");
                int portOne = Integer.parseInt(encoderElement.getAttribute("port_one"));
                int portTwo = Integer.parseInt(encoderElement.getAttribute("port_one"));
                encoder = new EncoderWrapper(getEncoderType(type, portOne, portTwo));
            }
        }
    }

    private EncoderBase getEncoderType(String encoderType, int portOne, int portTwo){
        if (encoderType.equals("TOUGH_BOX")) {
            return ;
        }else if(encoderType.equals("NEO")){
            return ;
        }else if(encoderType.equals("775")){

        }else{
            return null;
        }
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void setInverted(boolean invert) {
        motor.setInverted(invert);
    }

    public String getAttribute(String attribute) {
        return motorElement.getAttribute(attribute);
    }

    @Override
    public void set(double setpoint, CommandMode mode) {
        motor.set(setpoint, mode);
    }

    @Override
    public void setPID(double kP, double kI, double kD, double kF) {
        motor.setPID(kP, kI, kD, kF);
    }
}