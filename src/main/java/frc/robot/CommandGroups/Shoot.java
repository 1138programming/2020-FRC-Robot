package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.enums.StorageStage;

public class Shoot extends SequentialCommandGroup {
  public Shoot() {
    addCommands(
      parallel(
        new IndexIn(),
        new EngageStage2(),
        new StorageIn(StorageStage.BOTH)
      )
    );
  }
}