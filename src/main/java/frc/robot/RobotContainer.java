package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Base.DriveWithJoysticks;
import frc.robot.commands.Base.BaseShiftHigh;
import frc.robot.commands.Base.BaseShiftMedium;
import frc.robot.commands.Base.BaseShiftLow;
import frc.robot.commands.Base.BaseLinearMovement;
import frc.robot.commands.Camera.MoveBaseToTarget;
import frc.robot.commands.Camera.MoveTilterToTarget;
import frc.robot.commands.Flywheel.StopFlywheel;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Intake.IntakeStop;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Wheel.WheelStop;
import frc.robot.commands.Wheel.GoToColor;
import frc.robot.commands.Tilter.TilterStop;
import frc.robot.commands.Pneumatics.CompressorControl;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
  // Controller Constants
  private static final int KLogitechDrive = 0;
  private static final int KXboxArms = 1;  

  //Deadzone
  private static final double KDeadZone = 0.2;

  //Logitech Button Constants
  public static final int KLogitechButtonX = 1;
  public static final int KLogitechButtonA = 2;
  public static final int KLogitechButtonB = 3;
  public static final int KLogitechButtonY = 4;
  public static final int KLogitechLeftBumper = 5; 
  public static final int KLogitechRightBumper = 6;
  public static final int KLogitechLeftTrigger = 7;
  public static final int KLogitechRightTrigger = 8; 

  private static final int KLeftYAxis = 1;
  private static final int KRightYAxis = 3;

  //Xbox Button Constants
  public static final int KXboxButtonA = 1; 
  public static final int KXboxButtonB = 2;
  public static final int KXboxButtonX = 3;  
  public static final int KXboxButtonY = 4; 
  public static final int KXboxLeftBumper = 5; 
  public static final int KXboxRightBumper = 6; 
  public static final int KXboxStartButton = 8; 
  public static final int KXboxLeftTrigger = 9; 
  public static final int KXboxRightTrigger = 10; 


  public static Joystick logitech;
  public static XboxController xbox; 
  public JoystickButton logitechBtnX, logitechBtnA, logitechBtnB, logitechBtnY, logitechBtnLB, logitechBtnRB, logitechBtnLT, logitechBtnRT; //Logitech Button
  public JoystickButton xboxBtnA, xboxBtnB, xboxBtnX, xboxBtnY, xboxBtnLB, xboxBtnRB, xboxBtnStrt, xboxBtnLT, xboxBtnRT;
  
  /**
   * The container for the robot.  Contains default commands, OI devices, and commands.
   */
  public RobotContainer() {
    // Set default commands
    Robot.base.setDefaultCommand(new DriveWithJoysticks());
    Robot.flywheel.setDefaultCommand(new StopFlywheel());
    Robot.indexer.setDefaultCommand(new IndexStop());
    Robot.pneumatics.setDefaultCommand(new CompressorControl());
    Robot.intake.setDefaultCommand(new IntakeStop());
    Robot.storage.setDefaultCommand(new StorageStop());

    TilterStop tilterStop = new TilterStop();
    SmartDashboard.putBoolean("Tilter exists?", Robot.tilter != null);
    SmartDashboard.putBoolean("Tilter stop exists?", tilterStop != null);
    Robot.tilter.setDefaultCommand(tilterStop);
    Robot.wheel.setDefaultCommand(new WheelStop());

    // Controllers
    logitech = new Joystick(KLogitechDrive);
    xbox = new XboxController(KXboxArms);
    
    // Logitch Buttons 
    logitechBtnX = new JoystickButton(logitech, KLogitechButtonX);
    logitechBtnA = new JoystickButton(logitech, KLogitechButtonA);
    logitechBtnB = new JoystickButton(logitech, KLogitechButtonB);
    logitechBtnY = new JoystickButton(logitech, KLogitechButtonY);
    logitechBtnLB = new JoystickButton(logitech, KLogitechLeftBumper);
    logitechBtnRB = new JoystickButton(logitech, KLogitechRightBumper);
    logitechBtnLT = new JoystickButton(logitech, KLogitechLeftTrigger);
    logitechBtnRT = new JoystickButton(logitech, KLogitechRightTrigger);

    // XBox Buttons
    xboxBtnA = new JoystickButton(xbox, KXboxButtonA);
  	xboxBtnB = new JoystickButton(xbox, KXboxButtonB);
		xboxBtnX = new JoystickButton(xbox, KXboxButtonX);
		xboxBtnY = new JoystickButton(xbox, KXboxButtonY);
		xboxBtnLB = new JoystickButton(xbox, KXboxLeftBumper);
		xboxBtnRB = new JoystickButton(xbox, KXboxRightBumper);
		xboxBtnStrt = new JoystickButton(xbox, KXboxStartButton);
		xboxBtnLT = new JoystickButton(xbox, KXboxLeftTrigger);
    xboxBtnRT = new JoystickButton(xbox, KXboxRightTrigger);


    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    logitechBtnY.whenPressed(new GoToColor());
    logitechBtnRB.whenPressed(new BaseShiftHigh());
    logitechBtnRB.whenReleased(new BaseShiftMedium());
    logitechBtnRT.whenPressed(new BaseShiftLow());
    logitechBtnRT.whenReleased(new BaseShiftMedium());
    logitechBtnLT.whenPressed(new BaseLinearMovement(100000, 100000));
    logitechBtnA.whileHeld(new MoveBaseToTarget());
    logitechBtnY.whileHeld(new MoveTilterToTarget());
  }

  public double getRightAxis() {
    final double Y = logitech.getRawAxis(KRightYAxis);
    //SmartDashboard.putNumber("Here's a thingy!", Y);
    if (Y > KDeadZone || Y < -KDeadZone)
      return -Y;
    else
      return 0;
  }

  public double getLeftAxis() {
    final double Y = logitech.getRawAxis(KLeftYAxis);
    //SmartDashboard.putNumber("Here's a left thingy!", Y);
    if(Y > KDeadZone || Y < -KDeadZone)
      return -Y;
    else 
      return 0; 
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
    return null;
  }
}
