package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveWithJoysticks extends CommandBase {

  public DriveWithJoysticks() {
    addRequirements(Robot.base);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Base Turn Speed", 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPWM = Robot.m_robotContainer.getLeftAxis();
    double rightPWM = Robot.m_robotContainer.getRightAxis();
    //double leftPWM = SmartDashboard.getNumber("Base Turn Speed", 0.0);
    //double rightPWM = -SmartDashboard.getNumber("Base Turn Speed", 0.0);
    
    Robot.base.move(leftPWM, rightPWM);
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
