package frc.robot.commands.Camera;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Camera;
import frc.robot.Robot;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveBaseToTarget extends CommandBase {

  public MoveBaseToTarget() {
      addRequirements(Robot.base);
      addRequirements(Robot.camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double output = Robot.camera.getXOutput();
    if(-0.1 < output && 0.1 > output) {
      Robot.base.move(0.0, 0.0);
    }
    else if(-0.1 < output && 0.1 < output) {
      Robot.base.move(-0.2, 0.2);      
    }
    else if(-0.1 > output && 0.1 > output) {
      Robot.base.move(0.2, -0.2);
    }

    SmartDashboard.putNumber("xOutPut", output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}