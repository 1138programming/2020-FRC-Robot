package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Base.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.commands.Indexer.Indexin;
import frc.robot.commands.Storage.MoveIn;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Storage.EngageStage2;

public class TrenchShot extends SequentialCommandGroup {
  public TrenchShot() {
    addCommand(
      parallel(
        new SpinUpFlywheel(),
        new MoveBaseToTarget(),
        new MoveTilterTo(1) // TODO: Change Value
      ),
      parallel(
        new IndexIn(),
        new EngageStage2();
        new MoveIn(StorageStage.BOTH)
      )
    )
  }
}