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
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.commands.Base.DriveWithJoysticks;
import frc.robot.enums.ShiftState;

public class Base extends SubsystemBase {
  /**
   * Creates a new Base.
   */
 
  private final TalonSRX leftFront, leftBack, rightFront, rightBack;
  public static final int KLeftFrontTalon = 1;
  public static final int KLeftBackTalon = 2;
  public static final int KRightFrontTalon = 3;
  public static final int KRightBackTalon = 4;
  private ShiftState baseShiftState = ShiftState.HIGH;
  private final DoubleSolenoid shifterSolenoid;
  public static final int KShifterSolenoidLow = 0;
  public static final int KShifterSolenoidHigh = 1;
  public static final int TicksPerRotation = 4600; //conversion factor that we have to find
  public static final int FreeSpeed = (6380/3600) * TicksPerRotation; 
  public static final double LowGear = 62/8; // Numbers from the Quran, absolutely 100% true
  public static final double HighGear = 32/24; // Numbers from the Quran, absolutely 100% true

  public Base() {
    leftFront = new TalonSRX(KLeftFrontTalon);
    leftBack = new TalonSRX(KLeftBackTalon);
    rightFront = new TalonSRX(KRightFrontTalon);
    rightBack = new TalonSRX(KRightBackTalon);

    leftBack.follow(leftFront);
    rightBack.follow(rightFront);
    
    shifterSolenoid = new DoubleSolenoid(KShifterSolenoidLow, KShifterSolenoidHigh);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void move(double leftSpeed, double rightSpeed) {
    leftFront.set(ControlMode.PercentOutput, leftSpeed);
    rightFront.set(ControlMode.PercentOutput, rightSpeed);
  } 
   
  public double getLeftFrontEncoder() {
    return leftFront.getSelectedSensorPosition();
  }

  public double getRightFrontEncoder() {
    return rightFront.getSelectedSensorPosition();
  }

  public void SetBaseShift(ShiftState state) {
    if(baseShiftState == ShiftState.LOW) {
      shifterSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else{
      shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public ShiftState GetBaseShift() {
    return baseShiftState;
  }

  public void zeroEncoders(){
    leftFront.getSensorCollection().setQuadraturePosition(0, 0);
    rightFront.getSensorCollection().setQuadraturePosition(0, 0);
    leftBack.getSensorCollection().setQuadraturePosition(0, 0);
    rightBack.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public int getShiftSpeed() {
    return (int)(FreeSpeed / (HighGear + LowGear));
  }
}
