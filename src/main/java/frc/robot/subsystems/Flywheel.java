package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;

import frc.robot.controller.PIDController;
import frc.robot.enums.IntegralType;
import edu.wpi.first.wpilibj.SlewRateLimiter;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private final CANEncoder topEncoder, bottomEncoder;
  private PIDController topController, bottomController;
  private SlewRateLimiter topLimiter, bottomLimiter;

  private double topPWM = 0, bottomPWM = 0;

  /**
   * @brief This is the Flywheel
   */
  public Flywheel() {
    // Create SparkMax objects
    flywheelTop = new CANSparkMax(KFlywheelTopSpark, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelBottom = new CANSparkMax(KFlywheelBottomSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    // Configure spark. Factory defaults are restored, so every necessary configuration is included here
    flywheelTop.restoreFactoryDefaults();
    flywheelBottom.restoreFactoryDefaults();
    flywheelTop.setInverted(true);
    flywheelBottom.setInverted(false);

    // Burn configurations to flash memory. This is where the sparks get configured upon being rebooted.
    // This protects against wrong configurations if the robot reboots during a match
    flywheelTop.burnFlash();
    flywheelBottom.burnFlash();

    // Get encoder objects from each spark
    topEncoder = flywheelTop.getEncoder();
    bottomEncoder = flywheelBottom.getEncoder();

    // Top TBH Controller
    topController = new PIDController(0.0003, 0.0005, 0.000193, 0.000185, 0.02);
    topController.setInputRange(-5500, 5500);
    topController.setOutputRange(-1, 1);
    topController.configIntegral(IntegralType.DEFAULT, true);
    topController.setIntegralZoneRange(50);

    // Bottom TBH Controller
    bottomController = new PIDController(0.0003, 0.0005, 0.000035, 0.000193, 0.02);
    bottomController.setInputRange(-10000, 10000);
    bottomController.setOutputRange(-1, 1);
    bottomController.configIntegral(IntegralType.DEFAULT, true);
    bottomController.setIntegralZoneRange(50);

    // Slew rate limits to prevent the motor PWM values from changing too fast
    topLimiter = new SlewRateLimiter(1);
    bottomLimiter = new SlewRateLimiter(1);

    // Initialize SmartDashboard fields that we are getting numbers from
    SmartDashboard.putNumber("Flywheel Top Setpoint", 2800.0);
    SmartDashboard.putNumber("Flywheel Bottom Setpoint", 3000.0);
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
    // Print flywheel values
    SmartDashboard.putNumber("Flywheel Top Velocity", getTopVel());
    SmartDashboard.putNumber("Flywheel Bottom Velocity", getBottomVel());
    SmartDashboard.putNumber("Flywheel Top PWM", topPWM);
    SmartDashboard.putNumber("Flywheel Bottom PWM", bottomPWM);
    SmartDashboard.putNumber("Flywheel Top Error", topController.getError());
    SmartDashboard.putNumber("Flywheel Bottom Error", bottomController.getError());
  }
  
  /**
   * @brief Moves the flywheel directly
   * 
   * Includes a slew rate limiter
   * 
   * @param topPWM    Speed to move the top wheel at
   * @param bottomPWM Speed to move the bottom wheel at
   */
  public void move(double topPWM, double bottomPWM) {
    topPWM = topLimiter.calculate(topPWM);
    bottomPWM = bottomLimiter.calculate(bottomPWM);

    this.topPWM = topPWM;
    this.bottomPWM = bottomPWM;

    flywheelTop.set(topPWM);
    flywheelBottom.set(bottomPWM);
  }

  /**
   * @brief 
   */
  public double getTopVel() {
    return topEncoder.getVelocity();
  }

  /**
   * @brief 
   */
  public double getBottomVel() {
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
    move(topLimiter.calculate(topController.calculate(getTopVel())), bottomLimiter.calculate(bottomController.calculate(getBottomVel())));
  }

  public void reset() {
    topController.reset();
    bottomController.reset();
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
