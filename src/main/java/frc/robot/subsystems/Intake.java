package frc.robot.subsystems;

import static frc.robot.Constants.KIntakeVictor;
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
    intake = new CANSparkMax(KIntakeVictor, CANSparkMaxLowLevel.MotorType.kBrushless);

    // instantiate solenoids
    leftSolenoid = new Solenoid(KLeftIntakeSolenoid); 
    rightSolenoid = new Solenoid(KRightIntakeSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //move intake
  public void move(double speed){
    intake.set(speed);
  }

  //gets current state of the intake
  public SolenoidState getIntakePosition() {
    return intakePosition;
  }

  public void setIntakePosition(SolenoidState state) {
    intakePosition = state;
    leftSolenoid.set(state == SolenoidState.ACTIVE);
    rightSolenoid.set(state == SolenoidState.ACTIVE);
  }
}
