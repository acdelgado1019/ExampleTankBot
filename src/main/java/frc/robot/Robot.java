// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Autonomous.AutoCommand;
import frc.robot.Autonomous.OneBallAuto;
import frc.robot.Autonomous.ThreeBallAuto;
import frc.robot.Autonomous.TwoBallAuto;
import frc.robot.Subsystems.Climbers;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.ShooterIntake;

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
  
  public static final ShooterIntake shooterIntake = new ShooterIntake(
    Constants.HORIZONTAL_INTAKE,
    Constants.VERTICAL_INTAKE,
    Constants.INTAKE_LIFT
  );

  public static final Shooter shooter = new Shooter(
    Constants.SHOOTER
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

  //Controllers
  public static final Controller controller0 = new Controller(Constants.DRIVER_CONTROLLER_0);
  public static final Controller controller1 = new Controller(Constants.DRIVER_CONTROLLER_1);

  //Auto Commands
  Command autoSequence;
  private final Command oneBall = new OneBallAuto();
  private final Command twoBall = new TwoBallAuto();
  private final Command threeBall = new ThreeBallAuto();
  
  public SendableChooser<Command> m_chooser = new SendableChooser<>();
  public static SendableChooser<String> t_chooser = new SendableChooser<>();

  //Field display to Shuffleboard
  public static Field2d m_field = new Field2d();

  //Test Timer & Flag
  Timer timer = new Timer();
  boolean testComplete = false;

  /*
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Three Ball Auto", threeBall);
    m_chooser.addOption("Two Ball Auto", twoBall);
    m_chooser.addOption("One Ball Auto", oneBall);

    t_chooser.setDefaultOption("Red", "RED");
    t_chooser.addOption("Blue", "BLUE");

    // Put the choosers on the dashboard
    SmartDashboard.putData(m_chooser);
    SmartDashboard.putData(t_chooser);
    SmartDashboard.putString("System Testing", "NOT TESTED");

    AutoCommand.getConstraint();
    AutoCommand.getTrajectoryConfig();
      
    // Create and push Field2d to SmartDashboard.
    m_field = new Field2d();
    SmartDashboard.putData(m_field);
    SmartDashboard.putBoolean("Test Complete", testComplete);
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
    m_field.setRobotPose(drivetrain.odometry.getPoseMeters());
    drivetrain.m_drive.feed();
  }

  @Override
  public void autonomousInit() {
    autoSequence = m_chooser.getSelected();
    autoSequence.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    Command chosenRoutine = m_chooser.getSelected();
    String team = t_chooser.getSelected();

    if (chosenRoutine == twoBall && team == "BLUE"){AutoCommand.getTrajectory(1);}
    else if (chosenRoutine == threeBall && team == "BLUE"){AutoCommand.getTrajectory(2);}
    else if (chosenRoutine == twoBall && team == "RED"){AutoCommand.getTrajectory(3);}
    else if (chosenRoutine == threeBall && team == "RED"){AutoCommand.getTrajectory(4);}
    else if (chosenRoutine == oneBall && team == "BLUE"){AutoCommand.getTrajectory(5);}
    else if (chosenRoutine == oneBall && team == "RED"){AutoCommand.getTrajectory(6);}
      m_field.getObject("traj").setTrajectory(AutoCommand.trajectory);
      AutoCommand.resetOdometry(AutoCommand.trajectory);
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    climbers.resetEncoders();
    shooterIntake.resetEncoder();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    if (!testComplete){
      //Intake Lift Test
      SmartDashboard.putString("System Testing", "INTAKE LIFT");
      shooterIntake.setIntakeLift(-Constants.INTAKE_LIFT_SPEED);
      Timer.delay(2);
      shooterIntake.setIntakeLift(0);
      Timer.delay(2);
      shooterIntake.setIntakeLift(Constants.INTAKE_LIFT_SPEED);
      Timer.delay(2);
      shooterIntake.setIntakeLift(0);

      //Intake Test
      SmartDashboard.putString("System Testing", "INTAKE");
      shooterIntake.setHorizontalIntake(-Constants.HORIZONTAL_INTAKE_SPEED);
      Timer.delay(1);
      shooterIntake.setHorizontalIntake(0);
      Timer.delay(0.5);
      shooterIntake.setHorizontalIntake(Constants.HORIZONTAL_INTAKE_SPEED);
      Timer.delay(1);
      shooterIntake.setHorizontalIntake(0);

      //Trigger Test
      SmartDashboard.putString("System Testing", "TRIGGER");
      shooterIntake.setVerticalIntake(-Constants.VERTICAL_INTAKE_SPEED);
      Timer.delay(1);
      shooterIntake.setVerticalIntake(0);
      Timer.delay(0.5);
      shooterIntake.setVerticalIntake(Constants.VERTICAL_INTAKE_SPEED);
      Timer.delay(1);
      shooterIntake.setVerticalIntake(0);

      //Shooter Test
      SmartDashboard.putString("System Testing", "SHOOTER");
      shooter.setShooterMotor(-Constants.SHOOTER_IDLE_SPEED);
      Timer.delay(2);
      shooter.setShooterMotor(-Constants.SHOOTER_LOW_SPEED);
      Timer.delay(2);
      shooter.setShooterMotor(-Constants.SHOOTER_MID_SPEED);
      Timer.delay(2);
      shooter.setShooterMotor(-Constants.SHOOTER_HI_SPEED);
      Timer.delay(2);
      shooter.setShooterMotor(0);

      //Climber Rotate Test
      SmartDashboard.putString("System Testing", "CLIMBER ROT");
      climbers.setClimberRotation(Constants.CLIMBER_ROTATION_STATIC);
      Timer.delay(2);
      climbers.setClimberRotation(-Constants.CLIMBER_ROTATION_SPEED);
      Timer.delay(2);
      climbers.setClimberRotation(0);
      Timer.delay(0.5);
      climbers.setClimberRotation(Constants.CLIMBER_ROTATION_SPEED);
      Timer.delay(2);
      climbers.setClimberRotation(0);

      //Climber Rotate Test
      SmartDashboard.putString("System Testing", "CLIMBER EXT");
      climbers.setLeftClimber(1);
      climbers.setRightClimber(1);
      Timer.delay(2);
      climbers.setLeftClimber(-1);
      climbers.setRightClimber(-1);
      Timer.delay(2);
      climbers.setLeftClimber(0);
      climbers.setRightClimber(0);

      //Drivetrain Test
      SmartDashboard.putString("System Testing", "DRIVETRAIN");
      drivetrain.setLeftDrivetrain(Constants.MAX_DRIVE_SPEED);
      Timer.delay(1);
      drivetrain.setRightDrivetrain(Constants.MAX_DRIVE_SPEED);
      drivetrain.setLeftDrivetrain(0);
      Timer.delay(1);
      drivetrain.setRightDrivetrain(0);
      drivetrain.setLeftDrivetrain(-Constants.MAX_DRIVE_SPEED);
      Timer.delay(1);
      drivetrain.setRightDrivetrain(-Constants.MAX_DRIVE_SPEED);
      Timer.delay(1);
      drivetrain.setRightDrivetrain(0);
    }
    SmartDashboard.putString("System Testing", "COMPLETE");
    testComplete = true;
    SmartDashboard.putBoolean("Test Complete", testComplete);
  }
}