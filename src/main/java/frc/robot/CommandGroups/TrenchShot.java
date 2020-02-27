package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Base.MoveBaseToCrosshair;
import frc.robot.commands.Tilter.MoveTilterTo;

public class TrenchShot extends SequentialCommandGroup {
  public TrenchShot() {
    addCommands(
      parallel(
        new SpinUpFlywheel(),
        new MoveBaseToCrosshair(),
        new MoveTilterTo(1) // TODO: Change Value
      ),
      parallel(
        new FeedShot()
      )
    );
  }
}