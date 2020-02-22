package frc.robot;

import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;

public final class Constants {
// Motors IDs
    //Base
    public static final int KLeftFrontTalon = 0;
    public static final int KLeftBackTalon = 1;
    public static final int KRightFrontTalon = 2;
    public static final int KRightBackTalon = 3;
    //Wheel
    public static final int KWheelVictor = 4;
    //Storage
    public static final int KStage1Victor = 5;
    public static final int KStage2Victor = 6;
    //Flywheel
    public static final int KFlywheelTopSpark = 7;
    public static final int KFlywheelBottomSpark = 8;
    //Intake
    public static final int KIntakeSpark = 9;
    //Indexer
    public static final int KIndexerSpark = 10;
    //Climb
    public static final int KClimbTalon = 11;
    public static final int KClimbVictor = 12;
    //Tilter
    public static final int KTilterTalon = 13;

// Solenoid Channels
    //Base
    public static final int KBaseShifter = 0;
    //IDK where port 1 went
    //Storage
    public static final int KStorageShifterSolenoid = 7;
    //Intake
    public static final int KLeftIntakeSolenoid = 1;
    public static final int KRightIntakeSolenoid = 6;
    //Climb
    public static final int KClimbRatchetSolenoid = 5;

// Sensors Ports
    //Storage
    public static final int KBallSensor1 = 0; 
    public static final int KBallSensor2 = 1;
    //Climb
    public static final int KTopLimit = 2; 
    public static final int KBottomLimit = 3; 
    //Tilter
    public static final int KTilterBottomLimit = 4;

// Default PWM values
    public static final double KClimbPWM = 1;
    public static double KIndexerPWM = 1;
    public static double KIntakePWM = 0.5;
    public static double KStoragePWM = 1;
    public static final double KTilterPWM = 0.5;
    public static final double KWheelPWM = 0.25;

//Other constants
    //Global Conversion Factors
    public static final int KDegreesPerRevolution = 360;
    //Base
    public static final double KBaseMediumGear = 0.5;
    public static final double KBaseMaxVel = 5;
    public static final double KBaseMaxAccel = 0.1;
    //Camera
    //Wheel
    public static final Color KBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color KGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color KRedTarget = ColorMatch.makeColor(0.438, 0.386, 0.175);
    public static final Color KYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
}
