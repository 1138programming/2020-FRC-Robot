package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import frc.robot.enums.StorageStage;
import frc.robot.enums.RobotState;
import frc.robot.CommandGroups.MoveBallIn;
import frc.robot.commands.Storage.MoveStorageFor;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class StorageStop extends CommandBase {
  boolean ballSensor1LastState = false;

  public StorageStop() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ballSensor1LastState = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean ballSensor1State = Robot.storage.getBallSensor1();

    SmartDashboard.putBoolean("Entering Scope", false);
    if (Robot.RobotState == RobotState.COLLECTING) {
      if (Robot.storage.getBallCount() < 5) {
        Robot.storage.move(KStoragePWM, StorageStage.STAGE1);
        if (ballSensor1State) {
          SmartDashboard.putBoolean("Entering Scope", true);
          //CommandScheduler.getInstance().schedule(new MoveBallIn());
          //CommandScheduler.getInstance().schedule(new MoveStorageFor(StorageStage.BOTH, 100));
          Robot.storage.move(KStoragePWM, StorageStage.BOTH);
        } else {
          Robot.storage.move(0, StorageStage.STAGE2);
        }
      } else {
        if (ballSensor1State) {
          Robot.storage.move(0, StorageStage.STAGE1);
        } else {
          Robot.storage.move(KStoragePWM, StorageStage.STAGE1);
        }
      }
    } else if (Robot.RobotState == RobotState.SHOOTING) {
      Robot.storage.move(KStoragePWM, StorageStage.BOTH);
    } else {
      Robot.storage.move(0, StorageStage.BOTH);
    }

    ballSensor1LastState = ballSensor1State;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
