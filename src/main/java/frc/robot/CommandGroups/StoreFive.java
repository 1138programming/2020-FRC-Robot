package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import frc.robot.Robot;
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.enums.StorageStage;

public class StoreFive extends SequentialCommandGroup {
  public StoreFive() {
    addCommands(
      new IntakeDeploy(),
      new IntakeIn(),
      parallel(
        new EngageStage2(),
        new StorageIn(StorageStage.BOTH)
      )
    );
  }
}