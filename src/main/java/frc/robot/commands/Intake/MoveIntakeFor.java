package frc.robot.commands.Intake;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;

public class MoveIntakeFor extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private double PWM;
    private long duration;
    private long startTime;

    public MoveIntakeFor(double PWM, long duration) {
        this.PWM = PWM;
        this.duration = duration;

        addRequirements(Robot.intake);
    }

    public MoveIntakeFor(long duration) {
        this(KIntakePWM, duration);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.intake.move(PWM);
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
