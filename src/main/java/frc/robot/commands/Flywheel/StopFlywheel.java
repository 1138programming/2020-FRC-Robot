package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot; 
import frc.robot.subsystems.Flywheel;
import frc.robot.enums.RobotState;
import static frc.robot.Constants.*;

public class StopFlywheel extends CommandBase {

  public StopFlywheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.RobotState == RobotState.SHOOTING){
      Robot.flywheel.move(0.8, 1.0);
    }else {
      Robot.flywheel.move(0.0, 0.0);
    }
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
