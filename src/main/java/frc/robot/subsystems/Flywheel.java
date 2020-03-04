package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;
import frc.robot.controller.PIDController;
import frc.robot.enums.IntegralType;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import java.util.HashMap;
import java.util.ArrayList;
import frc.robot.FlywheelState;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private final CANEncoder topEncoder, bottomEncoder;
  private CANPIDController topController, bottomController;
  private SlewRateLimiter topLimiter, bottomLimiter;
  private double topPWM = 0, bottomPWM = 0;
  
  private HashMap<Double, FlywheelState> shootingTable;
  private ArrayList<Double> distanceKeys; // Sort from smallest to largest

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
    topController = flywheelTop.getPIDController();
    topController.setP(0.00028);
    topController.setI(0.0005);
    topController.setD(0.0002);
    topController.setFF(0.000169);

    // Bottom TBH Controller
    bottomController = flywheelBottom.getPIDController();
    topController.setP(0.0003);
    topController.setI(0.0005);
    topController.setD(0.00002);
    topController.setFF(0.000174);

    // Slew rate limits to prevent the motor PWM values from changing too fast
    topLimiter = new SlewRateLimiter(1);
    bottomLimiter = new SlewRateLimiter(1);

    // Initialize the shooting table
    shootingTable = new HashMap<Double, FlywheelState>(14);
    distanceKeys = new ArrayList<Double>(14);
    initShootingTable();

    // Initialize SmartDashboard fields that we are getting numbers from
    SmartDashboard.putNumber("Flywheel Top Setpoint", 3200.0);
    SmartDashboard.putNumber("Flywheel Bottom Setpoint", 3500.0);
    SmartDashboard.putNumber("Flywheel Top P", topController.getP());
    SmartDashboard.putNumber("Flywheel Top I", topController.getI());
    SmartDashboard.putNumber("Flywheel Top D", topController.getD());
    SmartDashboard.putNumber("Flywheel Top F", topController.getFF());
    SmartDashboard.putNumber("Flywheel Bottom P", bottomController.getP());
    SmartDashboard.putNumber("Flywheel Bottom I", bottomController.getI());
    SmartDashboard.putNumber("Flywheel Bottom D", bottomController.getD());
    SmartDashboard.putNumber("Flywheel Bottom F", bottomController.getFF());
    SmartDashboard.putNumber("Shooting Table Top Vel", 0);
    SmartDashboard.putNumber("Shooting Table Bottom Vel", 0);
    SmartDashboard.putNumber("Shooting Table Angle", 0);
  }

  @Override
  public void periodic() {
    // Print flywheel values
    SmartDashboard.putNumber("Flywheel Top Velocity", getTopVel());
    SmartDashboard.putNumber("Flywheel Bottom Velocity", getBottomVel());
    SmartDashboard.putNumber("Flywheel Top PWM", topPWM);
    SmartDashboard.putNumber("Flywheel Bottom PWM", bottomPWM);
  }
  
  private void initShootingTable() {
    addTableEntry(20.0, new FlywheelState(76, 5000, 3500));
    addTableEntry(18.0, new FlywheelState(76, 4700, 3500));
    addTableEntry(16.0, new FlywheelState(76, 4700, 3500));
    addTableEntry(14.0, new FlywheelState(76, 4700, 3500));
    addTableEntry(12.0, new FlywheelState(76, 4700, 3500));
    addTableEntry(10.0, new FlywheelState(76, 4700, 3500));
    addTableEntry(8.0, new FlywheelState(96, 2700, 2900));
    addTableEntry(6.0, new FlywheelState(96, 2700, 2900));
    addTableEntry(4.0, new FlywheelState(96, 2700, 2900));
    addTableEntry(2.0, new FlywheelState(96, 2700, 2900));
  }

  private void addTableEntry(Double distance, FlywheelState flywheelState) {
    shootingTable.put(distance, flywheelState);
    for (int i = 0; i < distanceKeys.size(); i++) {
      if (distanceKeys.get(i) > distance) {
        distanceKeys.add(i, distance);
        return;
      }
    }
    distanceKeys.add(distance);
  }

  public FlywheelState readShootingTable(Double distance) {
    for (Double distanceKey : distanceKeys) {
      if (distanceKey > distance) {
        SmartDashboard.putNumber("Shooting Table Key", distanceKey);
        return shootingTable.get(distanceKey);
      }
    }
    Double distanceKey = distanceKeys.get(distanceKeys.size() - 1);
    SmartDashboard.putNumber("Shooting Table Key", distanceKey);
    return shootingTable.get(distanceKey);
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
    topController.setReference(topSetpoint, ControlType.kVelocity, 0);
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
