package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Climb extends SubsystemBase {

  private final TalonSRX climbTalon;
  private final VictorSPX climbVictor;

  private final Solenoid ratchetSolenoid;

  private final DigitalInput TopLimit;
  private final DigitalInput BottomLimit;

  public SolenoidState ratchetState = SolenoidState.DEFAULT;

  private final PIDController climbPID;
  
  public Climb() {
    climbTalon = new TalonSRX(KClimbTalon);
    climbVictor = new VictorSPX(KClimbVictor);
    TopLimit = new DigitalInput(KTopLimit);
    BottomLimit = new DigitalInput(KBottomLimit);

    climbPID = new PIDController(0.0001, 0, 0);

    climbTalon.setInverted(false);
    climbVictor.setInverted(true);

    climbVictor.follow(climbTalon);

    ratchetSolenoid = new Solenoid(KClimbRatchetSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Moves climb without taking limits into account
   * 
   * @param speed Speed to move the climb at
   */
  private void moveWithoutLimits(double speed) {
    climbTalon.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Moves the climb, taking limits into account
   * 
   * @param speed Speed to move the climb at
   */
  public void move(double speed) {
    if (TopLimit.get() == false && BottomLimit.get() == false){      //limit logic in move, removed so we have a consistent move function and leave it to the methods
      moveWithoutLimits(speed);
    }
  }

  /**
   * Sets the state of the ratchet
   * 
   * @param state State of the pawl for the ratchet
   */
  public void setRatchetState(SolenoidState state) {
    ratchetState = state;
    ratchetSolenoid.set(state == SolenoidState.ACTIVE);
  }

  /**
   * Gets the state of the ratchet
   * 
   * @return  State of the pawl for the ratchet
   */
  public SolenoidState getRatchetState() {
    return ratchetState;
  }

  public double getClimbEncoder(){
    return climbTalon.getSelectedSensorPosition();
  }

  public void setSetpoint(double setpoint) {
    climbPID.setSetpoint(setpoint);
  }

  public double getSetpoint(){
    return climbPID.getSetpoint();
  }

  public void calculate(){
    move(climbPID.calculate(getClimbEncoder()));
  }
}
