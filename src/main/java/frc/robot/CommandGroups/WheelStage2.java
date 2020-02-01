package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import frc.robot.Robot;
import frc.robot.commands.Climb.ClimbDown;
import frc.robot.commands.Climb.ClimbUp;
import frc.robot.commands.Wheel.Spin;

public class WheelStage2 extends SequentialCommandGroup {
  public WheelStage2() {
    addCommands(
        new ClimbUp(),
        new Spin(),
        new ClimbDown()
    );
  }
}