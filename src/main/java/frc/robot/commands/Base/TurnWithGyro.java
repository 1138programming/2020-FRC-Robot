package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class TurnWithGyro extends CommandBase {
    private double targetAngle;
    private boolean relative;
    private double maxSpeed;

    public TurnWithGyro(double targetAngle, double maxSpeed, boolean relative) {
        this.targetAngle = targetAngle;
        this.relative = relative;
        this.maxSpeed = maxSpeed;

        addRequirements(Robot.base);
    }

    public TurnWithGyro(double targetAngle, double maxSpeed) {
        this(targetAngle, maxSpeed, false);
    }

    public TurnWithGyro(double targetAngle) {
        this(targetAngle, 1, false);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (relative) {
            Robot.base.setRotationSetpointRelative(targetAngle);
        } else {
            Robot.base.setRotationSetpoint(targetAngle);
        }
        Robot.base.setRotationMaxSpeed(maxSpeed);
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
