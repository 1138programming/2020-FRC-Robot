package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Flywheel.StopFlywheel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;

import frc.robot.controller.TakeBackHalf;
import frc.robot.controller.PIDController;
import edu.wpi.first.wpilibj.SlewRateLimiter;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private final CANEncoder topEncoder, bottomEncoder;
  //private TakeBackHalf topController;
  //private TakeBackHalf bottomController;
  private PIDController topController;
  private PIDController bottomController;
  private SlewRateLimiter topLimiter;
  private SlewRateLimiter bottomLimiter;

  private double topSpeed = 0, bottomSpeed = 0;

  /**
   * @brief This is the Flywheel
   */
  public Flywheel() {
    flywheelTop = new CANSparkMax(KFlywheelTopSpark, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelBottom = new CANSparkMax(KFlywheelBottomSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    topEncoder = flywheelTop.getEncoder();
    bottomEncoder = flywheelBottom.getEncoder();

    flywheelBottom.setInverted(false);
    flywheelTop.setInverted(true);

    // Top TBH Controller
    //topController = new TakeBackHalf(0.00002);
    topController = new PIDController(0.0003, 0, 0, 0.000185, 0.02);
    topController.setInputRange(-5500, 5500);
    topController.setOutputRange(-1, 1);
    //topController.setTolerance(10, 500);

    // Bottom TBH Controller
    //bottomController = new TakeBackHalf(0.00002);
    bottomController = new PIDController(0, 0, 0, 0.000193, 0.02);
    bottomController.setInputRange(-10000, 10000);
    bottomController.setOutputRange(-1, 1);
    //bottomController.setTolerance(10, 500);

    topLimiter = new SlewRateLimiter(1);
    bottomLimiter = new SlewRateLimiter(1);

    //SmartDashboard.putNumber("Flywheel Top Gain", topController.getGain());
    //SmartDashboard.putNumber("Flywheel Bottom Gain", bottomController.getGain());
    SmartDashboard.putNumber("Flywheel Top Setpoint", 0.0);
    SmartDashboard.putNumber("Flywheel Bottom Setpoint", 0.0);
    SmartDashboard.putNumber("Flywheel Top P", topController.getP());
    SmartDashboard.putNumber("Flywheel Top I", topController.getI());
    SmartDashboard.putNumber("Flywheel Top D", topController.getD());
    SmartDashboard.putNumber("Flywheel Top F", topController.getF());
    SmartDashboard.putNumber("Flywheel Bottom P", bottomController.getP());
    SmartDashboard.putNumber("Flywheel Bottom I", bottomController.getI());
    SmartDashboard.putNumber("Flywheel Bottom D", bottomController.getD());
    SmartDashboard.putNumber("Flywheel Bottom F", bottomController.getF());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Flywheel Top Velocity", getTopSpeed());
    SmartDashboard.putNumber("Flywheel Bottom Velocity", getBottomSpeed());
    SmartDashboard.putNumber("Flywheel Top PWM", topSpeed);
    SmartDashboard.putNumber("Flywheel Bottom PWM", bottomSpeed);
    SmartDashboard.putNumber("Flywheel Top Error", topController.getError());
    SmartDashboard.putNumber("Flywheel Bottom Error", bottomController.getError());
  }
  
  /**
   * @brief Moves the flywheel directly
   * 
   * @param topSpeed    Speed to move the top wheel at
   * @param bottomSpeed Speed to move the bottom wheel at
   */
  public void move(double topSpeed, double bottomSpeed) {
    this.topSpeed = topSpeed;
    this.bottomSpeed = bottomSpeed;

    flywheelTop.set(topSpeed);
    flywheelBottom.set(bottomSpeed);
  }

  /**
   * @brief 
   */

  public double getTopSpeed() {
    return topEncoder.getVelocity();
  }

  /**
   * @brief 
   */

  public double getBottomSpeed() {
    return bottomEncoder.getVelocity();
  }

  public void setSetpoints(double topSetpoint, double bottomSetpoint) {
    topController.setSetpoint(topSetpoint);
    bottomController.setSetpoint(bottomSetpoint);
  }

  /**
   * @brief 
   */

  public double getTopSetpoint() {
    return topController.getSetpoint();
  }

  /**
   * @brief 
   */

  public double getBottomSetpoint() {
    return bottomController.getSetpoint();
  }

  public boolean atTopSetpoint() {
    return topController.atSetpoint();
  }

  /**
   * @brief 
   */
  public boolean atBottomSetpoint() {
    return bottomController.atSetpoint();
  }

  public boolean atSetpoints() {
    return topController.atSetpoint() && bottomController.atSetpoint();
  }

  public void calculate() {
    move(topLimiter.calculate(topController.calculate(getTopSpeed())), bottomLimiter.calculate(bottomController.calculate(getBottomSpeed())));
  }

  public void reset() {
    topController.reset();
    bottomController.reset();
    topLimiter.reset(0);
    bottomLimiter.reset(0);
  }

  public void setTopConstants(double Kp, double Ki, double Kd, double Kf) {
    topController.setP(Kp);
    topController.setI(Ki);
    topController.setD(Kd);
    topController.setF(Kf);
  }

  public void setBottomConstants(double Kp, double Ki, double Kd, double Kf) {
    bottomController.setP(Kp);
    bottomController.setI(Ki);
    bottomController.setD(Kd);
    bottomController.setF(Kf);
  }
}
