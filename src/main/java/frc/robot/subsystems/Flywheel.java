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

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private final CANEncoder topEncoder, bottomEncoder;
  private TakeBackHalf topController;
  private TakeBackHalf bottomController;

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
    topController = new TakeBackHalf(0.00002);
    topController.setInputRange(-6000, 6000);
    topController.setOutputRange(-1, 1);
    topController.setTolerance(10, 500);

    // Bottom TBH Controller
    bottomController = new TakeBackHalf(0.00002);
    bottomController.setInputRange(-6000, 6000);
    bottomController.setOutputRange(-1, 1);
    bottomController.setTolerance(10, 500);

    SmartDashboard.putNumber("Flywheel Top Gain", topController.getGain());
    SmartDashboard.putNumber("Flywheel Bottom Gain", bottomController.getGain());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Flywheel Top Velocity", getTopSpeed());
    SmartDashboard.putNumber("Flywheel Bottom Velocity", getBottomSpeed());
  }
  
  /**
   * @brief Moves the flywheel directly
   * 
   * @param topSpeed    Speed to move the top wheel at
   * @param bottomSpeed Speed to move the bottom wheel at
   */
  public void move(double topSpeed, double bottomSpeed) {
    flywheelTop.set(topSpeed);
    flywheelBottom.set(bottomSpeed);

    SmartDashboard.putNumber("Flywheel Top Voltage", topSpeed);
    SmartDashboard.putNumber("Flywheel Bottom Voltage", bottomSpeed);
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
    double topSpeed = topController.calculate(getTopSpeed());
    double bottomSpeed = bottomController.calculate(getBottomSpeed());

    SmartDashboard.putNumber("Flywheel Top Error", topController.getError());
    SmartDashboard.putNumber("Flywheel Bottom Error", bottomController.getError());
    SmartDashboard.putNumber("Flywheel Top Accel Error", topController.getAccelError());
    SmartDashboard.putNumber("Flywheel Bottom Accel Error", bottomController.getAccelError());

    move(topSpeed, bottomSpeed);
  }

  public void setGainTop(double gain) {
    topController.setGain(gain);
  }

  public void setGainBottom(double gain) {
    bottomController.setGain(gain);
  }
}
