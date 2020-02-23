package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.RobotState.StartShooting;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Tilter.MoveTilterToIdealAngle;

public class LimelightPosition extends SequentialCommandGroup {
  public LimelightPosition() {
    addCommands(
      //new StartShooting(),
      parallel (
        new MoveBaseToTarget(),
        new MoveTilterToIdealAngle()
      )
    );
  }
}