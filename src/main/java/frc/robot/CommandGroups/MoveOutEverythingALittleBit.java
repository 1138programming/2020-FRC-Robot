package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Indexer.MoveIndexerFor;


public class MoveOutEverythingALittleBit extends SequentialCommandGroup {
  public MoveOutEverythingALittleBit() {
    addCommands(
        // parallel(
        //   new MoveStorageFor(-0.5, StorageStage.BOTH, 50),
        //   new MoveIndexerFor(-0.5, 50)
        // ),
        parallel(
          new MoveStorageFor(-0.5, StorageStage.STAGE2, 20),
          new MoveIndexerFor(-0.5, 200)
        )
    );
  }
}