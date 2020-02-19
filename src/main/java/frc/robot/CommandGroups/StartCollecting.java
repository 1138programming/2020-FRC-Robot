package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.StorageOut;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.enums.StorageStage;

public class StartCollecting extends SequentialCommandGroup {
	public StartCollecting() {
        addCommands(
            new IntakeDeploy(),
            parallel(
                new IntakeIn(),
                new StorageIn(StorageStage.STAGE1)
            )
        );
	}
}