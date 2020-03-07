package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveWithJoysticks extends CommandBase {

  public DriveWithJoysticks() {
    addRequirements(Robot.base);
  }

  private double quadraticCurve(double input) {
    if (input > 0) {
      input *= input;
    } else {
      input *= -input;
    }

    return input;
  }

  private double cubicCurve(double input) {
    return input * input * input;
  }

  private double quarticCurve(double input) {
    if (input > 0) {
      input *= input;
      input *= input;
    } else {
      input *= input;
      input *= -input;
    }

    return input;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Base Turn Speed", 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPWM = 0;
    double rightPWM = 0;

    if(Robot.autonomousActive == false) {
      //leftPWM = quadraticCurve(Robot.m_robotContainer.getLeftAxis());
      //rightPWM = quadraticCurve(Robot.m_robotContainer.getRightAxis());
      double leftY = quadraticCurve(Robot.m_robotContainer.getLeftAxis());
      double rightX = Robot.m_robotContainer.getArcadeRightAxis() * 0.4; //cubicCurve(Robot.m_robotContainer.getArcadeRightAxis());
      leftPWM = leftY - rightX;
      rightPWM = leftY + rightX;
    }

    //double leftPWM = SmartDashboard.getNumber("Base Turn Speed", 0.0);
    //double rightPWM = -SmartDashboard.getNumber("Base Turn Speed", 0.0);
    //double leftPWM = 0.0;
    //double rightPWM = 0.0;

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
