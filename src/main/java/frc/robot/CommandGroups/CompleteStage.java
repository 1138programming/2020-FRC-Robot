package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.Climb.ClimbDown;
import frc.robot.commands.Climb.ClimbUp;
import frc.robot.commands.Wheel.SmartWheel;

public class CompleteStage extends SequentialCommandGroup {
  public CompleteStage() {
    addCommands(
        new ClimbUp(),
        new SmartWheel(),
        new ClimbDown()
    );
  }
}