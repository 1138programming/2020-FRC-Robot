package frc.robot.commands.Wheel;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.enums.ColorLabel;

public class SmartWheel extends CommandBase {
    private GoToColor goToColor;
    private Spin spin;
    private Command wheelCommand;

    public SmartWheel() {
        goToColor = new GoToColor();
        spin = new Spin();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (Robot.wheel.getTargetColor() == ColorLabel.UNKNOWN) {
            wheelCommand = goToColor;
        } else {
            wheelCommand = spin;
        }

        wheelCommand.schedule();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            wheelCommand.cancel();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return wheelCommand.isFinished();
    }
}
