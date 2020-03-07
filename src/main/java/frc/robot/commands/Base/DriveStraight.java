package frc.robot.commands.Base;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveStraight extends CommandBase {
    private double setpoint;
    private double maxPWM;

    public DriveStraight(double setpoint, double maxPWM) {
        this.setpoint = setpoint;
        this.maxPWM = maxPWM;

        addRequirements(Robot.base);
    }

    public DriveStraight(double setpoint) {
        this(setpoint, 1);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.base.resetStraightPID();
        Robot.base.setStraightPIDSetpoint(setpoint);
        Robot.base.setStraightPIDMaxPWM(maxPWM);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.base.straightPIDCalculate();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Robot.base.atStraightPIDSetpoint();
    }
}
