package frc.robot.commands.Wheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Wheel;
import frc.robot.enums.ColorLabel;
import frc.robot.enums.RotationDirection;
import static frc.robot.Constants.*;

public class Spin extends CommandBase {
  private int count;
  ColorLabel initialColor;
  ColorLabel lastColor;
  

  public Spin() {
    addRequirements(Robot.wheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    count = 0;
    initialColor = Robot.wheel.getColor();
    lastColor = Robot.wheel.getColor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.wheel.move(1);
    if (lastColor != Robot.wheel.getColor() && Robot.wheel.getColor() == initialColor){
      count++;
    }
    lastColor = Robot.wheel.getColor();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count == 6;
  }
}
