package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import static frc.robot.Constants.*;

public class Tilter extends SubsystemBase {
    private final CANSparkMax tilterMotor; 
    private final CANEncoder tilterEncoder;
    private final PIDController tilterPID;
    
    private static final double ticksPerRev = 2048; 
    private static final double gearRatio = 300;
    private static final double degreesPerTick = KDegreesPerRevolution / (ticksPerRev * gearRatio);

    public Tilter() {
        tilterMotor = new CANSparkMax(KTilterSparkMax, CANSparkMaxLowLevel.MotorType.kBrushless); 
        tilterEncoder = new CANEncoder(tilterMotor);
        tilterPID = new PIDController(0.0001, 0, 0);

        tilterPID.enableContinuousInput(0, 40);
        tilterPID.disableContinuousInput();

        tilterPID.reset();
    }

    public void move(double TilterSpeed){
        tilterMotor.set(TilterSpeed);
    }

    public void setTilterSetpoint (double setpoint){
        tilterPID.setSetpoint(setpoint);
    }

    public double getTilterSetpoint () {
        return tilterPID.getSetpoint();
    }

    public double getTilterAngle() {
        return tilterEncoder.getPosition() * degreesPerTick; // Function that gets the encoder value from the motor object
        
    }

    public void calculate() {
        move(tilterPID.calculate(getTilterAngle()));
    }
 
}
