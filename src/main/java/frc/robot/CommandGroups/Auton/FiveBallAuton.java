package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.CommandGroups.AutonCollecting;
import frc.robot.CommandGroups.Collecting;
import frc.robot.CommandGroups.AutonFeedShot;
import frc.robot.CommandGroups.MoveOutEverythingALittleBit;
import frc.robot.CommandGroups.PositionWithLimelight;
import frc.robot.commands.Base.BaseLinearMovement;
import frc.robot.commands.Base.DriveUntilFull;
import frc.robot.commands.Base.TurnWithGyro;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Base.DriveStraightFor;
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.commands.Micellaneous.Delay;

public class FiveBallAuton extends SequentialCommandGroup {
	public FiveBallAuton() {
        addCommands(
            new IntakeDeploy(),
            parallel(
                new DriveStraightFor(2000, 0.5),
                new AutonCollecting()
            ),
            new MoveOutEverythingALittleBit(),
            new DriveStraightFor(1200, -1),
            parallel(
                new SpinUpFlywheel(4000, 2000),
                sequence(
                    new TurnWithGyro(150, 0.8, true),
                    parallel(
                        new PositionWithLimelight(),
                        new AutonFeedShot()
                    )
                )
            )
        );
    }
}