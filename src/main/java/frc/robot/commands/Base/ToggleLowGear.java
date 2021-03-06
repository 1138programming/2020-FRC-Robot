package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Base;
import frc.robot.enums.BaseState;
import static frc.robot.Constants.*;

public class ToggleLowGear extends CommandBase {
  public ToggleLowGear() {
    addRequirements(Robot.base);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.base.getBaseState() == BaseState.LOW) {
        Robot.base.setBaseState(BaseState.MEDIUM);
    } else {
        Robot.base.setBaseState(BaseState.LOW);
    }
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
