package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.CommandGroups.PositionWithLimelight;
import frc.robot.commands.Base.MoveBaseFor;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Micellaneous.Delay;
import frc.robot.CommandGroups.FeedShot;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.enums.*;

public class AutonShootFromLine extends SequentialCommandGroup {
	public AutonShootFromLine() {
        addCommands(
            parallel(
                new SpinUpFromTable(),
                sequence(
                    new Delay(1000),
                    new MoveIndexerFor(0.5, 100),
                    parallel(
                        new MoveStorageFor(0.5, StorageStage.BOTH, 20),
                        new MoveIndexerFor(0.5, 200)
                    )
                ),
                sequence(
                    new Delay(14000),
                    new MoveBaseFor(-0.5, -0.5, 0)
                )
            )
        );
    }
    
}