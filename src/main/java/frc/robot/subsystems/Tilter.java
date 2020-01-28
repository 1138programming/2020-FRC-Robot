package frc.robot.subsystems;

import static frc.robot.Constants.KTilterTalon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tilter extends SubsystemBase {
    private final TalonSRX tilterMotor; 
    private final PIDController tilterPID;
    private double position = 0;

    public Tilter() {
        tilterMotor = new TalonSRX(KTilterTalon);
        tilterPID = new PIDController(0.0001, 0, 0);

        tilterPID.enableContinuousInput(0, 40);
        tilterPID.disableContinuousInput();

        tilterPID.reset();
    }

    /**
     * Moves the tilter by a given speed
     * 
     * @param speed The speed to move the tilter at
     */
    public void move(double speed) {
        tilterMotor.set(ControlMode.PercentOutput, speed);
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
    public double getSetpoint () {
        return tilterPID.getSetpoint();
    }
}
