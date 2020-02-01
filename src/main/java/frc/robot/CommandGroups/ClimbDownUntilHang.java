package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;

import frc.robot.commands.Climb.ClimbDown;
import frc.robot.enums.StorageStage;

public class ClimbDownUntilHang extends SequentialCommandGroup {
	public ClimbDownUntilHang() {
        addCommands(
            new ClimbDown()
        );
	}
}