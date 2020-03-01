package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.enums.StorageStage;

public class FeedShot extends SequentialCommandGroup {
	public FeedShot() {
        addCommands(
            parallel(
                // new SpinUpFromTable(),
                sequence(
                    new MoveIndexerFor(800),
                    parallel(
                        new MoveIndexerFor(0),
                        new MoveStorageFor(1, StorageStage.BOTH, 0)
                    )
                )
            )
        );
	}
}