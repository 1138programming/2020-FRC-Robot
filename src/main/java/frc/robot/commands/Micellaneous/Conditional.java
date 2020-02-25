package frc.robot.commands.Micellaneous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.BooleanSupplier;
import static frc.robot.Constants.*;

/**
 * @brief Command that returns conditional on a BooleanSupplier's result
 * 
 * The purpose of this command is to be able to run other commands conditionally using CommandBase.raceWith(CommandBase command).
 * This function ends both commands when either finishes.
 */
public class Conditional extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private BooleanSupplier condition;
    private boolean continuous;
    private boolean conditionResult;

    public Conditional(BooleanSupplier condition, boolean continuous) {
        this.condition = condition;
        this.continuous = continuous;
    }

    public Conditional(BooleanSupplier condition) {
        this(condition, false);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (continuous) {
            conditionResult = false;
        } else {
            conditionResult = condition.getAsBoolean();
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (continuous) {
            return condition.getAsBoolean();
        } else {
            return conditionResult;
        }
    }
}