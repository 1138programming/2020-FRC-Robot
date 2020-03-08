package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.enums.StorageStage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import static frc.robot.Constants.*;

public class MoveStorageFor extends CommandBase {
  private double PWM;
  private StorageStage stage;
  private long duration;
  private long startTime;

  public MoveStorageFor(double PWM, StorageStage stage, long duration) {
    this.PWM = PWM;
    this.stage = stage;
    this.duration = duration;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.storage);
  }

  public MoveStorageFor(StorageStage stage, long duration) {
    this(KStoragePWM, stage, duration);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.storage.move(PWM, stage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.storage.move(0, stage);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (duration != 0) && (System.currentTimeMillis() - startTime) > duration;
  }
}
