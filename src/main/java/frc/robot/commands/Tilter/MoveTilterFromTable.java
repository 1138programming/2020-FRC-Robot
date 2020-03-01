/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Tilter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.FlywheelState;

public class MoveTilterFromTable extends CommandBase {
  private FlywheelState flywheelState;

  /**
   * Creates a new MoveTilterFromTable.
   */
  public MoveTilterFromTable() {
    addRequirements(Robot.tilter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flywheelState = Robot.flywheel.readShootingTable(Robot.camera.getDistance());
    //SmartDashboard.putNumber("Shooting Table Angle", flywheelState.getAngle());
    Robot.tilter.reset();
    Robot.tilter.setSetpoint(flywheelState.getAngle());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    flywheelState.setAngle(SmartDashboard.getNumber("Shooting Table Angle", flywheelState.getAngle()));
    Robot.tilter.setTilterSetpoint(flywheelState.getAngle());
    Robot.tilter.calculate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Robot.tilter.atSetpoint();
  }
}
