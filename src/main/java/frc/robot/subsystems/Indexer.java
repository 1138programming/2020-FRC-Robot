package frc.robot.subsystems;

import frc.robot.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

public class Indexer extends SubsystemBase {
  private final CANSparkMax indexer;
  private final CANEncoder indexerEncoder;
  private PIDController indexerPID;
  private double indexerPWM = 0;

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

    indexerEncoder = indexer.getEncoder();

    indexerPID = new PIDController(0, 0, 0, 0, 0.02);

    SmartDashboard.putNumber("Indexer Setpoint", 0.0);
    SmartDashboard.putNumber("Indexer P", indexerPID.getP());
    SmartDashboard.putNumber("Indexer I", indexerPID.getI());
    SmartDashboard.putNumber("Indexer D", indexerPID.getD());
    SmartDashboard.putNumber("Indexer F", indexerPID.getF());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Indexer Velocity", getIndexerVel());
    SmartDashboard.putNumber("Indexer PWM", indexerPWM);
    SmartDashboard.putNumber("Indexer Error", indexerPID.getError());
  }
  
  /**
   * @brief Moves the indexer
   * 
   * @param PWM Speed to move the indexer at
   */
  public void move(double PWM) {
    indexerPWM = PWM;
    indexer.set(PWM);
  }

  public double getIndexerPosition() {
    return indexerEncoder.getPosition();
  }

  public double getIndexerVel() {
    return indexerEncoder.getVelocity();
  }

  public void setSetpoint(double setpoint) {
    indexerPID.setSetpoint(setpoint);
  }

  public void calculate() {
    move(indexerPID.calculate(getIndexerVel()));
  }

  public boolean atSetpoint() {
    return indexerPID.atSetpoint();
  }

  public void setConstants(double kP, double kI, double kD, double kF) {
    indexerPID.setGains(kP, kI, kD, kF);
  }
}
