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
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.commands.Micellaneous.Delay;

public class FiveBallAuton extends SequentialCommandGroup {
	public FiveBallAuton() {
        addCommands(
            new IntakeDeploy(),
            parallel(
                new DriveUntilFull(),
                //new BaseLinearMovement(2000, 2000),
                new AutonCollecting()
            ),
            new TurnWithGyro(179, 0.5, true),
            parallel(
                new PositionWithLimelight(),
                new FeedShot()
            )
        );
    }
}