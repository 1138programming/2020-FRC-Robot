package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import frc.robot.Robot;
import frc.robot.commands.Intake.IntakeRetract;
import frc.robot.commands.Intake.IntakeStop;
import frc.robot.commands.Wheel.WheelStop;

public class WheelStage1 extends SequentialCommandGroup {
  public EndCollecting() {
    addCommands(
      new IntakeRetract(),
      new IntakeStop(),
      new WheelStop() 
    );
    /*addSequentialCommand(new ClimbUp());
    addSequentialCommand(new GoToColor());
    addSequentialCommand(new ClimbDown());*/
  }
}