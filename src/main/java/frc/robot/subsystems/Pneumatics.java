package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics extends SubsystemBase {
  private final Compressor pCompressor;

	public Pneumatics() {
		pCompressor = new Compressor(0);
	}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setCompressor(boolean on){ 
    
  }

  public boolean getCompressor(){
    return true;
  }
}
