package frc.robot.commands.Micellaneous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;

public class CancelAll extends CommandBase {

  public CancelAll() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially sAcheduled.
  @Override
  public void initialize() {
    CommandScheduler.getInstance().cancelAll();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
