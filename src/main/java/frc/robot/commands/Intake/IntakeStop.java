package frc.robot.commands.Intake;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.enums.RobotState;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;

public class IntakeStop extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public IntakeStop() {
    addRequirements(Robot.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.robotState == RobotState.COLLECTING && Robot.storage.getBallCount() < 5) {
      if (Robot.intake.getIntakePosition() == SolenoidState.DEFAULT) {
        Robot.intake.setIntakePosition(SolenoidState.ACTIVE);
        Robot.intake.move(0);
      } else {
        Robot.intake.move(KIntakePWM);
      }
    } else {
      Robot.intake.setIntakePosition(SolenoidState.DEFAULT);
      Robot.intake.move(0);
    }
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