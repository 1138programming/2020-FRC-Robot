package frc.robot.commands.Base;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Base;

import static frc.robot.Constants.*;

public class DriveUntilFull extends CommandBase {
  public DriveUntilFull() {
    addRequirements(Robot.base);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.base.move(.5, .5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.storage.getBallCount() >= 5;
  }
}
