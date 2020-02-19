package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Base;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;

public class BaseLinearMovement extends CommandBase {
    double leftTarget;
    double rightTarget;
    double maxVel;
    double maxAccel;
    boolean absolute;

    public BaseLinearMovement(double leftTarget, double rightTarget, double maxVel, double maxAccel, boolean absolute) {
        addRequirements(Robot.base);

        this.leftTarget = leftTarget;
        this.rightTarget = rightTarget;
        this.maxVel = maxVel;
        this.maxAccel = maxAccel;
        this.absolute = absolute;
    }

    public BaseLinearMovement(double leftTarget, double rightTarget, double maxVel, double maxAccel) {
        this(leftTarget, rightTarget, maxVel, maxAccel, false);
    }

    public BaseLinearMovement(double leftTarget, double rightTarget, boolean absolute) {
        this(leftTarget, rightTarget, KBaseMaxVel, KBaseMaxAccel, absolute);
    }

    public BaseLinearMovement(double leftTarget, double rightTarget) {
        this(leftTarget, rightTarget, KBaseMaxVel, KBaseMaxAccel);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (absolute) {
            Robot.base.setTarget(leftTarget, rightTarget);
        } else {
            Robot.base.setTargetRelative(leftTarget, rightTarget);
        }

        Robot.base.setMaxVel(maxVel);
        Robot.base.setMaxAccel(maxAccel);
        Robot.base.initProfile();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.base.calculateProfile();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.base.zeroEncoders();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Robot.base.atProfileTarget();
    }
}
