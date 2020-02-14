package frc.robot.subsystems;

import static frc.robot.Constants.KIntakeSpark;
import static frc.robot.Constants.KLeftIntakeSolenoid;
import static frc.robot.Constants.KRightIntakeSolenoid;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;


public class Intake extends SubsystemBase {

  //Create victor
  private final CANSparkMax intake;

  // Create solenoids
  private Solenoid leftSolenoid;
  private Solenoid rightSolenoid;

  //Variables, enums, etc
  public SolenoidState intakePosition = SolenoidState.DEFAULT;
  
  /**
   * Creates a new Intake.
   */
  public Intake() {
    // instantiate victor
    intake = new CANSparkMax(KIntakeSpark, CANSparkMaxLowLevel.MotorType.kBrushless);

    // instantiate solenoids
    leftSolenoid = new Solenoid(KLeftIntakeSolenoid); 
    rightSolenoid = new Solenoid(KRightIntakeSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Moves the intake directly
   * 
   * @param speed Speed to move the intake at
   */
  public void move(double speed){
    intake.set(speed);
  }

  /**
   * Sets the position of the intake solenoid
   * 
   * @param state The state of the solenoids on the intake
   */
  public void setIntakePosition(SolenoidState state) {
    intakePosition = state;
    leftSolenoid.set(state == SolenoidState.ACTIVE);
    rightSolenoid.set(state == SolenoidState.ACTIVE);
  }

  /**
   * Gets the position of the intake
   * 
   * @return  State of the intake's solenoids
   */
  public SolenoidState getIntakePosition() {
    return intakePosition;
  }
}
