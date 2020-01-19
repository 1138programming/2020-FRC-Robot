package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;

import frc.robot.enums.ColorLabel;
import frc.robot.enums.RotationDirection;
import frc.robot.enums.SolenoidState;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

import frc.robot.commands.Wheel.GoToColor;
import frc.robot.commands.Wheel.Spin;
import frc.robot.commands.Wheel.WheelStop;

public class Wheel extends SubsystemBase {

  //Create the talons
  private final CANSparkMax WheelMotor;
  public static final int KWheel = 1;

  //Create the solenoids
  private final Solenoid WheelSolenoid;
  public static final int KWheelSolenoid = 7;

  //Create the color sensors
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  //Create the color targets
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  //create variables, enums, etc. 
  public SolenoidState SolenoidState = SolenoidState.DEFAULT;

  //code by Corey

  public Wheel() {
    //instantiate the talons
    WheelMotor = new CANSparkMax(KWheel, CANSparkMaxLowLevel.MotorType.kBrushless);

    //instantiate the solenoid
    WheelSolenoid = new Solenoid(KWheelSolenoid);

    //set the colors of the matcher
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //moves the wheel
  public void moveMotor(double WheelSpeed) {
    WheelMotor.set(WheelSpeed);
  }

  public void moveSolenoid(SolenoidState state) {
    SolenoidState = state;
    WheelSolenoid.set(SolenoidState == SolenoidState.ACTIVE);
  }

  //gets the direction we need to go to get to our target
  public RotationDirection getRotationDirection(ColorLabel Current, ColorLabel Target) {
    // Make sure both colors are known
    if (Current == ColorLabel.UNKNOWN || Target == ColorLabel.UNKNOWN) {
      return RotationDirection.NONE;
    }

    // Get the number associated with each color
    int targetNum = Target.getValue();
    int currentNum = Current.getValue();

    // Make sure the current color is not the target color
    if (targetNum == currentNum) {
      return RotationDirection.NONE;
    }

    // Gets distances the wheel would have to turn in both directions
    int clockwiseDistance = targetNum - currentNum;
    int counterclockwiseDistance = currentNum - targetNum;

    // Normalizes between 0 and 3
    if (clockwiseDistance < 0) {
      clockwiseDistance += 4;
    }

    // Normalizes between 0 and 3
    if (counterclockwiseDistance < 0) {
      counterclockwiseDistance += 4;
    }

    // Compares the two distances, returning the direction associated with the shorter one
    if (clockwiseDistance < counterclockwiseDistance) {
      return RotationDirection.CLOCKWISE;
    } else {
      return RotationDirection.COUNTERCLOCKWISE;
    }
  }

  //gets the color we are on from the color sensor
  public ColorLabel getColor(){
    Color detectedColor = m_colorSensor.getColor();
    double IR = m_colorSensor.getIR();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    ColorLabel color;
    String colorString;

    if (match.color == kBlueTarget) {
      color = ColorLabel.BLUE;
      colorString = "blue";
    } else if (match.color == kRedTarget) {
      color = ColorLabel.RED;
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      color = ColorLabel.GREEN;
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      color = ColorLabel.YELLOW;
      colorString = "Yellow";
    } else {
      color = ColorLabel.UNKNOWN;
      colorString = "Unknown";
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putNumber("IR", IR);

    return color;
  }
}
