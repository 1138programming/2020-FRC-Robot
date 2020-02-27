package frc.robot;

/**
 * @brief Wrapper class to hold flywheel parameters for shooting balls
 */
public class FlywheelState {
    private double angle;
    private double topVel;
    private double bottomVel;

    public FlywheelState(double angle, double topVel, double bottomVel) {
        this.angle = angle;
        this.topVel = topVel;
        this.bottomVel = bottomVel;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getTopVel() {
        return topVel;
    }

    public void setTopVel(double topVel) {
        this.topVel = topVel;
    }

    public double getBottomVel() {
        return bottomVel;
    }

    public void setBottomVel(double bottomVel) {
        this.bottomVel = bottomVel;
    }
}