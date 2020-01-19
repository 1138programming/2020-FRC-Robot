package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.enums.SolenoidState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;


public class Intake extends SubsystemBase {

  //Create talons
  private final TalonSRX Intake;
  public static final int KIntakeTalon = 8;

  //Create solenoids
  private Solenoid IntakeSolenoid1;
  private Solenoid IntakeSolenoid2;
  public static final int kSolenoid3 = 3;
  public static final int kSolenoid4 = 4;

  //Variables, enums, etc
  public static double KIntakeSpeed = 1;
  public SolenoidState SolenoidState;
  public static String SolenoidStatus;

  
  public Intake() {
    //instantiate talons
    Intake = new TalonSRX(KIntakeTalon);

    //instantiate solenoids
    IntakeSolenoid1 = new Solenoid(kSolenoid3);
    IntakeSolenoid2 = new Solenoid(kSolenoid4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("SolenoidState", SolenoidStatus);
  }

  //move intake
  public void move(double IntakeSpeed){
    Intake.set(ControlMode.PercentOutput, IntakeSpeed);
  }

  //sets the position of the intake to extended (active) or retracted 
  public void setIntakePosition(SolenoidState position) {
    SolenoidStatus = position.name(); 
    if(position == SolenoidState.ACTIVE) {
      IntakeSolenoid1.set(true);
      IntakeSolenoid2.set(true);
    }
    else if(position == SolenoidState.DEFAULT) {
      IntakeSolenoid1.set(false);
      IntakeSolenoid2.set(false);
    }
    else {

    }
  }

  //gets current state of the intake
  public SolenoidState getIntakeState() {
    return SolenoidState; 
  }
}
