package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import static frc.robot.Constants.*;

public class Tilter extends SubsystemBase {
    private final CANSparkMax tilterMotor; 
    private final PIDController tilterPID;
    private double position = 0;

    public Tilter() {
        tilterMotor = new CANSparkMax(KTilterSparkMax, CANSparkMaxLowLevel.MotorType.kBrushless); 
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

    /*public double getTilterAngle() {
        return tilterPID.getAngle();
    }

    public void calculate (){
        move(tilterPID.calculate(getTilterAngle()));
    }*/

}
