package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.Robot;
import frc.robot.enums.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import static frc.robot.Constants.*;

public class ClimbStop extends CommandBase {

  public ClimbStop() {
      addRequirements(Robot.climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.climb.setRatchetState(Value.kReverse);
    Robot.climb.move(0);
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