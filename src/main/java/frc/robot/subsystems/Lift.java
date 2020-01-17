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


public class Lift extends SubsystemBase {
  /**
   * Creates a new Lift.
   */
  private final TalonSRX LiftLeft, LiftRight;
  public static final int KLiftLeft = 1;
  public static final int KLiftRight = 2;
  
  public Lift() {
    LiftLeft = new TalonSRX(KLiftLeft);
    LiftRight = new TalonSRX(KLiftRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double LiftLeftSpeed, double LiftRightSpeed){
    LiftLeft.set(ControlMode.PercentOutput, LiftLeftSpeed);
    LiftRight.set(ControlMode.PercentOutput,LiftRightSpeed);
  }
}
