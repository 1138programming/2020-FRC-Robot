package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FeedShot extends SequentialCommandGroup {
	public FeedShot() {
        addCommands(
            new Shoot()
        );
	}
}