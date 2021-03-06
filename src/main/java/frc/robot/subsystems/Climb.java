package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.controller.PIDController;

public class Climb extends SubsystemBase {

  private final TalonSRX climbTalon;
  private final VictorSPX climbVictor;

  //private final Solenoid ratchetSolenoid;
  private final DoubleSolenoid ratchetSolenoid;

  private final DigitalInput TopLimit;
  private final DigitalInput BottomLimit;

  //public DoubleSolenoid ratchetState = DoubleSolenoid.ACTIVE;

  private final PIDController climbPID;
  
  /**
   * @brief This is the Climb
   */

  public Climb() {
    climbTalon = new TalonSRX(KClimbTalon);
    climbVictor = new VictorSPX(KClimbVictor);
    TopLimit = new DigitalInput(KTopLimit);
    BottomLimit = new DigitalInput(KBottomLimit);

    climbPID = new PIDController(0.0001, 0, 0, 0, 0.02);

    climbTalon.setInverted(true);
    climbVictor.setInverted(true);

    climbVictor.follow(climbTalon);

    ratchetSolenoid = new DoubleSolenoid(KClimbRatchetSolenoidForward, KClimbRatchetSolenoidReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putString("climb ratchet state", getRatchetState().name());
    // SmartDashboard.putNumber("Climb Encoder", getClimbEncoder());
  }

  /**
   * @brief Moves climb without taking limits into account
   * 
   * @param PWM Speed to move the climb at
   */
  public void moveWithoutLimits(double PWM) { //CHANGE BACK TO PRIVATE
    climbTalon.set(ControlMode.PercentOutput, PWM);
  }

  /**
   * @brief Moves the climb, taking limits into account
   * 
   * @param PWM Speed to move the climb at
   */
  public void move(double PWM) {
    if (TopLimit.get() == false && BottomLimit.get() == false) { //limit logic in move, removed so we have a consistent move function and leave it to the methods
      moveWithoutLimits(PWM);
    }
    else if (TopLimit.get() == true && PWM >= 0) {
      moveWithoutLimits(PWM);
    }
    else if (BottomLimit.get() == true && PWM <= 0) {
      moveWithoutLimits(PWM);
    }
  }

  /**
   * @brief Sets the state of the ratchet
   * 
   * @param state State of the pawl for the ratchet
   */
  public void setRatchetState(Value state) {
    // ratchetState = state;
    ratchetSolenoid.set(state);
  }

  /**
   * @brief Gets the state of the ratchet
   * 
   * @return State of the pawl for the ratchet
   */
  // public DoubleSolenoid getRatchetState() {
  //   return DoubleSolenoid.Value;
  // }

  /**
   * @brief Gets the value of the climb encoder
   * 
   * @return The value of the climb encoder
   */

  public double getClimbEncoder() {
    return climbTalon.getSelectedSensorPosition();
  }

  /**
   * @brief Sets the setpoint for the climbPID
   * 
   * @param setpoint The setpoint
   */

  public void setSetpoint(double setpoint) {
    climbPID.setSetpoint(setpoint);
  }

  /**
   * @brief Gets the setpoint of the climbPID
   * 
   * @return The setpoint
   */

  public double getSetpoint() {
    return climbPID.getSetpoint();
  }

  /**
   * @brief Calculates the output of the climbPID and moves the climb motor with it
   * 
   */

  public void calculate() {
    move(climbPID.calculate(getClimbEncoder()));
  }
}
