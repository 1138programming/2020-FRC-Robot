package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;

public class Indexer extends SubsystemBase {
  private final CANSparkMax indexer;

  /**
   * @brief This is the Indexer
   */

  public Indexer() {
    indexer = new CANSparkMax(KIndexerSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    // Configure spark. Factory defaults are restored, so every necessary configuration is included here
    indexer.restoreFactoryDefaults();
    indexer.setInverted(true);
    indexer.setSmartCurrentLimit(40, 40, 3000);

    // Burn configurations to flash memory. This is where the sparks get configured upon being rebooted.
    // This protects against wrong configurations if the robot reboots during a match
    indexer.burnFlash();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  /**
   * @brief Moves the indexer
   * 
   * @param PWM Speed to move the indexer at
   */
  public void move(double PWM) {
    indexer.set(PWM);
  }
}
