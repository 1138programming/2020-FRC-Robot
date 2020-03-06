package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Micellaneous.Delay;


public class MoveOutEverythingALittleBit extends SequentialCommandGroup {
  public MoveOutEverythingALittleBit() {
    addCommands(
        parallel(
          new MoveStorageFor(-0.5, StorageStage.STAGE2, 400),
          new MoveIndexerFor(-0.5, 400)
        ),
        new Delay(100),
        parallel(
          new MoveIndexerFor(-0.5, 200)
        )
    );
  }
}