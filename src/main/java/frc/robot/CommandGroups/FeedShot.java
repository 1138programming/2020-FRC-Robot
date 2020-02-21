package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Flywheel.StopFlywheel;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Indexer.IndexOut;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.StorageOut;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.commands.Storage.DisengageStage2;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.enums.StorageStage;

public class FeedShot extends SequentialCommandGroup {
	public FeedShot() {
        addCommands(
            parallel(
                new MoveTilterTo(1) // TODO: Change Value
            ),
            parallel(
                new EngageStage2(),
                new StorageIn(StorageStage.BOTH),
                new IndexIn()
            )
        );
	}
}