package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Storage.EngageStage2;
import frc.robot.commands.Storage.DisengageStage2;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Micellaneous.Delay;

public class MoveBallIn extends SequentialCommandGroup {
	public MoveBallIn() {
        addCommands(
            new MoveStorageFor(StorageStage.BOTH, 500)
        ); //time for ball to travel into stage 2
	}
}