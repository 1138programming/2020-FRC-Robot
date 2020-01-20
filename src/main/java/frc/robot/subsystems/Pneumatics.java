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
