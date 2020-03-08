package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Camera.SwitchPipelineToTargeting;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Micellaneous.Delay;

public class AutonFeedShot extends SequentialCommandGroup {
	public AutonFeedShot() {
        addCommands(
                new MoveIndexerFor(3000),
                parallel(
                    new MoveIndexerFor(0),
                    new MoveStorageFor(.7, StorageStage.BOTH, 0)
                )
        );
	}
}