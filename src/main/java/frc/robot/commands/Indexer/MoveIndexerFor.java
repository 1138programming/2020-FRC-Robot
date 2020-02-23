package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import static frc.robot.Constants.*;

public class MoveIndexerFor extends CommandBase {
    private double PWM;
    private long duration;
    private long startTime;

    public MoveIndexerFor(double PWM, long duration) {
        this.PWM = PWM;
        this.duration = duration;

        addRequirements(Robot.indexer);
    }

    public MoveIndexerFor(long duration) {
        this(KIndexerPWM, duration);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.indexer.move(PWM);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (duration != 0) && (System.currentTimeMillis() - startTime) > duration;
    }
}
