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
import frc.robot.commands.Base.MoveBaseFor;
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.commands.Micellaneous.Delay;

//USE FULL BATTERY FOR MAX PERFORMANCE//
//USE FULL BATTERY FOR MAX PERFORMANCE//
//USE FULL BATTERY FOR MAX PERFORMANCE//

public class BarrelAuton extends SequentialCommandGroup {
	public BarrelAuton() {
// public class FiveBallAuton extends SequentialCommandGroup {
// 	public FiveBallAuton() {
    //     addCommands(
    //         new IntakeDeploy(),
    //         parallel(
    //             new DriveStraightFor(2000, 0.5),
    //             new AutonCollecting()
    //         ),
    //         new MoveOutEverythingALittleBit(),
    //         new DriveStraightFor(1200, -1),
    //         parallel(
    //             new SpinUpFlywheel(4000, 2000),
    //             sequence(
    //                 new TurnWithGyro(150, 0.8, true),
    //                 parallel(
    //                     new PositionWithLimelight(),
    //                     new AutonFeedShot()
    //                 )
    //             )
    //         )
    //     );
    // }
        addCommands(
            sequence(
                new MoveBaseFor(1, 0.95, 1350),     //Forward --> right
                //first point
                new MoveBaseFor(0.85, 0.3, 1000),     //Right roll
                new MoveBaseFor(1, 1, 350),     //Forward a bit
                new MoveBaseFor(0.8, 0.4, 1000), //Right roll
                new MoveBaseFor(1, 1, 150),       //Forward a bit
                new MoveBaseFor(0.8, 0.3, 565), //Right roll

                new MoveBaseFor(0.85, 1, 1000),     //Forward --> left
                //second point
                new MoveBaseFor(0.35, 0.85, 800),     //Left roll
                new MoveBaseFor(1, 1, 320),       //Forward a bit
                new MoveBaseFor(0.35, 0.8, 1000),     //Left roll
                new MoveBaseFor(1, 1, 250),       //Forward a bit
                new MoveBaseFor(0.35, 0.8, 420),   //Left roll
                
                new MoveBaseFor(1, 0.9, 910),
                //third point
                new MoveBaseFor(0.35, 0.8, 100),    //Left roll
                new MoveBaseFor(1, 1, 100),         //Forward a bit
                new MoveBaseFor(0.35, 0.8, 650),    //Left roll
                new MoveBaseFor(1, 1, 220),       //Forward a bit
                new MoveBaseFor(0.35, 0.8, 820),     //Left roll
                //RTB
                new MoveBaseFor(1, 1, 2500)     //RTB
                

            )
        );
    }
}