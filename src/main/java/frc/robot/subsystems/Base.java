package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
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

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Util;

public class Base extends SubsystemBase {
  //Creating the Talons
  private final TalonFX leftFront, leftBack, rightBack, rightFront;

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
  private final PIDController rotationController;
  private final PIDController straightener;

  //Variables
  private static final int KTicksPerRotation = 2048; //conversion factor that we have to find
  private static final int KFreeSpeedRPM = 6380; // Free speed of the base motors in RPM (check in the Quran)
  private static final int KFreeSpeed = KFreeSpeedRPM / 60; // Free speed of the base in ticks per second
  private static final double KLowGear = (12.0 / 60.0) * (18.0 / 38.0) * (16.0 / 50.0); // Numbers from the Quran, absolutely 100% true
  private static final double KHighGear = (12.0 / 60.0) * (18.0 / 13.0) * (36.0 / 30.0); // Numbers from the Quran, absolutely 100% true
  private static final double KRotationsPerTickLow = 1 / (KLowGear * KTicksPerRotation);
  private static final double KRotationsPerTickHigh = 1 / (KHighGear * KTicksPerRotation);
  private static final double KShiftSpeed = KFreeSpeed / ((KRotationsPerTickHigh + KRotationsPerTickLow) * KTicksPerRotation);

  private double lastLeftVel = 0;
  private double lastRightVel = 0;
  private double leftAccel = 0;
  private double rightAccel = 0;
  private double rotationsPerTick = KRotationsPerTickHigh;

  private double leftPWM, rightPWM;

  private final AHRS ahrs;
  private static double yawAngle;
  private static double velocityX;
  private static double velocityY;
  private static double velocityZ;
  private static double displacementX;
  private static double displacementY;
  private static double displacementZ;

  public Base() {
    // Instantiating the talons
    leftFront = new TalonFX(KLeftFrontTalon);
    leftBack = new TalonFX(KLeftBackTalon);
    rightBack = new TalonFX(KRightFrontTalon);
    rightFront = new TalonFX(KRightBackTalon);

    // Inverting the necessary talons
    rightBack.setInverted(true);
    rightFront.setInverted(true);

    //Smart Current
    // leftFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
    // leftBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
    // rightFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
    // rightBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));

    //Set brake mode
    leftBack.setNeutralMode(NeutralMode.Brake);
    leftFront.setNeutralMode(NeutralMode.Brake);
    rightBack.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);

    // Slaving the talons
    leftBack.follow(leftFront);
    // rightFront.follow(rightBack);


    // Configure sensors
    leftBack.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    rightBack.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    
    // Set up profilers for both sides
    leftProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0, 0, 0.02);
    rightProfiler = new LinearProfiler(5, 0.5, 0.1, 0, 0, 0, 0, 0.02);
    leftProfiler.setTolerance(50, 20);
    rightProfiler.setTolerance(50, 20);

    // Set up PID controller to work with the Limelight x offset
    xOffController = new PIDController(0.0065, 0.0035, 0.00005, 0, 0.05);
    //xOffController = new PIDController(0.0, 0.0, 0.0, 0.0, 0.02);
    xOffController.setInputRange(-28, 28);
    xOffController.setOutputRange(-1, 1);
    xOffController.setTolerance(1, 0.001);
    xOffController.setOutputDeadband(0.1, 0.02);
    xOffController.configIntegral(IntegralType.DEFAULT, true);
    xOffController.setIntegralZoneRange(5);
    xOffController.setSetpoint(0);

    // Set up PID controller to work with the gyro offset
    rotationController = new PIDController(0.016, 0.003, 0.001, 0, 0.02);
    rotationController.enableContinuousInput(0, 360);
    rotationController.setOutputRange(-1, 1);
    rotationController.setTolerance(1, 0.001);
    rotationController.setOutputDeadband(0.1, 0.02);
    rotationController.configIntegral(IntegralType.DEFAULT, true);
    rotationController.setIntegralZoneRange(5);

    // Set up PID controller to drive the base straight
    straightener = new PIDController(0.016, 0, 0, 0, 0.02);
    rotationController.enableContinuousInput(0, 360);
    rotationController.setOutputRange(-1, 1);
    rotationController.setOutputDeadband(0.1, 0.02);

    // Set up slew rate limiters
    leftLimiter = new SlewRateLimiter(6);
    rightLimiter = new SlewRateLimiter(6);

    // Instantiating the solenoid 
    shifter = new Solenoid(KBaseShifter);
    
    ahrs = new AHRS(SPI.Port.kMXP);

    SmartDashboard.putNumber("Base XOff P", rotationController.getP());
    SmartDashboard.putNumber("Base XOff I", rotationController.getI());
    SmartDashboard.putNumber("Base XOff D", rotationController.getD());
    SmartDashboard.putBoolean("Base Aligned", false);
  }

  @Override
  public void periodic() {
    double leftVel = getLeftVel();
    double rightVel = getRightVel();

    leftAccel = (leftVel - lastLeftVel) * 5;
    rightAccel = (rightVel - lastRightVel) * 5;

    SmartDashboard.putString("base state", baseState.name());
    SmartDashboard.putNumber("Facing angle", getFacingDirection());

    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Base Left Target Pos", leftProfiler.getTargetPos());
    //SmartDashboard.putNumber("Base Right Target Pos", rightProfiler.getTargetPos());
    //SmartDashboard.putNumber("Base Left Pos", getLeftEncoder());
    //SmartDashboard.putNumber("Base Right Pos", getRightEncoder());
    //SmartDashboard.putNumber("Base Left Target Vel", leftProfiler.getTargetVel());
    //SmartDashboard.putNumber("Base Right Target Vel", rightProfiler.getTargetVel());
    //SmartDashboard.putNumber("Base Left Vel", getLeftVel());
    //SmartDashboard.putNumber("Base Right Vel", getRightVel());
    //SmartDashboard.putNumber("Base Left Target Accel", leftProfiler.getTargetAccel());
    //SmartDashboard.putNumber("Base Right Target Accel", rightProfiler.getTargetAccel());
    //SmartDashboard.putNumber("Base Left Accel", getLeftAccel());
    //SmartDashboard.putNumber("Base Right Accel", getRightAccel());
    //SmartDashboard.putNumber("Base Left PWM", leftPWM);
    //SmartDashboard.putNumber("Base Right PWM", rightPWM);
    // SmartDashboard.putNumber("FacingAngle", yawAngle);
    // SmartDashboard.putNumber("VelocityX", velocityX);
    // SmartDashboard.putNumber("VelocityY", velocityY);
    // SmartDashboard.putNumber("VelocityZ", velocityZ);
    // SmartDashboard.putNumber("DisplacementX", displacementX);
    // SmartDashboard.putNumber("DisplacementY", displacementY);
    // SmartDashboard.putNumber("DisplacementZ", displacementZ);

    lastLeftVel = leftVel;
    lastRightVel = rightVel;

    //autoShift();
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
      rightBack.set(ControlMode.PercentOutput, rightPWM * KBaseMediumGear);
      rightFront.set(ControlMode.PercentOutput, rightPWM * KBaseMediumGear);
    } else {
      leftFront.set(ControlMode.PercentOutput, leftPWM);
      rightBack.set(ControlMode.PercentOutput, rightPWM);
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
      shifter.set(false);
      rotationsPerTick = KRotationsPerTickHigh;

      //leftFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
      //leftBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
      //rightFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
      //rightBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 50, 1));
    } else {
      //shifter.set(DoubleSolenoid.Value.kReverse);
      shifter.set(true);
      rotationsPerTick = KRotationsPerTickLow;

      //leftFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(false, 40, 50, 1));
      //leftBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(false, 40, 50, 1));
      //rightFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(false, 40, 50, 1));
      //rightBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(false, 40, 50, 1));
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
    return (double)rightBack.getSelectedSensorPosition() * rotationsPerTick; //selected sensor (in raw sensor units) per 100ms
  }

  /**
   * @brief Zeroes the encoders on both sides
   */
  public void zeroEncoders() {
    leftFront.setSelectedSensorPosition(0);
    rightBack.setSelectedSensorPosition(0);
    leftBack.setSelectedSensorPosition(0);
    rightFront.setSelectedSensorPosition(0);
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
    return (double)rightBack.getSelectedSensorVelocity() * rotationsPerTick * 10; //selected sensor (in raw sensor units) per 100ms
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

    //SmartDashboard.putNumber("Base left voltage", leftSpeed);
    //SmartDashboard.putNumber("Base right voltage", rightVel);

    move(leftSpeed, rightVel);
  }

  /**
   * @brief
   */
  public boolean atProfileTarget() {
    return leftProfiler.atTarget() && rightProfiler.atTarget();
  }

  public void calculateXOff() {
    double output = xOffController.calculate(Robot.camera.getOffsetX() - .5);
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

  public void setRotationMaxSpeed(double maxSpeed) {
    rotationController.setOutputRange(-maxSpeed, maxSpeed);
  }

  public void setRotationSetpoint(double setpoint) {
    //SmartDashboard.putNumber("Rotation setpoint", setpoint);
    rotationController.setSetpoint(setpoint);
  }

  public void setRotationSetpointRelative(double setpoint) {
    SmartDashboard.putNumber("Rotation setpoint", setpoint + getFacingDirection());
    setRotationSetpoint(setpoint + getFacingDirection());
  }

  public void calculateRotation() {
    double output = rotationController.calculate(getFacingDirection());

    move(output, -output);
  }

  public boolean atRotationSetpoint() {
    return rotationController.atSetpoint();
  }

  public void resetRotation() {
    rotationController.reset();
  }

  public void resetStraightener() {
    straightener.reset();
    straightener.setSetpoint(getFacingDirection());
  }

  public void moveWithStraightener(double PWM) {
    double rotationCorrection = straightener.calculate(getFacingDirection());

    move(PWM + rotationCorrection, PWM - rotationCorrection);
  }

  /**
   * Reset navx yaw
   */
  public void yawReset(){
    ahrs.zeroYaw();
  }

  public double getFacingDirection(){
    yawAngle = Util.wrapInput(ahrs.getAngle(), 0, 360);
    return yawAngle;
  }

  public double getVelocityX(){
    velocityX = ahrs.getVelocityX();
    return velocityX;
  }

  public double getVelocityY(){
    velocityY = ahrs.getVelocityY();
    return velocityY;
  }

  public double getVelocityZ(){
    velocityZ = ahrs.getVelocityZ();
    return velocityZ;
  }

  public double getDisplacementX(){
    displacementX = ahrs.getDisplacementX();
    return displacementX;
  }

  public double getDisplacementY(){
    displacementY = ahrs.getDisplacementY();
    return displacementY;
  }

  public double getDisplacementZ(){
    displacementZ = ahrs.getDisplacementZ();
    return displacementZ;
  }
  // Navx
}
