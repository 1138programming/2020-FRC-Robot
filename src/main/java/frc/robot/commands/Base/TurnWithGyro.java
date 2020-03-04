package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class TurnWithGyro extends CommandBase {
    private double targetAngle;

    public TurnWithGyro(double targetAngle) {
        this.targetAngle = targetAngle;

        addRequirements(Robot.base);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.base.setRotationSetpoint(targetAngle);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.base.calculateRotation();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Robot.base.atRotationSetpoint();
    }
}
