package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class SpinUpFlywheel extends CommandBase {
  long timeTolerance = 30;

  long timeRefTop = 0;
  long timeTop = 0;
  boolean atTopSetpointLast = false;
  boolean atTopSetpoint = false;

  long timeRefBottom = 0;
  long timeBottom = 0;
  boolean atBottomSetpointLast = false;
  boolean atBottomSetpoint = false;

  public SpinUpFlywheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Flywheel Setpoint Top", 0.0);
    //SmartDashboard.putNumber("Flywheel Setpoint Bottom", 0.0);

    timeRefTop = System.currentTimeMillis();
    timeRefBottom = System.currentTimeMillis();

    atTopSetpointLast = true;
    atBottomSetpointLast = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    atTopSetpoint = Robot.flywheel.atTopSetpoint();
    atBottomSetpoint = Robot.flywheel.atBottomSetpoint();

    Robot.flywheel.setTopConstants(
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
    );

    Robot.flywheel.setSetpoints(SmartDashboard.getNumber("Flywheel Setpoint Top", 0.0), SmartDashboard.getNumber("Flywheel Setpoint Bottom", 0.0));
    Robot.flywheel.calculate();
    //Robot.flywheel.move(0.05, 0.05);

    SmartDashboard.putBoolean("Flywheel Top At Setpoint", atTopSetpoint);
    SmartDashboard.putBoolean("Flywheel Bottom At Setpoint", atBottomSetpoint);

    if (!atTopSetpoint && atTopSetpointLast) {
      timeRefTop = System.currentTimeMillis();
    }
    if (atTopSetpoint && !atTopSetpointLast) {
      long pTime = System.currentTimeMillis() - timeRefTop;
      if (pTime > timeTolerance) {
        timeTop = pTime;
      }
    }

    if (!atBottomSetpoint && atBottomSetpoint) {
      timeRefBottom = System.currentTimeMillis();
    }
    if (atBottomSetpoint && !atBottomSetpointLast) {
      long pTime = System.currentTimeMillis() - timeRefBottom;
      if (pTime > timeTolerance) {
        timeBottom = pTime;
      }
    }

    SmartDashboard.putNumber("Flywheel top re-spin time", timeTop);
    SmartDashboard.putNumber("Flywheel bottom re-spin time", timeBottom);

    atTopSetpointLast = atTopSetpoint;
    atBottomSetpointLast = atBottomSetpoint;
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
