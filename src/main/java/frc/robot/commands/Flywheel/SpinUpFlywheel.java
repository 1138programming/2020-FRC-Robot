package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class SpinUpFlywheel extends CommandBase {

  public SpinUpFlywheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Flywheel Setpoint Top", 0.0);
    //SmartDashboard.putNumber("Flywheel Setpoint Bottom", 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Robot.flywheel.setSetpoints(SmartDashboard.getNumber("Flywheel Setpoint Top", 0.0), SmartDashboard.getNumber("Flywheel Setpoint Bottom", 0.0));
    //Robot.flywheel.setSetpoints(0.1, 0.1);
    //Robot.flywheel.calculate();
    Robot.flywheel.move(1, 1);
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
