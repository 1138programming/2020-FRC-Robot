package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.enums.SolenoidState;
import frc.robot.enums.StorageStage;
import edu.wpi.first.wpilibj.DigitalInput;

public class Storage extends SubsystemBase {
  
  //Create the talons
  private final TalonSRX Stage1;
  private final TalonSRX Stage2;
  public static final int KStage1Talon = 9;
  public static final int KStage2Talon = 10;

  //Create the solenoid
  private Solenoid Shifter;
  public static final int KSolenoid = 11;

  //Create the sensors
  public static DigitalInput BallSensor1;
  public static DigitalInput BallSensor2;
  public static final int KBallSensor1 = 12; 
  public static final int KBallSensor2 = 13; 

  //Variables, enums, etc.
  public static double KStorageSpeed = 1; 
  public static int numberOfBalls = 0;
  public SolenoidState StorageSolenoidState;

  public Storage() {
    //Instantiate everything
    Stage1 = new TalonSRX(KStage1Talon);
    Stage2 = new TalonSRX(KStage2Talon);
    Shifter = new Solenoid(KSolenoid);
    BallSensor1 = new DigitalInput(KBallSensor1);
    BallSensor2 = new DigitalInput(KBallSensor2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //moves the storage based on which stage we want moving
  public void move(double speed, StorageStage stage) {
    if (stage == StorageStage.STAGE1) {
      Stage1.set(ControlMode.PercentOutput, speed);
    }
    if (stage == StorageStage.STAGE2) {
      Stage2.set(ControlMode.PercentOutput, speed);
    }
    if (stage == StorageStage.BOTH) {
      Stage1.set(ControlMode.PercentOutput, speed);
      Stage2.set(ControlMode.PercentOutput, speed);
    }
  }

  //sets the shifter to extended (active) if desired, or retracted (default) if not
  public void setShifter(SolenoidState state){
    StorageSolenoidState = state;
    Shifter.set(StorageSolenoidState == SolenoidState.ACTIVE);
  }

  //increment or decrements our ball counter depending on the direction of intake (UNFINISHED)
  public int ballCount(boolean isIntaking){
    numberOfBalls +=1;
    return numberOfBalls;
  }
}
