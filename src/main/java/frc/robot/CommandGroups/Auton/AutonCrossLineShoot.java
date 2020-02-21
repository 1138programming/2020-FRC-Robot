package frc.robot.CommandGroups.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Robot;
import frc.robot.commands.Base.MoveBaseFor;

public class AutonCrossLineShoot extends SequentialCommandGroup {
	public AutonCrossLineShoot() {
        addCommands(
            new MoveBaseFor(-1, -1, 1000),
            new AutonShootFromLine()
        );
    }
}