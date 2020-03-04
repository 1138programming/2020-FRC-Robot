package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveBaseFor extends CommandBase {
    private double leftPWM, rightPWM;
    private long duration;
    private long startTime;

    public MoveBaseFor(double leftPWM, double rightPWM, long duration) {
        this.leftPWM = leftPWM;
        this.rightPWM = rightPWM;
        this.duration = duration;

        addRequirements(Robot.base);
    }

    public MoveBaseFor(long duration) {
        this(KBasePWM, KBasePWM, duration);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.base.move(leftPWM, rightPWM);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.base.move(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (duration != 0) && (System.currentTimeMillis() - startTime) > duration;
    }
}