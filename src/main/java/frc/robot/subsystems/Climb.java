package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;

import frc.robot.enums.SolenoidState;

public class Climb extends SubsystemBase {

  private final TalonSRX ClimbLeft;
  private final VictorSPX ClimbRight;
  public static final int KClimbLeft = 1;
  public static final int KClimbRight = 2;

  private final Solenoid ClimbSolenoid;
  public static final int KClimbSolenoid = 6;

  public SolenoidState SolenoidState = SolenoidState.DEFAULT;
  
  public Climb() {
    ClimbLeft = new TalonSRX(KClimbLeft);
    ClimbRight = new VictorSPX(KClimbRight);

    ClimbLeft.setInverted(false);
    ClimbRight.setInverted(true);

    ClimbRight.follow(ClimbLeft);

    ClimbSolenoid = newSolenoid(KClimbSolenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveMotor(double LiftLeftSpeed, double LiftRightSpeed){
    ClimbLeft.set(ControlMode.PercentOutput, LiftLeftSpeed);
  }

  public void moveSolenoid(SolenoidState state) {
    SolenoidState = state;
    ClimbSolenoid.set(SolenoidState == SolenoidState.ACTIVE);
  }
}
