package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
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
    //base constants
    public static final int KLeftFrontTalon = 1;
    public static final int KLeftBackTalon = 2;
    public static final int KRightFrontTalon = 3;
    public static final int KRightBackTalon = 4;
    public static final int KBaseShifterSolenoid = 0;
    public static final double KBaseMediumSpeed = 0.75;

    //wheel constants
    public static final int KWheel = 1;
    public static final int KWheelSolenoid = 7;
    public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    //Storage constants 
    public static final int KStage1Talon = 9;
    public static final int KStage2Talon = 10;
    public static final int KStorageShifter = 11;
    public static final int KBallSensor1 = 12; 
    public static final int KBallSensor2 = 13; 
    public static double KStorageSpeed = 1;

    //Pneumatics Contsants(none at the moment)

    //Indexer constants
    public static final int KIndexer = 7;
    public static double KIndexerSpeed = 1;

    //Intake Constants
    public static final int KIntakeVictor = 8;
    public static final int KLeftIntakeSolenoid = 3;
    public static final int KRightIntakeSolenoid = 4;
    public static double KIntakeSpeed = 1;

    //FlyWheel Constants
    public static final int KFlywheelTop = 5;
    public static final int KFlywheelBottom = 6;

    //Climb Constants
    public static final int KClimbTalon = 1;
    public static final int KClimbVictor = 2;
    public static final int KClimbSolenoid = 6;

    //Camera Constan (none at the moment)

}
