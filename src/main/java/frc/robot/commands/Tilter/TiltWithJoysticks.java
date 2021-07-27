package frc.robot.commands.Tilter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Tilter;
import static frc.robot.Constants.*;

public class TiltWithJoysticks extends CommandBase {
  /**
   * Creates a new TiltWithJoysticks.
   */
  public TiltWithJoysticks() {
    addRequirements(Robot.tilter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double PWM = Robot.m_robotContainer.getXboxLeftAxis();
    Robot.tilter.move(PWM);
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
