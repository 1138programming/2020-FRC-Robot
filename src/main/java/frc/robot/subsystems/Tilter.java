package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Tilter extends SubsystemBase {
    private final TalonSRX tilterMotor; 
    public static final int KTilterTalon = 1; 

    public Tilter (){
        tilterMotor = new TalonSRX(KTilterTalon); 
    }
}
