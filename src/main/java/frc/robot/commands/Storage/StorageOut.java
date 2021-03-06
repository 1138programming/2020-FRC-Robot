package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;
import frc.robot.enums.StorageStage;

import static frc.robot.Constants.*;

public class StorageOut extends CommandBase {

  private StorageStage stage;

  public StorageOut(StorageStage stage) {
    this.stage = stage;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.storage);
  }

  public StorageOut() {
    this(StorageStage.BOTH);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.storage.move(-KStoragePWM, stage);
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
