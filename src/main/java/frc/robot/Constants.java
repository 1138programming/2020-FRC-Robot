package frc.robot;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.enums.SolenoidState;

public final class Constants {
    public static final int KDegreesPerRevolution = 360;

    //base constants
    //Talons
    public static final int KLeftFrontTalon = 0;
    public static final int KLeftBackTalon = 1;
    public static final int KRightFrontTalon = 2;
    public static final int KRightBackTalon = 3;
    //Solenoids
    public static final int KBaseShifterForwardChannel = 0;
    public static final int KBaseShifterReverseChannel = 7;
    //others
    public static final double KBaseMediumGear = 0.5;
    public static final double KBaseMaxVel = 5;
    public static final double KBaseMaxAccel = 0.1;

    //wheel constants
    //Talons
    public static final int KWheelVictor = 4;
    //Color Targets
    public static final Color KBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color KGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color KRedTarget = ColorMatch.makeColor(0.438, 0.386, 0.175);
    public static final Color KYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    // Others
    public static final double KWheelSpeed = .25;

    //Storage constants 
    //Talons
    public static final int KStage1Victor = 5;
    public static final int KStage2Victor = 6;
    //Solenoids
    public static final int KStorageShifterSolenoid = 2;
    //Optical Sensors
    public static final int KBallSensor1 = 0; 
    public static final int KBallSensor2 = 1;
    //others
    public static double KStorageSpeed = 1;

    //Pneumatics Contsants(none at the moment)

    //Indexer constants
    //Sparks
    public static final int KIndexerSpark = 7;
    //others
    public static double KIndexerSpeed = 1;

    //Intake Constants
    //Victors
    public static final int KIntakeSpark = 8;
    //Solenoids
    public static final int KLeftIntakeSolenoid = 3;
    public static final int KRightIntakeSolenoid = 4;
    //others
    public static double KIntakeSpeed = 0.5;

    //FlyWheel Constants
    //Sparks
    public static final int KFlywheelTopSpark = 9;
    public static final int KFlywheelBottomSpark = 10;

    //Climb Constants
    //Talons+Victors
    public static final int KClimbTalon = 11;
    public static final int KClimbVictor = 12;
    //Solenoids
    public static final int KClimbRatchetSolenoid = 5;
    //Limits
    public static final int KTopLimit = 2; 
    public static final int KBottomLimit = 3; 
    //others
    public static final double KClimbSpeed = 1;
    

    //Camera Constants 
    //PID
    //public static final double kP = 0.001;
    //public static final double kI = 0.0;
    //public static final double kD = 0.0;
    //Height
    public static final double h1 = 1.42;
    public static final double h2 = 7.25;

    //Tilter Constant
    //Talons
    public static final int KTilterTalon = 13;
    public static final double KTilterSpeed = 0.5;
    //Limit
    public static final int KTilterBottomLimit = 4;
}
