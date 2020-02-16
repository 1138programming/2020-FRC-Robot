package frc.robot.commands.Pneumatics;

import frc.robot.Robot;
import frc.robot.subsystems.Pneumatics;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.Compressor;


public class CompressorControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public CompressorControl() {
    addRequirements(Robot.pneumatics);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.pneumatics.setCompressor(false);
  }
  
  @Override
  public void execute() {
    /*if (Robot.pneumatics.getCurrent() > 9.0){
      Robot.pneumatics.setCompressor(false);
    }
    else {
      Robot.pneumatics.setCompressor(true);
    }*/
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