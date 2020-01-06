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

import frc.robot.commands.Base.DriveWithJoysticks;

public class Base extends SubsystemBase {
  /**
   * Creates a new Base.
   */

  private final TalonSRX leftFront, leftBack, rightFront, rightBack;
  public static final int KLeftFrontTalon = 1;
  public static final int KLeftBackTalon = 2;
  public static final int KRightFrontTalon = 3;
  public static final int KRightBackTalon = 4;

  public Base() {
    leftFront = new TalonSRX(KLeftFrontTalon);
    leftBack = new TalonSRX(KLeftBackTalon);
    rightFront = new TalonSRX(KLeftFrontTalon);
    rightBack = new TalonSRX(KLeftFrontTalon);

    leftBack.follow(leftFront);
    rightBack.follow(rightFront);

    setDefaultCommand(new DriveWithJoysticks());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void move(double leftSpeed, double rightSpeed) {
    leftFront.set(ControlMode.PercentOutput, leftSpeed);
    rightFront.set(ControlMode.PercentOutput, rightSpeed);
  } 
}
