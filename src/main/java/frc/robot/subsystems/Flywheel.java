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

public class Flywheel extends SubsystemBase {

  private final CANSparkMax Flywheel1, Flywheel2;


  public Flywheel() {
    Flywheel1 = new CANSparkMax(KFlywheel1, CANSparkMaxLowLevel.MotorType.kBrushless);
    Flywheel2 = new CANSparkMax(KFlywheel2, CANSparkMaxLowLevel.MotorType.kBrushless);

    Flywheel2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void move(double FlywheelSpeed) {
    Flywheel1.set(FlywheelSpeed);
    Flywheel2.set(FlywheelSpeed);
  }
}
