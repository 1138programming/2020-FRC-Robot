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


public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private final TalonSRX Intake;
  public static final int KIntakeTalon = 8;
  public static double KIntakeSpeed = 1;
  
  public Intake() {
    Intake = new TalonSRX(KIntakeTalon);
    Shifter1 = new Solenoid(KSolenoid1); 
    Shifter2 = new Solenoid(kSolenoid2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void move(double IntakeSpeed){
    Intake.set(ControlMode.PercentOutput, IntakeSpeed);
  }

  public void stop(){
    Intake.set(ControlMode.PercentOutput, 0.0);

  public void setIntakePosition(SolenoidState state){
      Shifter1.set(state == SolenoidState.ACTIVE);
      Shifter2.set(state == SolenoidState.ACTIVE);
    }
  }
}
