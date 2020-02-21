package frc.robot.commands.Tilter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveTilterToIdealAngle extends CommandBase {

  /**
   * Creates a new MoveTilterTo.
   */
  public MoveTilterToIdealAngle() {
    addRequirements(Robot.tilter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.tilter.reset();
    Robot.tilter.setIdealSetpoint(45.11, Robot.camera.getDistance(), 8.1875);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.tilter.calculate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.tilter.atSetpoint();
  }
}