package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Indexer extends SubsystemBase {
  private final CANSparkMax indexer;

  public Indexer() {
    indexer = new CANSparkMax(KIndexerSpark, CANSparkMaxLowLevel.MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  /**
   * Moves the indexer
   * 
   * @param speed Speed to move the indexer at
   */
  public void move(double speed) {
    indexer.set(speed);
  }
}
