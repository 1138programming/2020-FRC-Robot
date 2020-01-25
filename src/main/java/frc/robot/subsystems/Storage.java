package frc.robot.subsystems;

import static frc.robot.Constants.KBallSensor1;
import static frc.robot.Constants.KBallSensor2;
import static frc.robot.Constants.KStage1Talon;
import static frc.robot.Constants.KStage2Talon;
import static frc.robot.Constants.KStorageShifterSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;
import frc.robot.enums.StorageStage;

public class Storage extends SubsystemBase {
  //Create the talons
  private final VictorSPX stage1;
  private final VictorSPX stage2;

  //Create the solenoid
  private final Solenoid shifter;

  //Create the sensors
  private final DigitalInput ballSensor1;
  private final DigitalInput ballSensor2;

  //Variables, enums, etc.
  private int numberOfBalls = 0;
  private boolean isIntaking;
  private SolenoidState shifterState = SolenoidState.DEFAULT;
  private boolean ballState1LastState = false; // Keeps track of the last state of the 1st ball sensor
  private double stage1Speed = 0; // Keeps track of the speed of stage 1

  public Storage() {
    //Instantiate everything
    stage1 = new VictorSPX(KStage1Talon);
    stage2 = new VictorSPX(KStage2Talon);
    shifter = new Solenoid(KStorageShifterSolenoid);
    ballSensor1 = new DigitalInput(KBallSensor1);
    ballSensor2 = new DigitalInput(KBallSensor2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    boolean ballSensor1State = ballSensor1.get();

    // Counts balls as in on the rising edge and out on the falling edge
    if (stage1Speed > 0) {
      if (ballSensor1State && !ballState1LastState) {
          numberOfBalls++;
      }
    } else {
      if (!ballSensor1State && ballState1LastState) {
        numberOfBalls--;
      }
    }
    ballState1LastState = ballSensor1State;
  }

  // Moves the storage based on which stage we want moving
  public void move(double speed) {
    move(speed, StorageStage.BOTH);
  }

  public void move(double speed, StorageStage stage) {
    if (stage == StorageStage.STAGE1) {
      stage1Speed = speed;
      stage1.set(ControlMode.PercentOutput, speed);
    }

    if (stage == StorageStage.STAGE2) {
      stage2.set(ControlMode.PercentOutput, speed);
    }

    if (stage == StorageStage.BOTH) {
      stage1Speed = speed;
      stage1.set(ControlMode.PercentOutput, speed);
      // Make sure stage 2 is engaged before running the motor
      if (shifterState == SolenoidState.ACTIVE) {
        stage2.set(ControlMode.PercentOutput, speed);
      }
    }
  }

  // Sets the shifter to extended (active) if desired, or retracted (default) if not
  public void setShifterState(SolenoidState state){
    shifterState = state;
    shifter.set(state == SolenoidState.ACTIVE);
  }

  // Gets the state of the shifter
  public SolenoidState getShifterState() {
    return shifterState;
  }
}
