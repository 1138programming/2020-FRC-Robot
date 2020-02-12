package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.ColorLabel;
import frc.robot.enums.RotationDirection;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.DriverStation;

public class Wheel extends SubsystemBase {
  //Create the talons
  private final VictorSPX wheelMotor;

  //Create the color sensors
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  //code by Corey
  private static String gameData;
  private static ColorLabel targetColor;

  public Wheel() {
    //instantiate the talons
    wheelMotor = new VictorSPX(KWheelTalon);

    //set the colors of the matcher
    m_colorMatcher.addColorMatch(KBlueTarget);
    m_colorMatcher.addColorMatch(KGreenTarget);
    m_colorMatcher.addColorMatch(KRedTarget);
    m_colorMatcher.addColorMatch(KYellowTarget); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Moves the wheel mechanism directly
   * 
   * @param speed Speed to move the wheel mechanism at
   */
  public void move(double speed) {
    wheelMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Gets the color that the color sensor is detecting
   * 
   * @return  The color
   */
  public ColorLabel getColor(){
    Color detectedColor = m_colorSensor.getColor();
    double IR = m_colorSensor.getIR();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    ColorLabel color;
    String colorString;
    if(match.confidence > .95) {
    if (match.color == KBlueTarget) {
      color = ColorLabel.BLUE;
      colorString = "Blue";
    } else if (match.color == KRedTarget) {
      color = ColorLabel.RED;
      colorString = "Red";
    } else if (match.color == KGreenTarget) {
      color = ColorLabel.GREEN;
      colorString = "Green";
    } else if (match.color == KYellowTarget) {
      color = ColorLabel.YELLOW;
      colorString = "Yellow";
    } else {
      color = ColorLabel.UNKNOWN;
      colorString = "Unknown";
    }
  }
  else {
    color = ColorLabel.UNKNOWN;
    if (match.color == KBlueTarget) {
      colorString = "Blue";
    } else if (match.color == KRedTarget) {
      colorString = "Red";
    } else if (match.color == KGreenTarget) {
      colorString = "Green";
    } else if (match.color == KYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }  
  }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putNumber("IR", IR);

    return color;
  }

  /**
   * Gets the direction in which the wheel mechanism needs to rotate to get to the target color
   * 
   * @param Current The current color the wheel is on
   * @param Target  The target color for the wheel
   * @return        The direction to rotate
   */
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

  /**
   * Gets the target color for the wheel from the FMS
   * 
   * @return  The target color
   */
  public ColorLabel getTargetColor() {

    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
        if(gameData.charAt(0) == 'B') {
          targetColor = ColorLabel.BLUE;
        }
        if(gameData.charAt(0) == 'G') {
          targetColor = ColorLabel.GREEN;
        }
        if(gameData.charAt(0) == 'R') {
          targetColor = ColorLabel.RED;
        }
        if(gameData.charAt(0) == 'Y') {
          targetColor = ColorLabel.YELLOW;
        }
    } else {
      targetColor = ColorLabel.UNKNOWN;
    }
    return targetColor;
  }
}
