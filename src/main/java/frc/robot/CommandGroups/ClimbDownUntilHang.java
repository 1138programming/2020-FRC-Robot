package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Climb.ClimbDown;

public class ClimbDownUntilHang extends SequentialCommandGroup {
	public ClimbDownUntilHang() {
        addCommands(
            new ClimbDown()
        );
	}
}