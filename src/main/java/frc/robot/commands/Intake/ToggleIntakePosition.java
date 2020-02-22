/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Intake;

import frc.robot.Robot;
import frc.robot.subsystems.Base;
import frc.robot.enums.SolenoidState;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleIntakePosition extends CommandBase {
  /**
   * Creates a new StartCollecting.
   */
  public ToggleIntakePosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // If in collecting state, switch to default
    if (Robot.intake.getIntakePosition() == SolenoidState.DEFAULT) {
        Robot.intake.setIntakePosition(SolenoidState.ACTIVE);
    } else { // Otherwise, switch to collecting
      Robot.intake.setIntakePosition(SolenoidState.DEFAULT);
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
    return true;
  }
}
