package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Camera.SwitchPipelineToTargeting;
import frc.robot.commands.Tilter.MoveTilterToIdealAngle;

public class PositionWithLimelight extends SequentialCommandGroup {
  public PositionWithLimelight() {
    addCommands(
      new SwitchPipelineToTargeting(),
      parallel (
        //new MoveBaseToTarget(),
        //new MoveTilterToIdealAngle()
      )
    );
  }
}