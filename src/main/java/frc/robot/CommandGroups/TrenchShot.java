package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;
import frc.robot.commands.Flywheel.SpinUpFlywheel;

public class TrenchShot extends CommandGroup {
  public TrenchShot() {
    addSequentialCommand(new SpinUpFlywheel());
  }
}