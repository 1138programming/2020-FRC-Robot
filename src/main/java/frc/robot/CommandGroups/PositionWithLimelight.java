package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Micellaneous.Delay;
import frc.robot.commands.Base.MoveBaseToCrosshair;
import frc.robot.commands.Camera.SwitchPipelineToTargeting;
import frc.robot.commands.Tilter.MoveTilterToIdealAngle;
import frc.robot.commands.Tilter.MoveTilterFromTable;

public class PositionWithLimelight extends SequentialCommandGroup {
  public PositionWithLimelight() {
    addCommands(
      new SwitchPipelineToTargeting(),
      new Delay(500),
      parallel (
        new SpinUpFromTable(),
        sequence (
          new MoveBaseToCrosshair(),
          // new MoveTilterFromTable(), 
          new FeedShot()
        )
      )
    );
  }
}