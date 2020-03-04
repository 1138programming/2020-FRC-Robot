package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.CommandGroups.AutonCollecting;
import frc.robot.CommandGroups.Collecting;
import frc.robot.CommandGroups.FeedShot;
import frc.robot.CommandGroups.PositionWithLimelight;
import frc.robot.commands.Base.BaseLinearMovement;
import frc.robot.commands.Base.DriveUntilFull;
import frc.robot.commands.Base.TurnWithGyro;

public class FiveBallAuton extends SequentialCommandGroup {
	public FiveBallAuton() {
        addCommands(
            parallel(
                new DriveUntilFull(),
                new AutonCollecting()
            ),
            new TurnWithGyro(180),
            new PositionWithLimelight(),
            new FeedShot()
        );
    }
}