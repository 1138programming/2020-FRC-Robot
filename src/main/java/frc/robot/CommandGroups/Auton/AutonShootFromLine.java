package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.CommandGroups.LimelightPosition;
import frc.robot.CommandGroups.FeedShot;

public class AutonShootFromLine extends SequentialCommandGroup {
	public AutonShootFromLine() {
        addCommands(
            new LimelightPosition(),
            new FeedShot()
        );
    }
    
}