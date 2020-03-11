import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class NewPID extends PIDCommand {
    public NewPID(PIDController pidControl, DoubleSupplier supply, double setpoint, DoubleConsumer consume) {
        super(pidControl, supply, setpoint, consume);
    }

    public NewPID(PIDController pidControl, DoubleSupplier supply, DoubleSupplier setpoint, DoubleConsumer consume) {
        super(pidControl, supply, setpoint, consume);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
}