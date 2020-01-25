package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Flywheel.StopFlywheel;
import static frc.robot.Constants.*;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax flywheelTop, flywheelBottom;

  public Flywheel() {
    flywheelTop = new CANSparkMax(KFlywheelTopSpark, CANSparkMaxLowLevel.MotorType.kBrushless);
    flywheelBottom = new CANSparkMax(KFlywheelBottomSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    flywheelBottom.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void move(double topSpeed, double bottomSpeed) {
    flywheelTop.set(topSpeed);
    flywheelBottom.set(bottomSpeed);
  }
}
