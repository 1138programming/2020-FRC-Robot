package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.commands.Base.MoveBaseToCrosshair;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.enums.StorageStage;

public class CloseShot extends SequentialCommandGroup {
	public CloseShot() {
        addCommands(
            parallel(
                new SpinUpFlywheel(),
                new MoveBaseToCrosshair(),
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