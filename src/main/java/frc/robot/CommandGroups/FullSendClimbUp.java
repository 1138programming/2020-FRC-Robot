package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Climb.ClimbUp;


public class FullSendClimbUp extends SequentialCommandGroup {
  public FullSendClimbUp() {
    addCommands(
      new ClimbUp()
    );
  }
}