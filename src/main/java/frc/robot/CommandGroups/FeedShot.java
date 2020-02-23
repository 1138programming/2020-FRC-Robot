package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Storage.EngageStage2;
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