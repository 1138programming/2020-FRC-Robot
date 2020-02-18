package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import frc.robot.enums.StorageStage;
import frc.robot.enums.RobotState;
import frc.robot.CommandGroups.MoveBallIn;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class StorageStop extends CommandBase {

  public StorageStop() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.RobotState == RobotState.COLLECTING){
      Robot.storage.move(KStorageSpeed, StorageStage.STAGE1);
      if (Robot.storage.getBallSensor1() && !Robot.storage.getBallSensor1LastState()) {
        MoveBallIn moveBallIn = new MoveBallIn();
        CommandScheduler.getInstance().schedule(moveBallIn);
      }
    }
    if (Robot.RobotState == RobotState.SHOOTING){
      Robot.storage.move(KStorageSpeed, StorageStage.BOTH);
    } else {
      Robot.storage.move(0, StorageStage.BOTH);
    }
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
