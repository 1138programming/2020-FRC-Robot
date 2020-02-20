package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;
import frc.robot.enums.StorageStage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import static frc.robot.Constants.*;

public class MoveStorageFor extends CommandBase {
  private StorageStage stage;
  private long startTime;
  private long length;

  public MoveStorageFor(StorageStage stage, double length) {
    this.stage = stage;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.storage);
  }

  public MoveStorageFor(double length) {
    this(StorageStage.BOTH, length);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.storage.move(KStoragePWM, stage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (System.currentTimeMillis() - startTime) > length;
  }
}
