package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;

public class SpinUpIndexer extends CommandBase {
  public SpinUpIndexer() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.indexer.setSetpoint(SmartDashboard.getNumber("Indexer Setpoint", 0.0));
    Robot.indexer.setConstants(
        SmartDashboard.getNumber("Indexer P", 0.0),
        SmartDashboard.getNumber("Indexer I", 0.0), 
        SmartDashboard.getNumber("Indexer D", 0.0),
        SmartDashboard.getNumber("Indexer F", 0.0)
    );

    Robot.indexer.calculate();
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
