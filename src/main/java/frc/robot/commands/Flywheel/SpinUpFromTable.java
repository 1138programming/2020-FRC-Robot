package frc.robot.commands.Flywheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.FlywheelState;

public class SpinUpFromTable extends CommandBase {
  private FlywheelState flywheelState;

  public SpinUpFromTable() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flywheelState = Robot.flywheel.readShootingTable(Robot.camera.getDistance());
    SmartDashboard.putNumber("Shooting Table Top Vel", flywheelState.getTopVel());
    SmartDashboard.putNumber("Shooting Table Bottom Vel", flywheelState.getBottomVel());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    flywheelState.setTopVel(SmartDashboard.getNumber("Shooting Table Top Vel", flywheelState.getTopVel()));
    flywheelState.setBottomVel(SmartDashboard.getNumber("Shooting Table Bottom Vel", flywheelState.getBottomVel()));
    Robot.flywheel.setSetpoints(flywheelState.getTopVel(), flywheelState.getBottomVel());
    Robot.flywheel.calculate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
