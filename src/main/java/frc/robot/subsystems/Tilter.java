/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;
import edu.wpi.first.wpilibj.Solenoid;

public class Tilter extends SubsystemBase {
  
  private Solenoid leftTilter;
  private Solenoid rightTilter;
  public static final int KLeftTilterSolenoid = 7;
  public static final int KRightTilterSolenoid = 8;

  public Tilter() {
    leftTilter = new Solenoid(KLeftTilterSolenoid);
    rightTilter = new Solenoid(KRightTilterSolenoid);
  }

  public void setTilterPosition(SolenoidState state) {
    leftTilter.set(state == SolenoidState.ACTIVE);
    rightTilter.set(state == SolenoidState.ACTIVE);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
