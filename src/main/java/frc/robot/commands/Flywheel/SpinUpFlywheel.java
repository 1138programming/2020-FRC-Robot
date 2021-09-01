package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class SpinUpFlywheel extends CommandBase {
  private double topSetpoint, bottomSetpoint;

  public SpinUpFlywheel(double topSetpoint, double bottomSetpoint) {
    this.topSetpoint = topSetpoint;
    this.bottomSetpoint = bottomSetpoint;

    addRequirements(Robot.flywheel);
  }

  public SpinUpFlywheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    this(1800, 1800);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.flywheel.setSetpoints(topSetpoint, bottomSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //atTopSetpoint = Robot.flywheel.atTopSetpoint();
    //atBottomSetpoint = Robot.flywheel.atBottomSetpoint();

    /*Robot.flywheel.setTopConstants(
      SmartDashboard.getNumber("Flywheel Top P", 0.0),
      SmartDashboard.getNumber("Flywheel Top I", 0.0),
      SmartDashboard.getNumber("Flywheel Top D", 0.0),
      SmartDashboard.getNumber("Flywheel Top F", 0.0)
    );

    Robot.flywheel.setBottomConstants(
      SmartDashboard.getNumber("Flywheel Bottom P", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom I", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom D", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom F", 0.0)
    );*/

    //double topSetpoint = SmartDashboard.getNumber("Flywheel Top Setpoint", 0.0);
    //double bottomSetpoint = SmartDashboard.getNumber("Flywheel Bottom Setpoint", 0.0);
    //Robot.flywheel.setSetpoints(topSetpoint, bottomSetpoint);

    Robot.flywheel.setTopConstants(.00025, .00047, 0, .000178);
    Robot.flywheel.setBottomConstants(.000208, .00047, 0, .000178);

    Robot.flywheel.calculate();
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
