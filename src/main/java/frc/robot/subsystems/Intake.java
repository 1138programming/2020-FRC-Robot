/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.enums.SolenoidState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private final TalonSRX intake;
  public static final int KIntakeTalon = 8;
  public static double KIntakeSpeed = 1;
  private Solenoid leftSolenoid;
  private Solenoid rightSolenoid;
  public static final int KLeftSolenoid = 2;
  public static final int KRightSolenoid = 3;
  
  public Intake() {
    intake = new TalonSRX(KIntakeTalon);
    leftSolenoid = new Solenoid(KLeftSolenoid); 
    rightSolenoid = new Solenoid(KRightSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double IntakeSpeed){
    intake.set(ControlMode.PercentOutput, IntakeSpeed);
  }

  public void stop() {
    intake.set(ControlMode.PercentOutput, 0.0);
  }

  public void setIntakePosition(SolenoidState state){
    leftSolenoid.set(state == SolenoidState.ACTIVE);
    rightSolenoid.set(state == SolenoidState.ACTIVE);
  }
}
