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


public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */
  private final TalonSRX Indexer;
  public static final int KIndexer = 7;
  public static double KIndexerSpeed = 1;
  
  public Indexer() {
    Indexer = new TalonSRX(KIndexer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double IndexerSpeed) {
    Indexer.set(ControlMode.PercentOutput, IndexerSpeed);
  }
}
