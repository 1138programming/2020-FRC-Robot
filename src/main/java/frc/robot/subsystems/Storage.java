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

public class Storage extends SubsystemBase {
  /**
   * Creates a new Storage.
   */
  private final TalonSRX StorageMotor;
  public static final int KStorage = 9;
  public static double KStorageSpeed = 1;

  public Storage() {
    StorageMotor = new TalonSRX(KStorage);
  }

  public void move(double StorageSpeed) {
    StorageMotor.set(ControlMode.PercentOutput, StorageSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
