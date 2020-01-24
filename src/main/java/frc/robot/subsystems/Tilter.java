package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

public class Tilter extends SubsystemBase {

    private final TalonSRX tilterMotor; 

    public Tilter() {
        tilterMotor = new TalonSRX(KTilterTalon);
    }
    public void move(double speed){
        tilterMotor.set(ControlMode.PercentOutput, speed);
    }
}
