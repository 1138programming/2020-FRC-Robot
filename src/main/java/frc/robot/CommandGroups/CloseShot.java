package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Flywheel.StopFlywheel;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Indexer.IndexOut;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Storage.MoveIn;
import frc.robot.commands.Storage.MoveOut;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.commands.Storage.DisengageStage2;
import frc.robot.enums.StorageStage;

public class CloseShot extends SequentialCommandGroup {
	public CloseShot() {
        addCommands(
            new SpinUpFlywheel(),
            //Limelight commands missing
                parallel(
                    new MoveIn(StorageStage.STAGE1),
                    new EngageStage2(),
                    new IndexIn()
                )
        );
        /*addSequential(Command SpinUpFlywheel());
        //Limelight commands missing
        addParallel(Command IndexIn());
        addParallel(Command EngageStage2());
        addSequential(Command MoveIn());*/
	}
}