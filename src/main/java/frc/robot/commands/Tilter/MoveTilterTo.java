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

public class MoveTilterTo extends CommandBase {
  double setpoint;

  /**
   * Creates a new MoveTilterTo.
   */
  public MoveTilterTo(double setpoint) {
    addRequirements(Robot.tilter);

    this.setpoint = setpoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.tilter.reset();
    Robot.tilter.setSetpoint(SmartDashboard.getNumber("Tilter Target Linkage Angle", 0.0));
    Robot.tilter.setConstants(
      SmartDashboard.getNumber("Tilter P", 0.0),
      SmartDashboard.getNumber("Tilter I", 0.0),
      SmartDashboard.getNumber("Tilter D", 0.0)
    );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Robot.tilter.move(0);//not sure how do move motor to a certain angle
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
    //return false;
  }
}
