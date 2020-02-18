package frc.robot.subsystems;

import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
//import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DigitalInput;

public class Tilter extends SubsystemBase {
    private final TalonSRX tilterMotor;
    
    //private final CANEncoder tilterEncoder;
    private final PIDController tilterPID;
    
    private static final double KTicksPerRev = 2048;
    private static final double KGearRatio = 1; // It was supposed to be 300 but Humzah is bad
    private static final double KDegreesPerTick = KDegreesPerRevolution / (KTicksPerRev * KGearRatio);
    private static final double KDegreeOffset = 27;

    private final DigitalInput tilterBottomLimit;

    private final PIDController yOffController;

    /**
     * @brief This is the Tilter
     */

    public Tilter() {
        tilterMotor = new TalonSRX(KTilterTalon);
        tilterMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

        tilterBottomLimit = new DigitalInput(KTilterBottomLimit);
        //tilterMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.PulseWidthEncodedPosition, 0, 0);

        //tilterEncoder = new CANEncoder(tilterMotor);
        tilterPID = new PIDController(0.0021, 0, 0.01);

        //tilterPID.enableContinuousInput(0, 900);
        //tilterPID.disableContinuousInput();
        tilterPID.setInputRange(-100, 1000);
        tilterPID.setOutputRange(-1, 1);
        tilterPID.setOutputDeadband(0.15, 0.01);
        tilterPID.setTolerance(5, 1); //possible position and velocity tolerance values

        tilterPID.reset();

        yOffController = new PIDController(0.023, 0, 0, 0, 0.02);
        yOffController.setInputRange(-28, 28);
        yOffController.setOutputRange(-1, 1);
        yOffController.setTolerance(1, 0.001);

        yOffController.reset();
        yOffController.setSetpoint(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Tilter Angle", getTilterAngle());
        SmartDashboard.putBoolean("Tilter Limit", getBottomLimit());
    }

    /**
     * @brief Moves the tilter by a given speed
     * 
     * @param speed The speed to move the tilter at
     */

    public void move(double speed) {
        SmartDashboard.putNumber("Tilter Voltage", speed);
        tilterMotor.set(ControlMode.PercentOutput, enforceLimits(speed));
    }

    public double enforceLimits(double speed) {
        if (getBottomLimit()) {
            zeroEncoder();
            if (speed < 0) {
                speed = 0;
            }
        }
        return speed;
    }

    /**
     * @brief Sets the setpoint for the tiler PID
     * 
     * @param setpoint  The setpoint
     */

    public void setSetpoint(double setpoint) {
        tilterPID.setSetpoint(setpoint);
    }

    /**
     * @brief Gets the setpoint of the tilter PID
     * 
     * @return  The setpoint
     */

    public double getSetpoint() {
        return tilterPID.getSetpoint();
    }

    public void zeroEncoder() { // The bounds right now are 0 to 900
        tilterMotor.setSelectedSensorPosition(0);
    }

    public double getEncoderValue() {
        return tilterMotor.getSelectedSensorPosition();
    }

    /**
     * @brief Calculates the output of the tilterPID and moves the tilterMotor with it
     */

    public void calculate() {
        move(tilterPID.calculate(getEncoderValue()));
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

    public boolean getBottomLimit() {
        return !tilterBottomLimit.get();
    }

    /**
     * @brief Solves for angles on the tilter mechanism
     * 
     * Calculates the remaining angle in a mechanism made up of 4 linkages. 
     * Given the lengths of the linkages and an angle, it solves for the angle of the opposite linkage. 
     * The linkages are arranged in clockwise order: A, B, C, D. 
     * See this graph for more information: https://www.desmos.com/calculator/ehqwghlllg 
     * 
     * @param A         Length of linkage A
     * @param B         Length of linkage B
     * @param C         Length of linkage C
     * @param D_x       X component of linkage D. Right is positive
     * @param D_y       Y component of linkage D. Up is positive
     * @param thetaA    Angle of linkage A above the horizontal
     * 
     * @return          Angle of linkage C above the horizontal
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
     * @brief Gets the angle of the linkage being directly controlled by the tilter motor
     * 
     * @return  The angle of the linkage
     */
    private double getLinkageAngle() {
        return tilterMotor.getSelectedSensorPosition() * KDegreesPerTick + KDegreeOffset;
    }

    /**
     * @brief Computes the angle that the tilter is tilting the flywheel at above the horizontal
     * 
     * @return  The angle
     */
    public double getTilterAngle() {
        //return tilterEncoder.getPosition() * KDegreesPerTick; // Function that gets the encoder value from the motor object
        return toTilterAngle(getLinkageAngle());   
    }

    /**
     * @brief Converts an angle of the tilter to an angle of the tilter's linkage
     * 
     * @param flywheelAngle Angle of the tilter
     * @return              Angle of the tilter linkage
     */
    private double toLinkageAngle(double flywheelAngle) {
        double thetaC = solveForAngle(4.757, 4.5, 4, 7.75, -2, (flywheelAngle - 90) * Math.PI / 180);
        return (thetaC * 180 / Math.PI);
    }

    /**
     * @brief Converts an angle of the tilter's linkage to the angle of the flywheel
     * 
     * @param tilterAngle   Angle of the tilter linkage
     * @return              Angle of the tilter
     */
    private double toTilterAngle(double tilterAngle) {
        double thetaC = solveForAngle(4, 4.5, 4.757, 7.75, 2, tilterAngle * Math.PI / 180);
        return (thetaC * 180 / Math.PI) - 90;
    }

    // public double getLimelightHeight(double tilterAngle) {
    //     double AngleT = toTilterAngle(tilterAngle);
    //     double AngleLc = 48.21521752;
    //     double AngleA = 90 - (90 - AngleLc) - (90 - AngleT);
    //     double RadianA = Math.toRadians(AngleA);
    //     double Height1 = Math.sin(RadianA) * 7.879;
    //     double distance = Height1 + 19.1255;
    // }
    //Alex's unfinished code
}
