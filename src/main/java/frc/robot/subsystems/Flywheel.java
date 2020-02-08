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
import static frc.robot.Constants.*;

import frc.robot.controller.TakeBackHalf;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private final CANEncoder topEncoder, bottomEncoder;
  private TakeBackHalf topController;
  private TakeBackHalf bottomController;

  public Flywheel() {
    flywheelTop = new CANSparkMax(KFlywheelTopSpark, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelBottom = new CANSparkMax(KFlywheelBottomSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    topEncoder = flywheelTop.getEncoder();
    bottomEncoder = flywheelBottom.getEncoder();

    flywheelBottom.setInverted(true);
    topController = new TakeBackHalf(0.000001);
    topController.setInputRange(-40000, 40000);
    topController.setOutputRange(-1, 1);
    bottomController = new TakeBackHalf(0.000001);
    bottomController.setInputRange(-40000, 40000);
    bottomController.setOutputRange(-1, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  /**
   * Moves the flywheel directly
   * 
   * @param topSpeed    Speed to move the top wheel at
   * @param bottomSpeed Speed to move the bottom wheel at
   */
  public void move(double topSpeed, double bottomSpeed) {
    flywheelTop.set(topSpeed);
    flywheelBottom.set(bottomSpeed);
  }

  public double getTopSpeed() {
    return topEncoder.getVelocity();
  }

  public double getBottomSpeed() {
    return bottomEncoder.getVelocity();
  }

  public void setSetpoints(double setpoint) {
    topController.setSetpoint(setpoint);
    bottomController.setSetpoint(setpoint);
  }

  public double getTopSetpoint() {
    return topController.getSetpoint();
  }

  public double getBottomSetpoint() {
    return bottomController.getSetpoint();
  }
  
  public void calculate() {
    double topSpeed = topController.calculate(getTopSpeed());
    double bottomSpeed = bottomController.calculate(getBottomSpeed());
    move(topSpeed, bottomSpeed);
  }
}
