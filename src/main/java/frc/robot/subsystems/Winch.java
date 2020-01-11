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


public class Winch extends SubsystemBase {
  /**
   * Creates a new Winch.
   */
  private final TalonSRX WinchLeft, WinchRight;
  public static final int KWinchLeft = 1;
  public static final int KWinchRight = 2;
  
  public Winch() {
    WinchLeft = new TalonSRX(KWinchLeft);
    WinchRight = new TalonSRX(KWinchRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double WinchLeftSpeed, double WinchRightSpeed){
    WinchLeft.set(ControlMode.PercentOutput, WinchLeftSpeed);
    WinchRight.set(ControlMode.PercentOutput,WinchRightSpeed);
  }
}
