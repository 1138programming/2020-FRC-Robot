package frc.robot.commands.Wheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class WheelStop extends CommandBase {

  public WheelStop() {
    addRequirements(Robot.wheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.wheel.move(0);
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
