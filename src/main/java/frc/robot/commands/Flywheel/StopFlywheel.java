package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.enums.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;

public class StopFlywheel extends CommandBase {

  public StopFlywheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.flywheel.setSetpoints(2700, 2900);
    // Robot.flywheel.setSetpoints(SmartDashboard.getNumber("Flywheel Top Setpoint", 0.0), SmartDashboard.getNumber("Flywheel Bottom Setpoint", 0.0));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.robotState == RobotState.SHOOTING) {
      Robot.flywheel.calculate();
    } else {
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
