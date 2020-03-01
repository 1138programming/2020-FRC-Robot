package frc.robot.subsystems;

import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
//import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.controller.PIDController;
import frc.robot.enums.IntegralType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DigitalInput;

public class Tilter extends SubsystemBase {
    private final TalonSRX tilterMotor;

    // private final CANEncoder tilterEncoder;
    private final PIDController tilterPID;

    private static final double KTicksPerRev = 4096;
    private static final double KGearRatio = 1; // It was supposed to be 300 but Humzah is bad
    private static final double KDegreesPerTick = KDegreesPerRevolution / (KTicksPerRev * KGearRatio);
    private static final double KDegreeOffset = 76; // Flywheel offset is 36

    private final DigitalInput tilterBottomLimit;

    private final PIDController yOffController;

    // Constants for converting between linkage and tilter angles
    private final double KLinkageALength = 4;
    private final double KLinkageBLength = 4.5;
    private final double KLinkageCLength = 4.757;
    private final double KLinkageDX = 7.75;
    private final double KLinkageDY = 2;
    private final double KParallelCorrection = 0.6; // Should be 3.013
    private final double KLimelightMountOffset = 10; // Offset from flywheel
    private final double KTargetHeight = 8.1875; // Height of the goal off the ground in feet

    private double PWM;

    /**
     * @brief This is the Tilter
     */
    public Tilter() {
        tilterMotor = new TalonSRX(KTilterTalon);
        tilterMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
        //tilterMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.PulseWidthEncodedPosition, 0, 0);

        tilterBottomLimit = new DigitalInput(KTilterBottomLimit);

        tilterPID = new PIDController(0.0045, 0.002, 0);

        tilterPID.setInputRange(-100, 1000);
        tilterPID.setOutputRange(-1, 1);
        tilterPID.setOutputDeadband(0.15, 0.01);
        tilterPID.setTolerance(0.5, 1); // possible position and velocity tolerance values
        tilterPID.configIntegral(IntegralType.DEFAULT, true);
        tilterPID.setIntegralZoneRange(5);

        tilterPID.reset();

        yOffController = new PIDController(0.023, 0, 0, 0, 0.02);
        yOffController.setInputRange(-28, 28);
        yOffController.setOutputRange(-1, 1);
        yOffController.setTolerance(1, 0.001);

        yOffController.reset();
        yOffController.setSetpoint(0);

        SmartDashboard.putNumber("Tilter Target Linkage Angle", 0.0);
        SmartDashboard.putNumber("Tilter P", tilterPID.getP());
        SmartDashboard.putNumber("Tilter I", tilterPID.getI());
        SmartDashboard.putNumber("Tilter D", tilterPID.getD());
        SmartDashboard.putNumber("Ball Initial Vel", 45.11);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Tilter Encoder", getEncoder());
        SmartDashboard.putNumber("Tilter Flywheel Angle", getTilterAngle());
        SmartDashboard.putNumber("Tilter Linkage Angle", getLinkageAngle());
        SmartDashboard.putNumber("Flywheel to Linkage", toLinkageAngle(getTilterAngle())); // Should give the same result as getLinkageAngle
        SmartDashboard.putNumber("Tilter PWM", PWM);
        SmartDashboard.putBoolean("Tilter Limit", getBottomLimit());
        SmartDashboard.putNumber("Limelight Height", getLimelightHeight());
        SmartDashboard.putNumber("Tilter Ideal Angle", idealTilterAngle(45.11));
        SmartDashboard.putNumber("Tilter Ideal Linkage Angle", toLinkageAngle(idealTilterAngle(45.11)));
        SmartDashboard.putNumber("Limelight Angle", getLimelightAngle());
    }

    /**
     * @brief Moves the tilter by a given speed
     * 
     * @param PWM   The PWM value to move the tilter at
     */
    public void move(double PWM) {
        this.PWM = PWM;
        tilterMotor.set(ControlMode.PercentOutput, enforceLimits(PWM));
    }

    /**
     * @brief Takes a PWM value and returns another that takes limits into account
     * 
     * @param PWM   The PWM value to modify
     * @return      The modified PWM value
     */
    public double enforceLimits(double PWM) {
        if (getBottomLimit()) {
            zeroEncoder();
            if (PWM < 0) {
                PWM = 0;
            }
        } else if (getEncoder() >= 340) {
            if (PWM > 0) {
                PWM = 0;
            }
        }
        return PWM;
    }

    /**
     * @brief Reset encoder value to 0
     */
    public void zeroEncoder() { // The bounds right now are 0 to 900
        tilterMotor.setSelectedSensorPosition(0);
    }

    /**
     * @brief Get the raw encoder value
     * 
     * @return  The encoder value
     */
    public double getEncoder() {
        return tilterMotor.getSelectedSensorPosition();
    }

    /**
     * @brief Gets the angle of the linkage being directly controlled by the tilter motor
     * 
     * @return The angle of the linkage
     */
    private double getLinkageAngle() {
        return tilterMotor.getSelectedSensorPosition() * KDegreesPerTick + KDegreeOffset;
    }

    /**
     * @brief Computes the angle that the tilter is tilting the flywheel at above the horizontal
     * 
     * Bounds on the tilter angle in degrees: [32, 61.5]
     * 
     * @return The angle
     */
    public double getTilterAngle() {
        return toTilterAngle(getLinkageAngle());
    }

    /**
     * @brief Sets the setpoint for the tiler PID
     * 
     * @param setpoint The target linkage angle
     */
    public void setSetpoint(double setpoint) {
        tilterPID.setSetpoint(setpoint);
    }

    /**
     * @brief Sets the setpoint for the tilter PID
     * 
     * @param setpoint The target flywheel angle
     */
    public void setTilterSetpoint(double setpoint) {
        double linkageAngle = toLinkageAngle(setpoint);
        SmartDashboard.putNumber("Table Angle Conversion", linkageAngle);
        setSetpoint(linkageAngle);
    }

    /**
     * @param vel   Output velocity of the ball in feet per second
     * @param xDist Horizonatal distance to the goal in feet
     */
    public void setIdealSetpoint(double vel) {
        setTilterSetpoint(idealTilterAngle(vel));
    }

    /**
     * @brief Gets the setpoint of the tilter PID
     * 
     * @return The setpoint
     */
    public double getSetpoint() {
        return tilterPID.getSetpoint();
    }

    /**
     * @brief Calculates the output of the tilterPID and moves the tilterMotor with it
     */
    public void calculate() {
        move(tilterPID.calculate(getLinkageAngle()));
        //move(tilterPID.calculate(getEncoderValue()));
    }

    public void calculateYOff() {
        double output = yOffController.calculate(Robot.camera.getOffsetY());
        SmartDashboard.putNumber("yOutput", output);
        move(-output);
    }

    public void reset() {
        tilterPID.reset();
    }

    public void resetYOff() {
        yOffController.reset();
        yOffController.setSetpoint(0);
    }

    /**
     * @brief
     */
    public boolean atSetpoint() {
        return tilterPID.atSetpoint();
    }

    public void setConstants(double kP, double kI, double kD) {
        tilterPID.setGains(kP, kI, kD);
    }

    public boolean getBottomLimit() {
        return !tilterBottomLimit.get();
    }

    /**
     * @brief Solves for angles on the tilter mechanism
     * 
     * Calculates the remaining angle in a mechanism made up of 4 linkages.
     * Given the lengths of the linkages and an angle, it solves for the
     * angle of the opposite linkage. The linkages are arranged in clockwise
     * order: A, B, C, D. See this graph for more information:
     * https://www.desmos.com/calculator/ehqwghlllg
     * 
     * @param A      Length of linkage A
     * @param B      Length of linkage B
     * @param C      Length of linkage C
     * @param D_x    X component of linkage D. Right is positive
     * @param D_y    Y component of linkage D. Up is positive
     * @param thetaA Angle of linkage A above the horizontal
     * 
     * @return Angle of linkage C above the horizontal
     */
    private double solveForAngle(double A, double B, double C, double D_x, double D_y, double thetaA) {
        double A_x = A * Math.cos(thetaA);
        double A_y = A * Math.sin(thetaA);
        double K_1 = D_x - A_x;
        double K_2 = D_y - A_y;
        double K_3 = 0.5 * ((B * B) - (K_1 * K_1) - (K_2 * K_2) - (C * C));
        double K_4 = K_3 / K_2;
        double K_5 = -K_1 / K_2;
        double K_6 = 1 + (K_5 * K_5);
        double K_7 = 2 * K_4 * K_5;
        double K_8 = (K_4 * K_4) - (C * C);
        double C_x = (-K_7 + Math.sqrt((K_7 * K_7) - (4 * K_6 * K_8))) / (2 * K_6);
        return Math.acos(C_x / C);
    }

    /**
     * @brief Converts an angle of the tilter to an angle of the tilter's linkage
     * 
     * @param flywheelAngle Angle of the tilter
     * @return Angle of the tilter linkage
     */
    private double toLinkageAngle(double tilterAngle) {
        double thetaC = solveForAngle(KLinkageCLength, KLinkageBLength, KLinkageALength, KLinkageDX, -KLinkageDY, Math.toRadians(tilterAngle - KParallelCorrection));
        thetaC = thetaC * 180 / Math.PI;
        SmartDashboard.putNumber("toLinkageAngle output", thetaC);
        SmartDashboard.putNumber("toLinkageAngle input", tilterAngle);
        return thetaC;
    }

    /**
     * @brief Converts an angle of the tilter's linkage to the angle of the flywheel
     * 
     * @param tilterAngle Angle of the tilter linkage
     * @return Angle of the tilter
     */
    private double toTilterAngle(double linkageAngle) {
        double thetaC = solveForAngle(KLinkageALength, KLinkageBLength, KLinkageCLength, KLinkageDX, KLinkageDY, linkageAngle * Math.PI / 180);
        return (thetaC * 180 / Math.PI) - 90 + KParallelCorrection;
    }

    private double idealTilterAngle(double vel) {
        double xDist = Robot.camera.getDistance();
        double yDist = KTargetHeight - getLimelightHeight();
        double g = 32.2; // Gravity in meters per second squared
        double velSq = vel * vel; // Velocity squared
        double rad = Math.sqrt((velSq * velSq) - (g * ((2 * velSq * yDist) + (g * xDist * xDist)))); // Radical

        double theta1 = Math.toDegrees(Math.atan((velSq + rad) / (g * xDist))); // First possible angle
        double theta2 = Math.toDegrees(Math.atan((velSq - rad) / (g * xDist))); // Second possible angle

        // If one angle is NaN, returns the other angle. Otherwise, returns the lower
        // angle
        if (Double.isNaN(theta1)) {
            return theta2;
        } else if (Double.isNaN(theta2)) {
            return theta1;
        } else {
            return theta1 < theta2 ? theta1 : theta2;
        }
    }

    public double getLimelightHeight() {
        double AngleT = getTilterAngle();
        double AngleLc = 48.21521752;
        double AngleA = 90 - (90 - AngleLc) - (90 - AngleT);
        double RadianA = Math.toRadians(AngleA);
        double Height1 = Math.sin(RadianA) * 7.879;
        double h1 = Height1 + 20.1255;

        return h1 / 12; // Convert to feet
    }

    public double getLimelightAngle() {
        return getTilterAngle() - KLimelightMountOffset;
    }
}
