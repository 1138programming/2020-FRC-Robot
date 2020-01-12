/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.enums.ColorLabel;
import frc.robot.enums.RotationDirection;

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
  /**
   * Creates a new Wheel.
   */
  private final TalonSRX Wheel;
  public static final int KWheel = 1;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  //code by Corey

  public Wheel() {
    Wheel = new TalonSRX(KWheel);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 
    //code by Corey 
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void move(double WheelSpeed) {
    Wheel.set(ControlMode.PercentOutput, WheelSpeed);
  }

  public RotationDirection getRotationDirection(ColorLabel Current, ColorLabel Target) {
    int T = Target.getValue();
    int C = Current.getValue();
    int TC = T - C;
    int CT = C - T;

    if (TC < 0) {
      TC += 4;
    }
    if (CT < 0) {
      CT += 4;
    }

    if (TC < CT) {
      return RotationDirection.CLOCK;
    } else {
      return RotationDirection.COUNTERCLOCK;
    }
  }

  public ColorLabel getColor(){

    Color detectedColor = m_colorSensor.getColor();
    double IR = m_colorSensor.getIR();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    ColorLabel color;
    String colorString;

    if (match.color == kBlueTarget) {
      color = ColorLabel.BLUE;
      colorString = "blue";
    }
    else if (match.color == kRedTarget) {
      color = ColorLabel.RED;
      colorString = "Red";
    }
    else if (match.color == kGreenTarget) {
      color = ColorLabel.GREEN;
      colorString = "Green";
    }
    else if (match.color == kYellowTarget) {
      color = ColorLabel.YELLOW;
      colorString = "Yellow";
    }
    else {
      color = ColorLabel.BLUE;
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
