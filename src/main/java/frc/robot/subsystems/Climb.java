package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;

public class Climb extends SubsystemBase {

  private final TalonSRX climbTalon;
  private final VictorSPX climbVictor;

  private final Solenoid climbSolenoid;


  public SolenoidState climbState = SolenoidState.DEFAULT;
  
  public Climb() {
    climbTalon = new TalonSRX(KClimbTalon);
    climbVictor = new VictorSPX(KClimbVictor);

    climbTalon.setInverted(false);
    climbVictor.setInverted(true);

    climbVictor.follow(climbTalon);

    climbSolenoid = new Solenoid(KClimbSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveMotor(double LiftLeftSpeed, double LiftRightSpeed){
    climbTalon.set(ControlMode.PercentOutput, LiftLeftSpeed);
  }

  public void moveSolenoid(SolenoidState state) {
    climbState = state;
    climbSolenoid.set(state == SolenoidState.ACTIVE);
  }
}
