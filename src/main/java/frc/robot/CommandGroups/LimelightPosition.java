package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterToIdealAngle;

public class LimelightPosition extends SequentialCommandGroup {
  public LimelightPosition() {
    addCommands(
      new SpinUpFlywheel(),
      parallel (
        new MoveBaseToTarget(),
        new MoveTilterToIdealAngle()
      )
    );
  }
}