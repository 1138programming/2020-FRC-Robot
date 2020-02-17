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
        yOffController.setSetpoint(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Tilter Angle", getTilterAngle());
        SmartDashboard.putBoolean("Tilter Limit", getBottomLimit());
    }

    /**
     * Moves the tilter by a given speed
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
     * Sets the setpoint for the tiler PID
     * 
     * @param setpoint  The setpoint
     */
    public void setSetpoint(double setpoint) {
        tilterPID.setSetpoint(setpoint);
    }

    /**
     * Gets the setpoint of the tilter PID
     * 
     * @return  The setpoint
     */
    public double getSetpoint() {
        return tilterPID.getSetpoint();
    }

    public void zeroEncoder() { // The bounds right now are 0 to 900
        tilterMotor.setSelectedSensorPosition(0);
    }

    public double getTilterAngle() {
        //return tilterEncoder.getPosition() * KDegreesPerTick; // Function that gets the encoder value from the motor object
        //return tilterMotor.getSelectedSensorPosition() * KDegreesPerTick + KDegreeOffset;
        return tilterMotor.getSelectedSensorPosition();
    }

    public void calculate() {
        move(tilterPID.calculate(getTilterAngle()));
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
 
    public boolean atSetpoint() {
        return tilterPID.atSetpoint();
    }

    public boolean getBottomLimit() {
        return !tilterBottomLimit.get();
    }
}
