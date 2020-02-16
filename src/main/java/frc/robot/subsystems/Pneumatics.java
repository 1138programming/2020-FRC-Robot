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

  /**
   * Sets the copmressor to either on or off
   * 
   * @param on  True means turn on the compressor, fake means turn it off
   */
  public void setCompressor(boolean on) {
    if(on == true) {
      pneumaticsCompressor.start();
    }else {
      pneumaticsCompressor.stop();
    }
  }

  /**
   * Gets the compressor's state
   * 
   * @return  True means on, false means off
   */
  public boolean getCompressor() {
    return pneumaticsCompressor.enabled();
  }

  public double getCurrent() {
    return pneumaticsCompressor.getCompressorCurrent();
  }
}
