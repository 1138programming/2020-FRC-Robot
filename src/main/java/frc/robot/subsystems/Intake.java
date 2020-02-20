package frc.robot.subsystems;

import static frc.robot.Constants.KIntakeSpark;
import static frc.robot.Constants.KLeftIntakeSolenoid;
import static frc.robot.Constants.KRightIntakeSolenoid;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANEncoder;
import frc.robot.controller.PIDController;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;
import frc.robot.enums.RobotState;

public class Intake extends SubsystemBase {
  // Intake PID
  private PIDController intakeController;

  //Create victor
  private final CANSparkMax intake;

  private final CANEncoder intakeEncoder;

  // Create solenoids
  private Solenoid leftSolenoid;
  private Solenoid rightSolenoid;

  //Variables, enums, etc
  public SolenoidState intakePosition = SolenoidState.DEFAULT;
  
  /**
   * @brief This is the Intake
   */
  public Intake() {
    // instantiate victor
    intake = new CANSparkMax(KIntakeSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    // Configure spark. Factory defaults are restored, so every necessary configuration is included here
    intake.restoreFactoryDefaults();

    // Burn configurations to flash memory. This is where the sparks get configured upon being rebooted.
    // This protects against wrong configurations if the robot reboots during a match
    intake.burnFlash();

    intakeEncoder = intake.getEncoder();

    intakeController = new PIDController(0, 0, 0, 0.0001, 0.02);

    // instantiate solenoids
    leftSolenoid = new Solenoid(KLeftIntakeSolenoid); 
    rightSolenoid = new Solenoid(KRightIntakeSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * @brief Moves the intake directly
   * 
   * @param PWM Speed to move the intake at
   */
  public void move(double PWM) {
    intake.set(PWM);
  }

  public double getIntakeVel() {
    return intakeEncoder.getVelocity();
  }

  public void setSetpoint(double topSetpoint) {
    intakeController.setSetpoint(topSetpoint);
  }

  public void calculate() {
    move(intakeController.calculate(getIntakeVel()));
  }

  public boolean atSetpoint() {
    return intakeController.atSetpoint();
  }

  public void reset() {
    intakeController.reset();
  }

  public void setConstants(double Kp, double Ki, double Kd, double Kf) {
    intakeController.setP(Kp);
    intakeController.setI(Ki);
    intakeController.setD(Kd);
    intakeController.setF(Kf);
  }

  /**
   * @brief Sets the position of the intake solenoid
   * 
   * @param state The state of the solenoids on the intake
   */
  public void setIntakePosition(SolenoidState state) {
    intakePosition = state;
    leftSolenoid.set(state == SolenoidState.ACTIVE);
    rightSolenoid.set(state == SolenoidState.ACTIVE);
  }

  /**
   * @brief Gets the position of the intake
   * 
   * @return  State of the intake's solenoids
   */
  public SolenoidState getIntakePosition() {
    return intakePosition;
  }
}
