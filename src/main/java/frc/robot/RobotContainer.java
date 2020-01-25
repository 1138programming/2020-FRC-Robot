package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Base.DriveWithJoysticks;
import frc.robot.commands.Flywheel.StopFlywheel;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Intake.IntakeStop;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Wheel.WheelStop;

public class RobotContainer {
  // Controller Constants
  private static final int KLogitechDrive = 0;
  private static final int KXboxArms = 1;  

  //Deadzone
  private static final double KDeadZone = 0.2;

  //Logitech Button Constants
  public static final int KButton1 = 1;
  public static final int KButton2 = 2;
  public static final int KButton3 = 3;
  public static final int KButton4 = 4;
  public static final int KButton5 = 5; 
  public static final int KButton6 = 6;
  public static final int KButton7 = 7;
  public static final int KButton8 = 8; 

  private static final int KLeftYAxis = 5;
  private static final int KRightYAxis = 1;

  //Xbox Button COnstants
  public static final int KButtonA = 1; 
  public static final int KButtonB = 2;
  public static final int KButtonX = 3;  
  public static final int KButtonY = 4; 
  public static final int KLeftBumper = 5; 
  public static final int KRightBumper = 6; 
  public static final int KStartButton = 8; 
  public static final int KLeftTrigger = 9; 
  public static final int KRightTrigger = 10; 


  public static Joystick logitech;
  public static XboxController xbox; 
  public JoystickButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8; //Logitech Button
  public JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB, btnStrt, btnLT, btnRT;
  
  /**
   * The container for the robot.  Contains default commands, OI devices, and commands.
   */
  public RobotContainer() { 

    Robot.base.setDefaultCommand(new DriveWithJoysticks());
    Robot.flywheel.setDefaultCommand(new StopFlywheel());
    Robot.indexer.setDefaultCommand(new IndexStop());
    Robot.intake.setDefaultCommand(new IntakeStop());
    Robot.storage.setDefaultCommand(new StorageStop());
    Robot.wheel.setDefaultCommand(new WheelStop());

    //Controllers
    logitech = new Joystick(KLogitechDrive);
    xbox = new XboxController(KXboxArms);
    
    //Logitch Buttons 
    btn1 = new JoystickButton(logitech, KButton1);
    btn2 = new JoystickButton(logitech, KButton2);
    btn3 = new JoystickButton(logitech, KButton3);
    btn4 = new JoystickButton(logitech, KButton4);
    btn5 = new JoystickButton(logitech, KButton5);
    btn6 = new JoystickButton(logitech, KButton6);
    btn7 = new JoystickButton(logitech, KButton7);
    btn8 = new JoystickButton(logitech, KButton8);

    //XBox Buttons
    btnA = new JoystickButton(xbox, KButtonA);
		btnB = new JoystickButton(xbox, KButtonB);
		btnX = new JoystickButton(xbox, KButtonX);
		btnY = new JoystickButton(xbox, KButtonY);
		btnLB = new JoystickButton(xbox, KLeftBumper);
		btnRB = new JoystickButton(xbox, KRightBumper);
		btnStrt = new JoystickButton(xbox, KStartButton);
		btnLT = new JoystickButton(xbox, KLeftTrigger);
    btnRT = new JoystickButton(xbox, KRightTrigger);


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
    
  }

  public double getRightAxis() {
    double Y = logitech.getRawAxis(KLeftYAxis);
    //SmartDashboard.putNumber("Here's a thingy!", Y);
    if (Y > KDeadZone || Y < -KDeadZone)
      return Y;
    else
      return 0;
  }

  public double getLeftAxis() {
    double Y = logitech.getRawAxis(KRightYAxis);
    //SmartDashboard.putNumber("Here's a left thingy!", Y);
    if(Y > KDeadZone || Y < -KDeadZone)
      return Y;
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
