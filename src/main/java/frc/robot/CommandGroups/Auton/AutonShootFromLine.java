package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.CommandGroups.PositionWithLimelight;
import frc.robot.commands.Base.MoveBaseFor;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.CommandGroups.FeedShot;
import frc.robot.commands.Tilter.MoveTilterTo;

public class AutonShootFromLine extends SequentialCommandGroup {
	public AutonShootFromLine() {
        addCommands(
            parallel(
                new SpinUpFlywheel(),
                new MoveBaseFor(1, 1, 500),
                new MoveTilterTo(61)
            ),
            new FeedShot()
        );
    }
    
}