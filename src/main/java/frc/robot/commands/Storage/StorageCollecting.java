package frc.robot.commands.Storage;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;
import frc.robot.enums.StorageStage;

import static frc.robot.Constants.*;

public class StorageCollecting extends CommandBase {
  public StorageCollecting() {
    addRequirements(Robot.storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean ballSensor1State = Robot.storage.getBallSensor1();

    // If the storage has less than 5 balls, run stage2 whenever ball sensor 1 detects a ball
    if (Robot.storage.getBallCount() < 5) {
        // Run stage 1 to get balls into the storage
        Robot.storage.move(KStoragePWM, StorageStage.STAGE1);

        // If ball sensor 1 is detecting a ball, run stage 2, otherwise stop it
        if (ballSensor1State) {
            Robot.storage.move(KStoragePWM, StorageStage.BOTH);
        } else {
            Robot.storage.move(0, StorageStage.STAGE2);
        }
    } else { 
        // Run stage1 when ball sensor 1 does not detect a ball
        if (ballSensor1State) {
            Robot.storage.move(0, StorageStage.STAGE1);
        } else {
            Robot.storage.move(KStoragePWM, StorageStage.STAGE1);
        }
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
