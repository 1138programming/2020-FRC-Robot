package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.BaseState;
import frc.robot.commands.Base.BaseShiftLow;
import frc.robot.controller.LinearProfiler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Base extends SubsystemBase {
  //Creating the Talons
  private final TalonFX leftFront, leftBack, rightFront, rightBack;

  //Linear profiler for both sides of the base
  private final LinearProfiler leftProfiler, rightProfiler;

  //Creating the Solenoids
  private final DoubleSolenoid shifter;

  //Sets the default state to medium
  private BaseState baseState = BaseState.MEDIUM;
  
  //Variables
  private static final int KTicksPerRotation = 2048; //conversion factor that we have to find
  private static final int KFreeSpeedRPM = 6380; // Free speed of the base motors in RPM (check in the Quran)
  private static final int KFreeSpeed = KFreeSpeedRPM / 60; // Free speed of the base in ticks per second
  private static final double KLowGear = (8.0 / 62.0) * (24.0 / 32.0) * (16.0 / 50.0); // Numbers from the Quran, absolutely 100% true
  private static final double KHighGear = (8.0 / 62.0) * (24.0 / 32.0) * (36.0 / 30.0); // Numbers from the Quran, absolutely 100% true
  private static final double KRotationsPerTickLow = 1 / (KLowGear * KTicksPerRotation);
  private static final double KRotationsPerTickHigh = 1 / (KHighGear * KTicksPerRotation);
  private static final double KShiftSpeed = KFreeSpeed / ((KRotationsPerTickHigh + KRotationsPerTickLow) * KTicksPerRotation);

  private double lastLeftSpeed = 0;
  private double lastRightSpeed = 0;
  private double leftAccel = 0;
  private double rightAccel = 0;
  private double rotationsPerTick = KRotationsPerTickHigh;

  public Base() {
    //instantiating the talons
    leftFront = new TalonFX(KLeftFrontTalon);
    leftBack = new TalonFX(KLeftBackTalon);
    rightFront = new TalonFX(KRightFrontTalon);
    rightBack = new TalonFX(KRightBackTalon);

    rightFront.setInverted(true);
    rightBack.setInverted(true);

    // Slaving the talons
    leftBack.follow(leftFront);
    rightBack.follow(rightFront);

    leftBack.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    rightFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

    zeroEncoders();
    
    // Set up profilers for both sides
    leftProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0.02);
    rightProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0.02);
    leftProfiler.setTolerance(50, 20);
    rightProfiler.setTolerance(50, 20);

    // Instantiating the solenoid
    shifter = new DoubleSolenoid(KBaseShifterForwardChannel, KBaseShifterReverseChannel);
  }

  @Override
  public void periodic() {
    double leftSpeed = getLeftSpeed();
    double rightSpeed = getRightSpeed();

    leftAccel = (leftSpeed - lastLeftSpeed) * 5;
    rightAccel = (rightSpeed - lastRightSpeed) * 5;

    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Base left target position", leftProfiler.getTargetPos());
    SmartDashboard.putNumber("Base right target position", rightProfiler.getTargetPos());
    SmartDashboard.putNumber("Base left position", getLeftEncoder());
    SmartDashboard.putNumber("Base right position", getRightEncoder());
    SmartDashboard.putNumber("Base left target velocity", leftProfiler.getTargetVel());
    SmartDashboard.putNumber("Base right target velocity", rightProfiler.getTargetVel());
    SmartDashboard.putNumber("Base left velocity", getLeftSpeed());
    SmartDashboard.putNumber("Base right velocity", getRightSpeed());
    SmartDashboard.putNumber("Base left target accel", leftProfiler.getTargetAccel());
    SmartDashboard.putNumber("Base right target accel", rightProfiler.getTargetAccel());
    SmartDashboard.putNumber("Base left accel", getLeftAccel());
    SmartDashboard.putNumber("Base right accel", getRightAccel());

    lastLeftSpeed = leftSpeed;
    lastRightSpeed = rightSpeed;

    /*if(getTicksToShiftAt() >= KShiftSpeed) {
      setBaseState(BaseState.LOW);
    }*/
    autoShift();
  }

  /**
   * Moves the base directly
   * 
   * @param leftSpeed   Speed to move the left side at
   * @param rightSpeed  Speed to move the right side at
   */
  public void move(double leftSpeed, double rightSpeed) {
    if (baseState == BaseState.MEDIUM) {
      leftFront.set(ControlMode.PercentOutput, leftSpeed * KBaseMediumGear);
      rightFront.set(ControlMode.PercentOutput, rightSpeed * KBaseMediumGear);
    } else {
      leftFront.set(ControlMode.PercentOutput, leftSpeed);
      rightFront.set(ControlMode.PercentOutput, rightSpeed);
    }
  }

  /**
   * Sets the base shift to high, medium, or low
   * 
   * @param state State to set the base to
   */
  public void setBaseState(BaseState state) {
    baseState = state;
    
    if (baseState == BaseState.HIGH || baseState == BaseState.MEDIUM) {
      //shifter.set(true);
      shifter.set(DoubleSolenoid.Value.kForward);
      rotationsPerTick = KRotationsPerTickHigh;
    } else {
      shifter.set(DoubleSolenoid.Value.kReverse);
      rotationsPerTick = KRotationsPerTickLow;
    }
  }

  /**
   * Gets the base's state
   * 
   * @return State of the base
   */
  public BaseState getBaseState() {
    return baseState;
  }

  /**
   * Gets the encoder value of the left side
   * 
   * @return  Left encoder value
   */
  public double getLeftEncoder() {
    return (double)leftFront.getSelectedSensorPosition() * rotationsPerTick; //selected sensor (in raw sensor units) per 100ms
    //return (double)leftFront.getSensorCollection().getIntegratedSensorPosition();
  }

  /**
   * Gets the encoder value of the right side
   * 
   * @return  Right encoder value
   */
  public double getRightEncoder() {
    return (double)rightFront.getSelectedSensorPosition() * rotationsPerTick; //selected sensor (in raw sensor units) per 100ms
    //return (double)rightFront.getSensorCollection().getIntegratedSensorPosition();
  }

  /**
   * Zeroes the encoders on both sides
   */
  public void zeroEncoders() {
    /*leftFront.getSensorCollection().setQuadraturePosition(0, 0);
    rightFront.getSensorCollection().setQuadraturePosition(0, 0);
    leftBack.getSensorCollection().setQuadraturePosition(0, 0);
    rightBack.getSensorCollection().setQuadraturePosition(0, 0);*/

    /*leftFront.getSensorCollection().setIntegratedSensorPosition(0, 0);
    rightFront.getSensorCollection().setIntegratedSensorPosition(0, 0);
    leftBack.getSensorCollection().setIntegratedSensorPosition(0, 0);
    rightBack.getSensorCollection().setIntegratedSensorPosition(0, 0);*/

    leftFront.setSelectedSensorPosition(0);
    rightFront.setSelectedSensorPosition(0);
    leftBack.setSelectedSensorPosition(0);
    rightBack.setSelectedSensorPosition(0);
  }

  //Getters

  /**
   * Gets the speed at which we should shift from low gear to high gear. The relevant equation can be found here: https://www.chiefdelphi.com/t/frc-95-the-grasshoppers-2020-build-thread/368912/28
   * 
   * @return  Speed in ticks per 1 s
   */
  /*public double getShiftSpeed() {
    return KFreeSpeed / (KRotationsPerTickHigh + KRotationsPerTickLow);
  }*/

  /**
   * Gets the speed of the left side
   * 
   * @return Speed in ticks per 100 ms
   */
  public double getLeftSpeed() {
    return (double)leftFront.getSelectedSensorVelocity() * rotationsPerTick * 10; //selected sensor (in raw sensor units) per 100ms
  }

  /**
   * Gets the speed of the right side
   * 
   * @return Speed in ticks per 100 ms
   */
  public double getRightSpeed() {
    return (double)rightFront.getSelectedSensorVelocity() * rotationsPerTick * 10; //selected sensor (in raw sensor units) per 100ms
  }

  private void autoShift() {
    double maxVel = Math.max(getLeftSpeed(), getRightSpeed());
    if (maxVel >= KShiftSpeed) {
      setBaseState(BaseState.LOW);
    }
  }

  public double getLeftAccel() {
    return leftAccel;
  }

  public double getRightAccel() {
    return rightAccel;
  }

  public void setTarget(double leftTarget, double rightTarget) {
    leftProfiler.setTarget(leftTarget);
    rightProfiler.setTarget(rightTarget);
  }

  public void setTargetRelative(double leftTarget, double rightTarget) {
    leftProfiler.setTargetRelative(leftTarget);
    rightProfiler.setTargetRelative(rightTarget);
  }

  public double getLeftTarget() {
    return leftProfiler.getTarget();
  }

  public double getRightTarget() {
    return rightProfiler.getTarget();
  }

  public void setMaxVel(double maxVel) {
    leftProfiler.setMaxVel(maxVel);
    rightProfiler.setMaxVel(maxVel);
  }

  public void setMaxAccel(double maxAccel) {
    leftProfiler.setMaxAccel(maxAccel);
    rightProfiler.setMaxAccel(maxAccel);
  }

  public void initLinearMovement() {
    leftProfiler.init(getLeftEncoder());
    rightProfiler.init(getRightEncoder());
  }

  public void calculate() {
    double leftSpeed = leftProfiler.calculate(getLeftSpeed());
    double rightSpeed = rightProfiler.calculate(getRightSpeed());

    //double leftSpeed = leftProfiler.calculate(leftProfiler.getTargetPos());
    //double rightSpeed = rightProfiler.calculate(rightProfiler.getTargetPos());

    SmartDashboard.putNumber("Base left voltage", leftSpeed);
    SmartDashboard.putNumber("Base right voltage", rightSpeed);

    move(leftSpeed, rightSpeed);
  }

  public boolean atTarget() {
    return leftProfiler.atTarget() && rightProfiler.atTarget();
  }
}
