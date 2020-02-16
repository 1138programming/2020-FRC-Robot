package frc.robot.commands.Wheel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.enums.ColorLabel;
import frc.robot.enums.RotationDirection;
import static frc.robot.Constants.*;

public class GoToColor extends CommandBase {
  private double speed; 
  private ColorLabel targetColor;
  private ColorLabel currentColor;

  public GoToColor() {
    addRequirements(Robot.wheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("working", "yes");
    speed = 0;
    targetColor = Robot.wheel.getTargetColor();
    currentColor = Robot.wheel.getColor();
    RotationDirection spinDirection = Robot.wheel.getRotationDirection(currentColor, targetColor);

    if (spinDirection == RotationDirection.CLOCKWISE) {
      speed = -KWheelSpeed;
    } 
    else if (spinDirection == RotationDirection.COUNTERCLOCKWISE) {
      speed = KWheelSpeed;
    }
    else {
      speed = 0;
    }
    
    SmartDashboard.putNumber("speed",speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.wheel.move(speed);
    currentColor = Robot.wheel.getColor();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.wheel.move(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return currentColor == targetColor || speed == 0;
  }
}
