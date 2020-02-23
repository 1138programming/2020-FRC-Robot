package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.MoveIntakeFor;
import frc.robot.commands.Storage.StorageCollecting;

public class Collecting extends SequentialCommandGroup {
	public Collecting() {
        addCommands(
            parallel(
                new MoveIntakeFor(0),
                new StorageCollecting()
            )
        );
	}
}