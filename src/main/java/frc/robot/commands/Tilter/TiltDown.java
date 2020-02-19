package frc.robot.commands.Tilter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class TiltDown extends CommandBase {
  /**
   * Creates a new TiltDown.
   */
  public TiltDown() {
    addRequirements(Robot.tilter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.tilter.move(-KTilterSpeed);
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