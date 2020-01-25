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

public class Climb extends SubsystemBase {

  private final TalonSRX climbTalon;
  private final VictorSPX climbVictor;

  private final Solenoid ratchetSolenoid;

  private final DigitalInput TopLimit;
  private final DigitalInput BottomLimit;

  public SolenoidState ratchetState = SolenoidState.DEFAULT;
  
  public Climb() {
    climbTalon = new TalonSRX(KClimbTalon);
    climbVictor = new VictorSPX(KClimbVictor);
    TopLimit = new DigitalInput(KTopLimit);
    BottomLimit = new DigitalInput(KBottomLimit);

    climbTalon.setInverted(false);
    climbVictor.setInverted(true);

    climbVictor.follow(climbTalon);

    ratchetSolenoid = new Solenoid(KClimbRatchetSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  private void moveWithoutLimits(double speed) {
    climbTalon.set(ControlMode.PercentOutput, speed);
  }

  public void move(double speed) {
    if (TopLimit.get() == false && BottomLimit.get() == false){      //limit logic in move, removed so we have a consistent move function and leave it to the methods
      moveWithoutLimits(speed);
    }
  }

  /*public void moveWithLimits(boolean movingUp) {
    if (movingUp == true) {
      if (TopLimit.get() == false && BottomLimit.get() == false){
        //climbTalon.set(ControlMode.PercentOutput, KClimbSpeed);
        move(KClimbSpeed);
      }
    }
    else if (movingUp == false) {
      if (TopLimit.get() == false && BottomLimit.get() == false){
        //climbTalon.set(ControlMode.PercentOutput, -KClimbSpeed);
        move(-KClimbSpeed);
      }
    }
  }*/

  public void setRatchetState(SolenoidState state) {
    ratchetState = state;
    ratchetSolenoid.set(state == SolenoidState.ACTIVE);
  }

  public SolenoidState getRatchetState() {
    return ratchetState;
  }
}
