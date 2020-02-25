package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.enums.StorageStage;

public class FeedShot extends SequentialCommandGroup {
	public FeedShot() {
        addCommands(
            new MoveIndexerFor(500),
            parallel(
                new MoveIndexerFor(0),
                new MoveStorageFor(StorageStage.BOTH, 0)
            )
        );
	}
}