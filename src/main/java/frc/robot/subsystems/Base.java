package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.BaseState;

import frc.robot.controller.LinearProfiler;
import frc.robot.controller.PIDController;
import frc.robot.enums.IntegralType;
import edu.wpi.first.wpilibj.SlewRateLimiter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Base extends SubsystemBase {
  //Creating the Talons
  private final TalonFX leftFront, leftBack, rightFront, rightBack;

  //Linear profiler for both sides of the base
  private final LinearProfiler leftProfiler, rightProfiler;

  private final SlewRateLimiter leftLimiter, rightLimiter;

  //Creating the Solenoids
  //private final DoubleSolenoid shifter;
  private final Solenoid shifter;

  //Sets the default state to medium
  private BaseState baseState = BaseState.MEDIUM;

  // PIDController using Limelight x offset
  private final PIDController xOffController;
  
  //Variables
  private static final int KTicksPerRotation = 2048; //conversion factor that we have to find
  private static final int KFreeSpeedRPM = 6380; // Free speed of the base motors in RPM (check in the Quran)
  private static final int KFreeSpeed = KFreeSpeedRPM / 60; // Free speed of the base in ticks per second
  private static final double KLowGear = (8.0 / 62.0) * (24.0 / 32.0) * (16.0 / 50.0); // Numbers from the Quran, absolutely 100% true
  private static final double KHighGear = (8.0 / 62.0) * (24.0 / 32.0) * (36.0 / 30.0); // Numbers from the Quran, absolutely 100% true
  private static final double KRotationsPerTickLow = 1 / (KLowGear * KTicksPerRotation);
  private static final double KRotationsPerTickHigh = 1 / (KHighGear * KTicksPerRotation);
  private static final double KShiftSpeed = KFreeSpeed / ((KRotationsPerTickHigh + KRotationsPerTickLow) * KTicksPerRotation);

  private double lastLeftVel = 0;
  private double lastRightVel = 0;
  private double leftAccel = 0;
  private double rightAccel = 0;
  private double rotationsPerTick = KRotationsPerTickHigh;

  private double leftPWM, rightPWM;

  /**
   * @brief This is the Base
   */

  public Base() {
    // Instantiating the talons
    leftFront = new TalonFX(KLeftFrontTalon);
    leftBack = new TalonFX(KLeftBackTalon);
    rightFront = new TalonFX(KRightFrontTalon);
    rightBack = new TalonFX(KRightBackTalon);

    // Inverting the necessary talons
    rightFront.setInverted(true);
    rightBack.setInverted(true);

    // Slaving the talons
    leftBack.follow(leftFront);
    rightBack.follow(rightFront);

    // Configure sensors
    leftBack.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    rightFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    
    // Set up profilers for both sides
    leftProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0, 0, 0.02);
    rightProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0, 0, 0.02);
    leftProfiler.setTolerance(50, 20);
    rightProfiler.setTolerance(50, 20);

    // Set up PID controller to work with the Limelight x offset
    xOffController = new PIDController(0.01, 0.015, 0.002, 0, 0.02);
    xOffController.setInputRange(-28, 28);
    xOffController.setOutputRange(-1, 1);
    xOffController.setTolerance(1, 0.001);
    xOffController.setSetpoint(0);
    xOffController.configIntegral(IntegralType.DEFAULT, true);
    xOffController.setIntegralZoneRange(10);

    // Set up slew rate limiters
    leftLimiter = new SlewRateLimiter(1);
    rightLimiter = new SlewRateLimiter(1);

    // Instantiating the solenoid
    shifter = new Solenoid(KBaseShifter);

    SmartDashboard.putNumber("Base XOff P", xOffController.getP());
    SmartDashboard.putNumber("Base XOff I", xOffController.getI());
    SmartDashboard.putNumber("Base XOff D", xOffController.getD());
  }

  @Override
  public void periodic() {
    double leftVel = getLeftVel();
    double rightVel = getRightVel();

    leftAccel = (leftVel - lastLeftVel) * 5;
    rightAccel = (rightVel - lastRightVel) * 5;

    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Base Left Target Pos", leftProfiler.getTargetPos());
    SmartDashboard.putNumber("Base Right Target Pos", rightProfiler.getTargetPos());
    SmartDashboard.putNumber("Base Left Pos", getLeftEncoder());
    SmartDashboard.putNumber("Base Right Pos", getRightEncoder());
    SmartDashboard.putNumber("Base Left Target Vel", leftProfiler.getTargetVel());
    SmartDashboard.putNumber("Base Right Target Vel", rightProfiler.getTargetVel());
    SmartDashboard.putNumber("Base Left Vel", getLeftVel());
    SmartDashboard.putNumber("Base Right Vel", getRightVel());
    SmartDashboard.putNumber("Base Left Target Accel", leftProfiler.getTargetAccel());
    SmartDashboard.putNumber("Base Right Target Accel", rightProfiler.getTargetAccel());
    SmartDashboard.putNumber("Base Left Accel", getLeftAccel());
    SmartDashboard.putNumber("Base Right Accel", getRightAccel());
    SmartDashboard.putNumber("Base Left PWM", leftPWM);
    SmartDashboard.putNumber("Base Right PWM", rightPWM);

    lastLeftVel = leftVel;
    lastRightVel = rightVel;

    autoShift();
  }

  /**
   * @brief Moves the base directly
   *  
   * @param leftPWM   Speed to move the left side at
   * @param rightPWM  Speed to move the right side at
   */
  public void move(double leftPWM, double rightPWM) {
    leftPWM = leftLimiter.calculate(leftPWM);
    rightPWM = rightLimiter.calculate(rightPWM);

    this.leftPWM = leftPWM;
    this.rightPWM = rightPWM;

    if (baseState == BaseState.MEDIUM) {
      leftFront.set(ControlMode.PercentOutput, leftPWM * KBaseMediumGear);
      rightFront.set(ControlMode.PercentOutput, rightPWM * KBaseMediumGear);
    } else {
      leftFront.set(ControlMode.PercentOutput, leftPWM);
      rightFront.set(ControlMode.PercentOutput, rightPWM);
    }
  }

  /**
   * @brief Sets the base shift to high, medium, or low
   * 
   * @param state State to set the base to
   */
  public void setBaseState(BaseState state) {
    baseState = state;
    
    if (baseState == BaseState.HIGH || baseState == BaseState.MEDIUM) {
      //shifter.set(DoubleSolenoid.Value.kForward);
      shifter.set(true);
      rotationsPerTick = KRotationsPerTickHigh;
    } else {
      //shifter.set(DoubleSolenoid.Value.kReverse);
      shifter.set(false);
      rotationsPerTick = KRotationsPerTickLow;
    }
  }

  /**
   * @brief Gets the base's state
   * 
   * @return State of the base
   */
  public BaseState getBaseState() {
    return baseState;
  }

  /**
   * @brief Gets the encoder value of the left side
   * 
   * @return  Left encoder value in rotations
   */
  public double getLeftEncoder() {
    return (double)leftFront.getSelectedSensorPosition() * rotationsPerTick; //selected sensor (in raw sensor units) per 100ms
  }

  /**
   * @brief Gets the encoder value of the right side
   * 
   * @return  Right encoder value in rotations
   */
  public double getRightEncoder() {
    return (double)rightFront.getSelectedSensorPosition() * rotationsPerTick; //selected sensor (in raw sensor units) per 100ms
  }

  /**
   * @brief Zeroes the encoders on both sides
   */
  public void zeroEncoders() {
    leftFront.setSelectedSensorPosition(0);
    rightFront.setSelectedSensorPosition(0);
    leftBack.setSelectedSensorPosition(0);
    rightBack.setSelectedSensorPosition(0);
  }

  //Getters
  
  /**
   * @brief Gets the speed of the left side
   * 
   * @return Speed in ticks per 100 ms
   */
  public double getLeftVel() {
    return (double)leftFront.getSelectedSensorVelocity() * rotationsPerTick * 10; //selected sensor (in raw sensor units) per 100ms
  }

  /**
   * @brief Gets the speed of the right side
   * 
   * @return Speed in ticks per 100 ms
   */

  public double getRightVel() {
    return (double)rightFront.getSelectedSensorVelocity() * rotationsPerTick * 10; //selected sensor (in raw sensor units) per 100ms
  }

  private void autoShift() {
    double maxVel = Math.max(getLeftVel(), getRightVel());
    if (maxVel >= KShiftSpeed) {
      setBaseState(BaseState.LOW);
    }
  }
  
  /**
   * @brief Gets the acceleration of the left side (as a double)
   * 
   * @return Acceleration in rotations per second squared
   */

  public double getLeftAccel() {
    return leftAccel;
  }

  /**
   * @brief Gets the acceleration of the right side (as a double)
   * 
   * @return Acceleration in rotations per second squared
   */

  public double getRightAccel() {
    return rightAccel;
  }

  /**
   * @brief Sets the targets for the linear profilers on both sides of the base
   * 
   * @param leftTarget The target for the left side, in rotations
   * @param rightTarget The target for the right side, in rotations
   */

  public void setTarget(double leftTarget, double rightTarget) {
    leftProfiler.setTarget(leftTarget);
    rightProfiler.setTarget(rightTarget);
  }

  /**
   * @brief Sets the targets for the linear profiler on both sides of the base relative to their current targets.
   * 
   * For example, if both targets are 5 rotations, then the profilers will try to rotate each side of the base by 5 additional rotations, regardless of what their current encoder count is.
   * 
   * @param leftTarget The relative target for the left side, in rotations
   * @param rightTarget The relative target for the right side, in rotations
   */

  public void setTargetRelative(double leftTarget, double rightTarget) {
    leftProfiler.setTargetRelative(leftTarget);
    rightProfiler.setTargetRelative(rightTarget);
  }

  /**
   * @brief Gets the target of the left profiler as a double
   * 
   * @return The left profiler’s target
   */

  public double getLeftTarget() {
    return leftProfiler.getTarget();
  }

  /**
   * @brief Gets the target of the right profiler as a double
   * 
   * @return The right profiler’s target
   */

  public double getRightTarget() {
    return rightProfiler.getTarget();
  }

  /**
   * @brief Sets the maximum velocity for both the left and right profilers on the base
   * 
   * @param maxVel The maximum velocity in rotations per second
   */

  public void setMaxVel(double maxVel) {
    leftProfiler.setMaxVel(maxVel);
    rightProfiler.setMaxVel(maxVel);
  }

  /**
   * @brief Sets the maximum acceleration for both the left and right profilers on the base
   * 
   * @param maxAccel The maximum velocity in rotations per second
   */

  public void setMaxAccel(double maxAccel) {
    leftProfiler.setMaxAccel(maxAccel);
    rightProfiler.setMaxAccel(maxAccel);
  }

  /**
   * @brief
   */

  public void initProfile() {
    leftProfiler.init(getLeftEncoder());
    rightProfiler.init(getRightEncoder());
  }

  /**
   * @brief
   */
  public void calculateProfile() {
    double leftSpeed = leftProfiler.calculate(getLeftVel());
    double rightVel = rightProfiler.calculate(getRightVel());

    //double leftSpeed = leftProfiler.calculate(leftProfiler.getTargetPos());
    //double rightVel = rightProfiler.calculate(rightProfiler.getTargetPos());

    SmartDashboard.putNumber("Base left voltage", leftSpeed);
    SmartDashboard.putNumber("Base right voltage", rightVel);

    move(leftSpeed, rightVel);
  }

  /**
   * @brief
   */
  public boolean atProfileTarget() {
    return leftProfiler.atTarget() && rightProfiler.atTarget();
  }

  public void calculateXOff() {
    double output = xOffController.calculate(Robot.camera.getOffsetX());
    SmartDashboard.putNumber("xOutput", output);

    move(-output, output);
  }

  public boolean atTargetXOff() {
    return xOffController.atSetpoint();
  }

  public void resetXOff() {
    xOffController.reset();
    xOffController.setSetpoint(0);
  }

  public void setXOffConstants(double kP, double kI, double kD) {
    xOffController.setGains(kP, kI, kD);
  }
}
