package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics extends SubsystemBase {
  private Compressor pneumaticsCompressor;

	public Pneumatics() {
		pneumaticsCompressor = new Compressor(0);
	}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setCompressor(boolean on){
    if(on == true) {
      pneumaticsCompressor.start();
    }else {
      pneumaticsCompressor.stop();
    }
  }

  public boolean getCompressor(){ 
    return pneumaticsCompressor.enabled();
  }
}
