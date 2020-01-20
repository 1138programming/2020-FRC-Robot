package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;


public class Indexer extends SubsystemBase {

  private final TalonSRX Indexer;

  
  public Indexer() {
    Indexer = new TalonSRX(KIndexer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void move(double IndexerSpeed) {
    Indexer.set(ControlMode.PercentOutput, IndexerSpeed);
  }
}
