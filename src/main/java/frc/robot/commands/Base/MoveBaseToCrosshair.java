package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Camera;
import frc.robot.Robot;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveBaseToCrosshair extends CommandBase {
  public MoveBaseToCrosshair() {
      addRequirements(Robot.base);
      // We probably don't need with the camera, although we should probably check with Pauline
      //addRequirements(Robot.camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putBoolean("Base Aligned", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.base.setXOffConstants(
      SmartDashboard.getNumber("Base XOff P", 0.0),
      SmartDashboard.getNumber("Base XOff I", 0.0),
      SmartDashboard.getNumber("Base XOff D", 0.0)
    );

    SmartDashboard.putBoolean("Base Aligned", false);

    Robot.base.calculateXOff();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Base Aligned", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.base.atTargetXOff();
    //return false;
  }
}