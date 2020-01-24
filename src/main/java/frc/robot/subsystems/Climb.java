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
  private final DigitalInput TopSensor;
  private final DigitalInput BottomSensor;

  public SolenoidState ratchetState = SolenoidState.DEFAULT;

  public double climbSpeed;
  
  public Climb() {
    climbTalon = new TalonSRX(KClimbTalon);
    climbVictor = new VictorSPX(KClimbVictor);
    TopSensor = new DigitalInput(KTopSensor);
    BottomSensor = new DigitalInput(KBottomSensor);

    climbTalon.setInverted(false);
    climbVictor.setInverted(true);

    climbVictor.follow(climbTalon);

    ratchetSolenoid = new Solenoid(KClimbRatchetSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void move(double climbSpeed){
    if (TopSensor.get() == false && BottomSensor.get() == false){
      climbTalon.set(ControlMode.PercentOutput, climbSpeed);
    }
  }

  public void moveUntilLimit(boolean movingUp){
    if (movingUp == true){
      if (TopSensor.get() == false && BottomSensor.get() == false){
        climbTalon.set(ControlMode.PercentOutput, climbSpeed);
      }
    }
    if (movingUp == false){
      if (TopSensor.get() == false && BottomSensor.get() == false){
        climbTalon.set(ControlMode.PercentOutput, -climbSpeed);
      }
    }
  }

  public void setRatchetState(SolenoidState state) {
    ratchetState = state;
    ratchetSolenoid.set(state == SolenoidState.ACTIVE);
  }

  public SolenoidState getRatchetState() {
    return ratchetState;
  }
}
