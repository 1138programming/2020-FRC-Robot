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
    // SmartDashboard.putNumber("Shooting Table Top Vel", flywheelState.getTopVel());
    // SmartDashboard.putNumber("Shooting Table Bottom Vel", flywheelState.getBottomVel());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // flywheelState.setTopVel(SmartDashboard.getNumber("Shooting Table Top Vel", flywheelState.getTopVel()));
    // flywheelState.setBottomVel(SmartDashboard.getNumber("Shooting Table Bottom Vel", flywheelState.getBottomVel()));

    Robot.flywheel.setTopConstants(
      SmartDashboard.getNumber("Flywheel Top P", 0.0),
      SmartDashboard.getNumber("Flywheel Top I", 0.0),
      SmartDashboard.getNumber("Flywheel Top D", 0.0),
      SmartDashboard.getNumber("Flywheel Top F", 0.0)
    );
    //Robot.flywheel.setTopConstants(.00025, .00047, 0, .000178);
    //Robot.flywheel.setBottomConstants(.000208, .00047, 0, .000178);

    Robot.flywheel.setBottomConstants(
      SmartDashboard.getNumber("Flywheel Bottom P", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom I", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom D", 0.0),
      SmartDashboard.getNumber("Flywheel Bottom F", 0.0)
    );

    Robot.flywheel.setSetpoints(flywheelState.getTopVel(), flywheelState.getBottomVel());
    Robot.flywheel.calculate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.flywheel.moveVoltage(0, 0);
    Robot.flywheel.move(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
