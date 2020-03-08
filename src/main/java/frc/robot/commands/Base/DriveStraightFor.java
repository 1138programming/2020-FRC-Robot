package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveStraightFor extends CommandBase {
    private long duration;
    private long startTime;
    private double maxPWM;

    public DriveStraightFor(long duration, double maxPWM) {
        this.duration = duration;
        this.maxPWM = maxPWM;

        addRequirements(Robot.base);
    }

    public DriveStraightFor(long duration) {
        this(duration, 1);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.base.resetStraightener();
        startTime = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.base.moveWithStraightener(maxPWM);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - startTime) > duration;
    }
}
