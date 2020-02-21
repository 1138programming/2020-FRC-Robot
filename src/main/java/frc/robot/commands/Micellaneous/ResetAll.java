package frc.robot.commands.Micellaneous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Wheel;
import frc.robot.subsystems.Tilter;
import frc.robot.subsystems.Pneumatics;
import static frc.robot.Constants.*;
import frc.robot.Robot;

public class ResetAll extends CommandBase {

  public ResetAll() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.base);
    addRequirements(Robot.flywheel);
    addRequirements(Robot.indexer);
    addRequirements(Robot.intake);
    addRequirements(Robot.storage);
    addRequirements(Robot.tilter);
    addRequirements(Robot.wheel);
    addRequirements(Robot.pneumatics);
  }

  // Called when the command is initially sAcheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      Robot.base.move(0,0);
      Robot.flywheel.move(0,0);
      Robot.indexer.move(0);
      Robot.intake.move(0);
      Robot.storage.move(0);
      Robot.tilter.move(0);
      Robot.wheel.move(0);
      Robot.pneumatics.setCompressor(false);
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
