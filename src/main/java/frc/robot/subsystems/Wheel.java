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

public class Wheel extends SubsystemBase {
  /**
   * Creates a new Wheel.
   */
  private final TalonSRX Wheel;
  public static final int KWheel = 1;

  public Wheel() {
    Wheel = new TalonSRX(KWheel);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double WheelSpeed) {
    Wheel.set(ControlMode.PercentOutput, WheelSpeed);
  }
}