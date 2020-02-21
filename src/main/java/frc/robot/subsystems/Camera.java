package frc.robot.subsystems;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.Tilter;
import frc.robot.Robot;
import java.lang.*;

public class Camera extends SubsystemBase {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tv = table.getEntry("tv"); // visible
  NetworkTableEntry tx = table.getEntry("tx"); // x-offset from target
  NetworkTableEntry ty = table.getEntry("ty"); // y-offset from target
  NetworkTableEntry ta = table.getEntry("ta"); // area covered by target area
  NetworkTableEntry ledMode = table.getEntry("ledMode"); 
  NetworkTableEntry camMode = table.getEntry("camMode");
  NetworkTableEntry pipeline = table.getEntry("pipeline"); // pipeline index
  NetworkTableEntry snapshot = table.getEntry("snapshot"); // Takes two snapshots per second when set to 1
  public static double i, x, y, area;

  /**
   * @brief This is the Camera
   */

  public Camera() {
  }

  @Override
  public void periodic() {
    i = tv.getDouble(0.0); 
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    //SmartDashboard.putNumber("IsLimelightTarget", i);
    //SmartDashboard.putNumber("LimelightX", x);
    //SmartDashboard.putNumber("LimelightY", y);
    //SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putNumber("Distance", getDistance());
  }

  /**
   * @brief Returns whether a valid target is in view of the Limelight
   * 
   * @return  Whether the target is in view or not
   */
  public boolean targetVisible() {
    if (i == 1.0) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * @brief Returns the X offset in degrees from the crosshair to the target
   * 
   * @return  Negative or positive degree offset
   */
  public double getOffsetX() {
    return x;
  }

  /**
   * @brief Returns the Y offset in degrees from the crosshair to the target
   * 
   * @return  Negative or positive degree offset
   */
  public double getOffsetY() {
    return y;
  }

  /**
   * @breif Returns the percentage of the image the target area takes up
   * 
   * @return  Percentage between 0 and 1
   */
  public double getAreaOfTarget() {
    return area;
  }

  /**
   * @breif Sets LED mode of the Limelight
   * 
   * @param led 0 = pipeline's, 1 = off, 2 = blink, 3 = on
   */
  public void setLEDMode(double led) { 
    ledMode.setNumber(led);
  }

  /**
   * @brief Sets Camera mode of the Limelight
   * 
   * @param cam 0 = vision processing, 1 = driver camera (exposure increased, no processing)
   */
  public void setCamMode(double cam) {
    camMode.setNumber(cam);
  }

  /**
   * @breif Sets pipeline index of the Limelight, 0-9
   * 
   * @param pipe  Index of pipeline between 0 and 9
   */
  public void setPipeline(double pipe) {
    pipeline.setNumber(pipe);
  }
  
  /**
   * Returns the distance the camera is from the target
   * 
   * @return Distance in feet(ft.)
   */
  public double getDistance() {
    double h1 = Robot.tilter.getLimelightHeight()/12;
    double h2 = 91/12;
    double a1 = Math.toRadians(Robot.tilter.getTilterAngle());
    double a2 = Math.toRadians(y);

    double tHeight = h2 - h1;
    double tangent = Math.tan(a1 + a2);

    double distance = tHeight / tangent;
    return distance;
  }
}
