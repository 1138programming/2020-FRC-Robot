package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Constants.*;


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
