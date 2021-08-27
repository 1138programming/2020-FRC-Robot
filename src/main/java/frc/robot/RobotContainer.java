package frc.robot;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.CommandGroups.PositionWithLimelight;
import frc.robot.CommandGroups.Auton.AutonShootFromLine;
// import frc.robot.CommandGroups.Auton.FiveBallAuton; //Removed for 2021 challenge
import frc.robot.CommandGroups.Auton.BarrelAuton;
import frc.robot.CommandGroups.FeedShot;
import frc.robot.commands.Base.DriveWithJoysticks;
import frc.robot.commands.Camera.SwitchPipelineToDefault;
import frc.robot.commands.Camera.SwitchPipelineToTargeting;
import frc.robot.commands.Base.BaseShiftHigh;
import frc.robot.commands.Base.BaseShiftLow;
import frc.robot.commands.Base.BaseShiftMedium;
import frc.robot.commands.Base.ToggleHighGear;
import frc.robot.commands.Base.ToggleLowGear;
import frc.robot.commands.Base.DriveWithJoysticks;
import frc.robot.commands.Climb.ClimbDown;
import frc.robot.commands.Climb.ClimbStop;
import frc.robot.commands.Climb.ClimbUpWithoutLimit;
import frc.robot.commands.Climb.ClimbDownWithoutLimit;
import frc.robot.commands.Climb.ClimbUp;
import frc.robot.commands.Climb.ClimbWithJoysticks;
import frc.robot.commands.Climb.ClimbDisengage;
import frc.robot.commands.Flywheel.StopFlywheel;
import frc.robot.commands.Flywheel.SpinUpFlywheel;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Indexer.IndexOut;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Intake.IntakeStop;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeOut;
import frc.robot.commands.Intake.IntakeRetract;
import frc.robot.commands.Intake.IntakeDeploy;
import frc.robot.commands.Micellaneous.ResetAll;
import frc.robot.commands.Micellaneous.CancelAll;
import frc.robot.commands.Pneumatics.CompressorControl;
import frc.robot.commands.Intake.ToggleIntakePosition;
import frc.robot.commands.Storage.StorageStop;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.commands.Storage.StorageOut;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Wheel.WheelStop;
import frc.robot.commands.Wheel.GoToColor;
import frc.robot.commands.Wheel.Spin;
import frc.robot.commands.Tilter.MoveTilterTo;
import frc.robot.commands.Tilter.TiltDown;
import frc.robot.commands.Tilter.MoveTilterToIdealAngle;
import frc.robot.commands.Tilter.MoveTilterFromTable;
import frc.robot.commands.Tilter.TiltUp;
import frc.robot.commands.Tilter.TilterStop;
import frc.robot.commands.Tilter.TiltWithJoysticks;
import frc.robot.commands.Wheel.WheelStop;
import frc.robot.commands.Wheel.GoToColor;
import frc.robot.CommandGroups.Collecting;
import frc.robot.CommandGroups.EjectBalls;
import frc.robot.CommandGroups.MoveOutEverythingALittleBit;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Micellaneous.Delay;
import frc.robot.Robot;
import java.util.function.BooleanSupplier;

public class RobotContainer {
  // Controller Constants
  private static final int KLogitechDrive = 0;
  private static final int KXboxArms = 1;  

  //Deadzone
  private static final double KDeadZone = 0.02;

  //Logitech Button Constants
  /* !Make sure logi controller is set to "Logitech Dual Action" NOT "Gamepad F310"!! */
  public static final int KLogitechButtonX = 1;
  public static final int KLogitechButtonA = 2;
  public static final int KLogitechButtonB = 3;
  public static final int KLogitechButtonY = 4;
  public static final int KLogitechLeftBumper = 5; 
  public static final int KLogitechRightBumper = 6;
  public static final int KLogitechLeftTrigger = 7;
  public static final int KLogitechRightTrigger = 8;

  private static final int KLogiLeftYAxis = 1;
  private static final int KLogiRightYAxis = 3; 
  private static final int KLogiLeftXAxis = 0; 
  private static final int KLogiRightXAxis = 2; 

  //Xbox Button Constants
  public static final int KXboxButtonA = 1; 
  public static final int KXboxButtonB = 2;
  public static final int KXboxButtonX = 3;  
  public static final int KXboxButtonY = 4; 
  public static final int KXboxLeftBumper = 5; 
  public static final int KXboxRightBumper = 6; 
  public static final int KXboxSelectButton = 7; 
  public static final int KXboxStartButton = 8; 
  public static final int KXboxLeftTrigger = 9; 
  public static final int KXboxRightTrigger = 10; 

  public static Joystick logitech;
  public static XboxController xbox; 
  public JoystickButton logitechBtnX, logitechBtnA, logitechBtnB, logitechBtnY, logitechBtnLB, logitechBtnRB, logitechBtnLT, logitechBtnRT; //Logitech Button
  public JoystickButton xboxBtnA, xboxBtnB, xboxBtnX, xboxBtnY, xboxBtnLB, xboxBtnRB, xboxBtnStrt, xboxBtnSelect, xboxBtnLT, xboxBtnRT;
  public Trigger xboxLeftJoystick;

  /**
   * The container for the robot.  Contains default commands, OI devices, and commands.
   */
  public RobotContainer() {
    // Set default commands
    Robot.base.setDefaultCommand(new DriveWithJoysticks());
    Robot.climb.setDefaultCommand(new ClimbStop());
    Robot.camera.setDefaultCommand(new SwitchPipelineToDefault());
    Robot.flywheel.setDefaultCommand(new StopFlywheel());
    Robot.indexer.setDefaultCommand(new IndexStop());
    Robot.pneumatics.setDefaultCommand(new CompressorControl());
    Robot.intake.setDefaultCommand(new IntakeStop());
    Robot.storage.setDefaultCommand(new StorageStop());
    //Robot.tilter.setDefaultCommand(new TiltWithJoysticks());
    Robot.tilter.setDefaultCommand(new TilterStop());
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
    xboxBtnSelect = new JoystickButton(xbox, KXboxSelectButton);
		xboxBtnStrt = new JoystickButton(xbox, KXboxStartButton);
		xboxBtnLT = new JoystickButton(xbox, KXboxLeftTrigger);
    xboxBtnRT = new JoystickButton(xbox, KXboxRightTrigger);

    BooleanSupplier xboxLeftSup = () -> {return getXboxLeftAxis() != 0;};
    xboxLeftJoystick = new Trigger(xboxLeftSup);

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
    // Commands whose isFinished is false
    PositionWithLimelight positionWithLimelight = new PositionWithLimelight();
    Collecting collecting = new Collecting();
    FeedShot feedShot = new FeedShot();
    SpinUpFlywheel spinUpFlywheel = new SpinUpFlywheel();
    SpinUpFromTable spinUpFromTable = new SpinUpFromTable();
    TiltWithJoysticks tiltWithJoysticks = new TiltWithJoysticks();
    MoveTilterFromTable moveTilterFromTable = new MoveTilterFromTable();

    // ----------Logitech----------
    // Shift high on press and medium on release
    //logitechBtnRB.whenPressed(new BaseShiftHigh());
    //logitechBtnRB.whenReleased(new BaseShiftMedium());
    logitechBtnRB.whenPressed(new ToggleHighGear()); //press to shift to high(slow), does not return to medium!

    // Shift low on press and medium on release
    //logitechBtnRT.whenPressed(new BaseShiftLow());
    //logitechBtnRT.whenReleased(new BaseShiftMedium());
    logitechBtnRT.whenPressed(new ToggleLowGear()); ////press to shift to low(fast), does not return to medium!

    // Climb up
    // logitechBtnLB.whenPressed(new ClimbDisengage(), false); //Make sure logi controller is set to "Logitech Dual Action" NOT "Gamepad F310"
    // logitechBtnLB.whileHeld(new ClimbUp());
    logitechBtnLB.whenPressed(new ClimbDisengage(), false); 
    logitechBtnLB.whileHeld(new ClimbUp()); 

    // Climb down
    // logitechBtnLT.whenPressed(new ClimbDisengage(), false); //Make sure logi controller is set to "Logitech Dual Action" NOT "Gamepad F310"
    // logitechBtnLT.whileHeld(new ClimbDown());
    logitechBtnLT.whenPressed(new ClimbDisengage(), false);
    logitechBtnLT.whileHeld(new ClimbDown());

    // Position with limelight and start flywheel
    logitechBtnA.whileHeld(positionWithLimelight);
    // logitechBtnA.toggleWhenActive(moveTilterFromTable);
    // logitechBtnA.toggleWhenActive(spinUpFromTable);

    // Use wheel mechanism to go to color
    // logitechBtnX.whenPressed(goToColor); //Wheel feature removed on THX
    // logitechBtnY.whenPressed(spin);

    // ----------Xbox----------
    xboxBtnB.whileActiveOnce(feedShot); // Manual feed shot control
    //xboxBtnB.whileActiveOnce(spinUpFromTable);
    
    // Actively start/stop flywheel
    xboxBtnA.toggleWhenActive(spinUpFlywheel);
    //xboxBtnA.toggleWhenActive(spinUpFromTable);
    //xboxBtnA.toggleWhenActive(moveTilterFromTable);

    // Toggle collector position
    xboxBtnX.toggleWhenActive(new ToggleIntakePosition());

    // Collecting button. When released, move all balls out for a bit
    xboxBtnY.whileActiveOnce(collecting);
    BooleanSupplier lessThanFive = () -> {return Robot.storage.getBallCount() == 5;};
    //xboxBtnY.whenReleased(new MoveOutEverythingALittleBit().raceWith(new Conditional(lessThanFive)));
    xboxBtnY.whenReleased(new ConditionalCommand(new MoveOutEverythingALittleBit(), new Delay(0), lessThanFive));

    // Eject balls
    xboxBtnRB.whileHeld(new EjectBalls());
    xboxBtnRB.cancelWhenPressed(collecting);

    // Intake out
    xboxBtnLB.whileHeld(new IntakeOut());
    
    // Manual override for moving the tilter with the joystick
    xboxLeftJoystick.whileActiveOnce(tiltWithJoysticks);

    // Cancel all currently running commands, going back to default commands
    xboxBtnSelect.whenPressed(new CancelAll()); 
  }

  public double getRightAxis() {
    final double Y = logitech.getRawAxis(KLogiRightYAxis);
    if (Y > KDeadZone || Y < -KDeadZone)
      return -Y;
    else
      return 0;
  }

  public double getLeftAxis() {
    final double Y = logitech.getRawAxis(KLogiLeftYAxis);
    if(Y > KDeadZone || Y < -KDeadZone)
      return -Y;
    else 
      return 0; 
  }

  public double getArcadeRightAxis() {
    double X = logitech.getRawAxis(KLogiRightXAxis);
    if (X > KDeadZone || X < -KDeadZone) {
      return -X;
    } else {
      return 0; 
    }
  }

  public double getArcadeLeftAxis() {
    double X = logitech.getRawAxis(KLogiLeftXAxis);
    if (X > KDeadZone || X < -KDeadZone) {
      return -X;
    } else {
      return 0; 
    }
  }

  public double getXboxLeftAxis() {
    final double Y = xbox.getRawAxis(KLogiLeftYAxis);
    if(Y > KDeadZone || Y < -KDeadZone)
      return -Y;
    else 
      return 0;
  }

  public double getXboxRightAxis() {
    final double Y = xbox.getRawAxis(KLogiRightYAxis);
    if (Y > KDeadZone || Y < -KDeadZone)
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
    return new BarrelAuton();
    // return new AutonShootFromLine();
    // return null;
  }
}
