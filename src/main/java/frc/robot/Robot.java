// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.Autonomous.AutoRoutine;
import frc.robot.Subsystems.Climbers;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.Climbers.AutoClimbStep;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.LEDs;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {  
  //Subsystem Declarations

  public static final Drivetrain drivetrain = new Drivetrain(
    Constants.LEFT_DRIVE_TRAIN_0,
    Constants.LEFT_DRIVE_TRAIN_1,
    Constants.RIGHT_DRIVE_TRAIN_0,
    Constants.RIGHT_DRIVE_TRAIN_1
  );
  
  public static final Intake intake = new Intake(
    Constants.HORIZONTAL_INTAKE,
    Constants.INTAKE_LIFT
  );

  public static final Shooter shooter = new Shooter(
    Constants.SHOOTER,
    Constants.TRIGGER
  );

  public static final Climbers climbers = new Climbers(
    Constants.LEFT_CLIMBER_0,
    Constants.LEFT_CLIMBER_1,
    Constants.LEFT_CLIMBER_ROTATE, 
    Constants.RIGHT_CLIMBER_0, 
    Constants.RIGHT_CLIMBER_1, 
    Constants.RIGHT_CLIMBER_ROTATE
  );
  
  public static final Limelight limelight = new Limelight();

  public static final LEDs ledStrip = new LEDs(0,28);

  //Controllers
  public static final Controller controller0 = new Controller(Constants.DRIVER_CONTROLLER_0);
  public static final Controller controller1 = new Controller(Constants.DRIVER_CONTROLLER_1);

  public enum AutoSection {
    STARTUP,
    OPENING_ACTION,
    MOVEMENT,
    CLOSING_ACTION,
    FINISH,
    EXIT_AUTO
  }

  public static AutoSection autoSection;
  public static String prevAuto = "";
  public double timeCheck;

  //Test Timer & Flag
  Timer timer = new Timer();

  /*
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    HDD.initBot();
    PlayerConfigs.initTeamSetup();
    //Set init for autoclimb
    climbers.autoClimbStep = AutoClimbStep.MANUAL_MODE;
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    HDD.m_field.setRobotPose(drivetrain.odometry.getPoseMeters());
    drivetrain.m_drive.feed();
    SmartDashboard.putNumber("Match Time",Timer.getMatchTime());
  }

  @Override
  public void autonomousInit() {
    Robot.limelight.setLED(3);
    Robot.drivetrain.brakeModeEngage(true);
    Constants.teamColor = DriverStation.getAlliance().toString();
    ledStrip.stripeRB();
    autoSection = AutoSection.STARTUP;
    AutoRoutine.timeCheck = Timer.getFPGATimestamp();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    AutoRoutine.runAutonomous();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    Robot.limelight.setLED(0);
    Robot.drivetrain.brakeModeEngage(false);
    autoSection = AutoSection.EXIT_AUTO;
    Constants.teamColor = DriverStation.getAlliance().toString();
    PlayerConfigs.getPlayers();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    PlayerConfigs.getDriverConfig();
    PlayerConfigs.getCoDriverConfig();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Robot.drivetrain.brakeModeEngage(false);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    HDD.updateStartupConfig();
    ledStrip.mardiGras();
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    climbers.resetEncoders();
    intake.resetEncoder();
    ledStrip.stripeRB();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }
}
