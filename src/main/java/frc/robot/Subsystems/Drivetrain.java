package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Commands.DrivetrainTOCom;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase{
    private CANSparkMax motorLeft0;
    private CANSparkMax motorLeft1;
    private CANSparkMax motorRight0;
    private CANSparkMax motorRight1;
    public static RelativeEncoder rightDrivetrain;
    public static RelativeEncoder leftDrivetrain;   
    private final MotorControllerGroup m_leftMotors;
    private final MotorControllerGroup m_rightMotors;
    public final DifferentialDrive m_drive;

    public static AHRS gyro = new AHRS(SerialPort.Port.kUSB);

    public DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.kTrackwidthMeters);
    public static DifferentialDriveOdometry odometry;

    public double initPose = 0.0;

    public Drivetrain (int l0, int l1, int r0, int r1){
        motorLeft0 = new CANSparkMax(l0, MotorType.kBrushless);
        motorLeft1 = new CANSparkMax(l1, MotorType.kBrushless);
        motorRight0 = new CANSparkMax(r0, MotorType.kBrushless);
        motorRight1 = new CANSparkMax(r1, MotorType.kBrushless);

        m_leftMotors = new MotorControllerGroup(motorLeft0,motorLeft1);
        m_rightMotors = new MotorControllerGroup(motorRight0,motorRight1); 
        m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

        m_leftMotors.setInverted(true);

        leftDrivetrain = motorLeft0.getEncoder();
        rightDrivetrain = motorRight0.getEncoder();
        rightDrivetrain.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
        leftDrivetrain.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);

        resetEncoders();
        odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    @Override
    public void periodic(){
        odometry.update(
            gyro.getRotation2d(), 
            -leftDrivetrain.getPosition(), 
            rightDrivetrain.getPosition()
        );
        setDefaultCommand(new DrivetrainTOCom());
    }

    public Pose2d getPose(){
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        return new DifferentialDriveWheelSpeeds(
            leftDrivetrain.getVelocity(), 
            rightDrivetrain.getVelocity()
        ); 
    }


    // Reset odometry to the starting pose of the trajectory.
    public static void resetOdometry(Trajectory trajectory){
        resetEncoders();
        odometry.resetPosition(trajectory.getInitialPose(), gyro.getRotation2d());
    }

    public void tankDriveVolts(double leftVolts, double rightVolts){
        var batteryVoltage = RobotController.getBatteryVoltage();
        if (Math.max(Math.abs(leftVolts), Math.abs(rightVolts)) > batteryVoltage) {
          leftVolts *= batteryVoltage / 12.0;
          rightVolts *= batteryVoltage / 12.0;
        }
        m_leftMotors.setVoltage(leftVolts);
        m_rightMotors.setVoltage(rightVolts);
        m_drive.feed();
    }

    public static void resetEncoders() {
        rightDrivetrain.setPosition(0);
        leftDrivetrain.setPosition(0);
    }

    public double getAverageEncoderDistance() {
        return (leftDrivetrain.getPosition() + rightDrivetrain.getPosition()) / 2.0;
    }

    public void setMaxOutput(double maxOutput) {
        m_drive.setMaxOutput(maxOutput);
    }

    public void zeroHeading(){
        gyro.reset();
    }

    public double getHeading(){
        return -gyro.getRotation2d().getDegrees();
    }

    public double getNormHeading(){
        double heading = 0.0;
        if((getHeading()+ initPose) % 360 > -180 && (getHeading()+ initPose) % 360 < 180){
            heading = (getHeading()+ initPose)%360;
        } else if((getHeading()+ initPose) % 360 < -180){
            heading = 360 + (getHeading()+ initPose)%360;
        } else if((getHeading()+ initPose) % 360 > 180){
            heading = -360 + (getHeading()+ initPose)%360;
        }
        return heading;
    }
}