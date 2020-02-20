package frc.robot.subsystems;

import static frc.robot.Constants.KBallSensor1;
import static frc.robot.Constants.KBallSensor2;
import static frc.robot.Constants.KStage1Victor;
import static frc.robot.Constants.KStage2Victor;
import static frc.robot.Constants.KStorageShifterSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.SolenoidState;
import frc.robot.enums.StorageStage;
import edu.wpi.first.wpilibj.SlewRateLimiter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Storage extends SubsystemBase {
  // Create the talons
  private final VictorSPX stage1;
  private final VictorSPX stage2;

  // Create the solenoid
  private final Solenoid shifter;

  // Create the sensors
  private final DigitalInput ballSensor1;
  private final DigitalInput ballSensor2;

  // Variables, enums, etc.
  private int ballCount = 0;
  private SolenoidState shifterState = SolenoidState.ACTIVE;
  private boolean ballSensor1LastState = false; // Keeps track of the last state of the 1st ball sensor
  private boolean ballSensor2LastState = false; // Keeps track of the last state of the 2nd ball sensor
  private double stage1PWM = 0; // Keeps track of the speed of stage 1
  private double stage2PWM = 0; // Keeps track of the speed of stage 2

  private SlewRateLimiter stage2Limiter;

  /**
   * @brief This is the Storage subsystem
   */

  public Storage() {
    // Instantiate everything
    stage1 = new VictorSPX(KStage1Victor);
    stage2 = new VictorSPX(KStage2Victor);
    shifter = new Solenoid(KStorageShifterSolenoid);
    ballSensor1 = new DigitalInput(KBallSensor1);
    ballSensor2 = new DigitalInput(KBallSensor2);

    stage2.setNeutralMode(NeutralMode.Brake);

    stage2Limiter = new SlewRateLimiter(3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateBallCount();

    SmartDashboard.putNumber("Storage BallCount", ballCount);
    SmartDashboard.putBoolean("Storage Ball Sensor 1", getBallSensor1());
  }

  /**
   * @brief Moves the given conveyor belt(s) directly
   * 
   * @param PWM Speed to move the conveyor belt(s) at
   * @param stage The stage(s) of the storage we want to move
   */
  public void move(double PWM, StorageStage stage) {
    if (stage == StorageStage.STAGE1) {
      stage1PWM = PWM;
      stage1.set(ControlMode.PercentOutput, PWM);
    } else if (stage == StorageStage.STAGE2) {
      stage2PWM = stage2Limiter.calculate(PWM);
      stage2.set(ControlMode.PercentOutput, stage2PWM);
    } else if (stage == StorageStage.BOTH) {
      stage1PWM = PWM;
      stage2PWM = stage2Limiter.calculate(PWM);
      stage1.set(ControlMode.PercentOutput, PWM);
      stage2.set(ControlMode.PercentOutput, stage2PWM);
    }
  }

  /**
   * @brief Moves both conveyor belts directly
   * 
   * @param PWM Speed to move the conveyor belts at
   */
  public void move(double PWM) {
    move(PWM, StorageStage.BOTH);
  }

  /**
   * @brief Set the state of the dogshifter to engage/disengage stage 2
   * 
   * @param state Change the state of the solenoid on the dogshifter
   */
  public void setShifterState(SolenoidState state) {
    shifterState = state;
    shifter.set(state == SolenoidState.ACTIVE);
  }

  /**
   * @brief Gets the state of the shifter
   * 
   * @return The shifter's state
   */
  public SolenoidState getShifterState() {
    return shifterState;
  }

  /**
   * @brief Tracks the number of balls in the storage
   */
  private void updateBallCount() {
    boolean ballSensor1State = ballSensor1.get();
    boolean ballSensor2State = ballSensor2.get();

    // Handles counting balls entering/exiting from the first stage
    if (stage1PWM > 0) {
      // Counts ball as in on the rising edge
      if (ballSensor1State && !ballSensor1LastState) {
        ballCount++;
      }
    } else {
      // Counts ball as out on the falling edge
      if (!ballSensor1State && ballSensor1LastState) {
        ballCount--;
      }
    }

    // Handles counting balls entering/exiting from the second stage
    if (stage2PWM > 0) {
      // Counts ball as out on the falling edge
      if (!ballSensor2State && ballSensor2LastState) {
        ballCount--;
      }
    } else {
      // Counts ball as in on the rising edge
      if (ballSensor2State && !ballSensor2LastState) {
        ballCount++;
      }
    }

    // Hard code known states where the storage has 4 or 5 balls
    if (ballSensor2State) {
      if (ballSensor1State) {
        ballCount = 5;
      } else {
        ballCount = 4;
      }
    }

    // Clamp ballCount to be between 0 and 5, inclusive
    ballCount = MathUtil.clamp(ballCount, 0, 5);

    // Update ball sensor last states
    ballSensor1LastState = ballSensor1State;
    ballSensor2LastState = ballSensor2State;
  }

  public int getBallCount() {
    return ballCount;
  }

  public void setBallCount(int ballCount) {
    this.ballCount = ballCount;
  }

  public boolean getBallSensor1() {
    return ballSensor1.get();
  }

  public boolean getBallSensor2() {
    return ballSensor2.get();
  }
}