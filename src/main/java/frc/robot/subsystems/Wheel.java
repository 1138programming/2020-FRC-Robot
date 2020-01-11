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

public class Wheel extends SubsystemBase {
  /**
   * Creates a new Wheel.
   */
  private final TalonSRX Wheel;
  public static final int KWheel = 1;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor;
  

  public Wheel() {
    Wheel = new TalonSRX(KWheel);
    m_colorSensor = new ColorSensorV3(i2cPort);
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
      colorString = "blue";
    }
    else if (match.color == kGreenTarget) {
      color = ColorLabel.GREEN;
      colorString = "blue";
    }
    else if (match.color == kYellowTarget) {
      color = ColorLabel.YELLOW;
      colorString = "blue";
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
