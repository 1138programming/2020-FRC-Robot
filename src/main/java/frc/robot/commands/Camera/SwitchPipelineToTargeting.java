package frc.robot.commands.Camera;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;
import frc.robot.Robot;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.enums.PipelineState;

public class SwitchPipelineToTargeting extends CommandBase {
  public SwitchPipelineToTargeting() {
      addRequirements(Robot.camera);
      // We probably don't need with the camera, although we should probably check with Pauline
      //addRequirements(Robot.camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.camera.setPipeline(PipelineState.TARGETING.getValue());
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