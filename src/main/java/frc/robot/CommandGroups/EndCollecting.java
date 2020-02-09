package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import frc.robot.Robot;
import frc.robot.commands.Intake.IntakeRetract;
import frc.robot.commands.Intake.IntakeStop;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Wheel.WheelStop;

public class EndCollecting extends SequentialCommandGroup {
  public EndCollecting() {
    addCommands(
      parallel(
        new IntakeRetract(),
        new IntakeStop()
      )
    );
  }
}