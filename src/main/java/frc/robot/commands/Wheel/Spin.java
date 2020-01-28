package frc.robot.commands.Wheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.enums.ColorLabel;
import static frc.robot.Constants.*;

public class Spin extends CommandBase {
  private int halfSpins; // Number of half spins we have detected
  private ColorLabel initialColor; // The first color we detected
  private ColorLabel lastColor; // The last color that we have detected

  public Spin() {
    addRequirements(Robot.wheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    halfSpins = 0;
    initialColor = Robot.wheel.getColor();
    lastColor = initialColor;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.wheel.move(KWheelSpeed);
    if (lastColor != Robot.wheel.getColor() && Robot.wheel.getColor() == initialColor){
      halfSpins++;
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
    return halfSpins == 6;
  }
}
