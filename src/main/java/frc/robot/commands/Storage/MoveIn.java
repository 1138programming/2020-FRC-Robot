package frc.robot.commands.Storage;
import frc.robot.Robot;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.enums.StorageStage;

public class MoveIn extends CommandBase {

  public static boolean LastState;

  public MoveIn() {
    addRequirements(Robot.storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.storage.move(Storage.KStorageSpeed, StorageStage.BOTH);
    if (Storage.BallSensor1.get() == false && Storage.BallSensor1.get() != LastState) {
      Storage.isIntaking = true; 
    } 
    LastState = Storage.BallSensor1.get();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
