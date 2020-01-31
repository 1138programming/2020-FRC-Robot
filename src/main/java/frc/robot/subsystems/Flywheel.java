package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.commands.Flywheel.SpinFlywheelAt;
import frc.robot.commands.Flywheel.StopFlywheel;
import static frc.robot.Constants.*;

import frc.robot.controller.TakeBackHalf;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;
  private TakeBackHalf tbhControllerTop;
  private TakeBackHalf tbhControllerBottom;

  public Flywheel() {
    flywheelTop = new CANSparkMax(KFlywheelTop, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelBottom = new CANSparkMax(KFlywheelBottom, CANSparkMaxLowLevel.MotorType.kBrushless);

    flywheelBottom.setInverted(true);
    tbhControllerTop = new TakeBackHalf(0.000001);
    tbhControllerTop.setInputRange(-40000, 40000);
    tbhControllerTop.setOutputRange(-1, 1);
    tbhControllerBottom = new TakeBackHalf(0.000001);
    tbhControllerBottom.setInputRange(-40000, 40000);
    tbhControllerBottom.setOutputRange(-1, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Flywheel speed", getSpeed());
  }
  
  public void move(double FlywheelSpeed) {
    flywheelTop.set(FlywheelSpeed);
    flywheelBottom.set(FlywheelSpeed);
  }

  public double getTopSpeed() {
    return flywheelTop.getSelectedSensorVelocity();
  }

  public double getBottomSpeed() {
    return flywheelBottom.getSelectedSensorVelocity();
  }

  public void setSetpoints(double setpoint) {
    tbhControllerTop.setSetpoint(setpoint);
    tbhControllerBottom.setSetpoint(setpoint);
  }

  public double getTopSetpoint() {
    return tbhControllerTop.getSetpoint();
  }

  public double getBottomSetpoint() {
    return tbhControllerBottom.getSetpoint();
  }
  
  public void calculate() {
    double topSpeed = tbhController.calculate(getTopSpeed());
    double bottomSpeed = tbhController.calculate(getBottomSpeed());
    move(topSpeed, bottomSpeed);
}
