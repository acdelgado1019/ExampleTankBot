// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.AutoMethods;
import frc.robot.Subsystems.Climbers;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.Shooter;
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
  
  public static final Intake shooterIntake = new Intake(
    Constants.HORIZONTAL_INTAKE,
    Constants.TRIGGER,
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

  public static final LEDs ledStrip = new LEDs(0,10);

  //Controllers
  public static final Controller controller0 = new Controller(Constants.DRIVER_CONTROLLER_0);
  public static final Controller controller1 = new Controller(Constants.DRIVER_CONTROLLER_1);

  //Auto Commands
  public static String autoSequence;
  public static String team;
  public static int path = 0;
  public static String prevTeam;
  public static int prevPath = 0;
  private final String oneBall = "One Ball Auto";
  private final String twoBall = "Two Ball Auto";
  private final String threeBall = "Three Ball Auto";
  public Boolean preMoveMode;
  public Boolean moveMode;
  public Boolean postMoveMode;
  public double timeCheck;
  
  public static SendableChooser<String> m_chooser = new SendableChooser<>();
  public static SendableChooser<String> t_chooser = new SendableChooser<>();

  //Field display to Shuffleboard
  public static Field2d m_field;
  public static Field2d logo;

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
    SmartDashboard.putString("Auto Step", "NOT STARTED");

    AutoMethods.getConstraint();
    AutoMethods.getTrajectoryConfig();
      
    // Create and push Field2d to SmartDashboard.
    m_field = new Field2d();

    SmartDashboard.putData(m_field);
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
    Constants.teamColor = DriverStation.getAlliance().toString();
    ledStrip.stripeRB();
    climbers.resetEncoders();
    shooterIntake.resetEncoder();
    preMoveMode = true;
    moveMode = false;
    postMoveMode = false;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    climbers.setLeftClimberRotation(-0.02);
    climbers.setRightClimberRotation(0.02);
    if (preMoveMode){
      if (autoSequence == "One Ball Auto"){
        Timer.delay(0);
        AutoMethods.lowerIntake();
        AutoMethods.limelightShoot();
      } else if (autoSequence == "Two Ball Auto"){
        AutoMethods.lowerIntake();
        AutoMethods.runIntake(Constants.HORIZONTAL_INTAKE_SPEED);
      } else if (autoSequence == "Three Ball Auto") {
        AutoMethods.lowerIntake();
        AutoMethods.limelightShoot();
        AutoMethods.runIntake(Constants.HORIZONTAL_INTAKE_SPEED);
      }
      moveMode = true;
      preMoveMode = false;
    } else if (moveMode){
      AutoMethods.runRamsete(path).schedule();
      postMoveMode = true;
      moveMode = false;
      timeCheck = Timer.getFPGATimestamp();
    } else if (postMoveMode){
      if (autoSequence == "One Ball Auto"){
        if(Timer.getFPGATimestamp() - timeCheck > 3){
          AutoMethods.runIntake(-Constants.HORIZONTAL_INTAKE_SPEED);
          postMoveMode = false;
        }
      } else if (autoSequence == "Two Ball Auto"){
        if(Timer.getFPGATimestamp() - timeCheck > 3){
          AutoMethods.runIntake(0);
          AutoMethods.limelightShoot();
          postMoveMode = false;
        }
      } else if (autoSequence == "Three Ball Auto") {
        if(Timer.getFPGATimestamp() - timeCheck > 4){
          AutoMethods.runIntake(0);
          AutoMethods.limelightShoot();
          postMoveMode = false;
        }
      }
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {Constants.teamColor = DriverStation.getAlliance().toString();}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    autoSequence = m_chooser.getSelected();
    team = Robot.t_chooser.getSelected();

    if (team == "RED" && autoSequence == "One Ball Auto"){path = 6;}
    else if (team == "BLUE" && autoSequence == "One Ball Auto"){path = 5;}
    else if (team == "RED" && autoSequence == "Three Ball Auto"){path = 4;}
    else if (team == "RED" && autoSequence == "Two Ball Auto"){path = 3;}
    else if (team == "BLUE" && autoSequence == "Three Ball Auto"){path = 2;}
    else if (team == "BLUE" && autoSequence == "Two Ball Auto"){path = 1;}
    
    if (prevPath!=path || prevTeam != team){
      AutoMethods.getTrajectory(path);
      m_field.getObject("traj").setTrajectory(AutoMethods.trajectory);
      AutoMethods.resetOdometry(AutoMethods.trajectory);
      prevPath = path;
      prevTeam = team;
    }
    ledStrip.mardiGras();
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
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
