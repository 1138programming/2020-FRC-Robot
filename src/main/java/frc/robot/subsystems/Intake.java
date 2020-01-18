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
import edu.wpi.first.wpilibj.Solenoid;


public class Intake extends SubsystemBase {
  /*
   * Creates a new Intake.
   */
  public static final int kSolenoid3 = 3;
  public static final int kSolenoid4 = 4;

  private final TalonSRX Intake;
  private Solenoid IntakeSolenoid1;
  private Solenoid IntakeSolenoid2;
  public static final int KIntakeTalon = 8;
  public static double KIntakeSpeed = 1;

  
  public Intake() {
    Intake = new TalonSRX(KIntakeTalon);
    IntakeSolenoid1 = new Solenoid(kSolenoid3);
    IntakeSolenoid2 = new Solenoid(kSolenoid4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double IntakeSpeed){
    Intake.set(ControlMode.PercentOutput, IntakeSpeed);
  }

  public void setIntakePosition(boolean position) {
    IntakeSolenoid1.set(position);
    IntakeSolenoid2.set(position);
  }

  public void Solinoidstop(){
    IntakeSolenoid1.close();
    IntakeSolenoid2.close();
  }
}
