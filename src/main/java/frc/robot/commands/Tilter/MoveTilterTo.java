/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Tilter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Tilter;
import static frc.robot.Constants.*;

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
    // Robot.tilter.setSetpoint(setpoint);
    Robot.tilter.setIdealSetpoint(13.75, Robot.camera.getDistance(), 2.5);
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
  }
}