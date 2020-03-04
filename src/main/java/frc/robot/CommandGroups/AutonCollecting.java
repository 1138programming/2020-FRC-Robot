package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.IntakeUntilFull;
import frc.robot.commands.Intake.MoveIntakeFor;
import frc.robot.commands.Storage.CollectUntilFull;
import frc.robot.commands.Storage.StorageCollecting;

public class AutonCollecting extends SequentialCommandGroup {
	public AutonCollecting() {
        addCommands(
            parallel(
                new IntakeUntilFull(),
                new CollectUntilFull()
            )
        );
	}
}