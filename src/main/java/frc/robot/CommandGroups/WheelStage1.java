package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import frc.robot.Robot;
import frc.robot.commands.Climb.ClimbDown;
import frc.robot.commands.Climb.ClimbUp;
import frc.robot.commands.Wheel.GoToColor;

public class WheelStage1 extends SequentialCommandGroup {
  public WheelStage1() {
    addCommands(
        new ClimbDown(),
        new ClimbUp(),
        new GoToColor()
    );
    /*addSequentialCommand(new ClimbUp());
    addSequentialCommand(new GoToColor());
    addSequentialCommand(new ClimbDown());*/
  }
}