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



public class Flywheel extends SubsystemBase {
  /**
   * Creates a new Flywheel.
   */
  private final TalonSRX Flywheel1, Flywheel2;
  public static final int KFlywheel1 = 1;
  public static final int KFlywheel2 = 2;

  public Flywheel() {
    Flywheel1 = new TalonSRX(KFlywheel1);
    Flywheel2 = new TalonSRX(KFlywheel2);

    Flywheel2.setInverted(true);
    Flywheel2.follow(Flywheel1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double FlywheelSpeed) {
    Flywheel1.set(ControlMode.PercentOutput, FlywheelSpeed);
    Flywheel2.set(ControlMode.PercentOutput, FlywheelSpeed);
  }
}
