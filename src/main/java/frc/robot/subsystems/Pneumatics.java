/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;


public class Pneumatics extends SubsystemBase {

  private Compressor pCompressor;

	public void PneumaticsSubsystem() {

		pCompressor = new Compressor(0);
	}

	public void initDefaultCommand()
	{
		pCompressor.start();
	}
  public void setCompressor(boolean on){ 
    
  }
  public boolean getCompressor(){
    return true;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
