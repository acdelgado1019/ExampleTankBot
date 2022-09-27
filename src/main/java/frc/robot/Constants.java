package frc.robot;

import edu.wpi.first.math.util.Units;

public class Constants {
    //Motor IDs -> VIEWING FROM BEHIND ROBOT
        //Drivetrain
        public static final int LEFT_DRIVE_TRAIN_0 = 20;    public static final int RIGHT_DRIVE_TRAIN_0 = 1;
        public static final int LEFT_DRIVE_TRAIN_1 = 19;    public static final int RIGHT_DRIVE_TRAIN_1 = 2;
        
        //Climbers
        public static final int LEFT_CLIMBER_0 = 12;        public static final int RIGHT_CLIMBER_0 = 11;
        public static final int LEFT_CLIMBER_1 = 9;        public static final int RIGHT_CLIMBER_1 = 10;
        public static final int LEFT_CLIMBER_ROTATE = 18;   public static final int RIGHT_CLIMBER_ROTATE = 3;
        
        //GAP = 15                                          //GAP = 6
        //GAP = 14                                          //GAP = 7
        //GAP = 13                                          //GAP = 8

        //Intake & Shooter
        public static final int SHOOTER = 15;               public static final int HORIZONTAL_INTAKE = 17;      
        public static final int TRIGGER = 16;               public static final int INTAKE_LIFT = 5;           

    //Controller Assignments
        public static final int DRIVER_CONTROLLER_0 = 0;
        public static final int DRIVER_CONTROLLER_1 = 1;
        
        //Control Axis
        public static final int LEFT_STICK_X = 0;
        public static final int LEFT_STICK_Y = 1;
        public static final int LEFT_TRIGGER = 2;
        public static final int RIGHT_TRIGGER = 3;
        public static final int RIGHT_STICK_X = 4;
        public static final int RIGHT_STICK_Y = 5;

        //Control D-Pad
        public static final int DPAD_X = 2;
        public static final int DPAD_Y = 3;

        //Control Buttons
        public static final int BUTTON_A = 1;
        public static final int BUTTON_B = 2;
        public static final int BUTTON_X = 3;
        public static final int BUTTON_Y = 4;
        public static final int LEFT_BUMPER = 5;
        public static final int RIGHT_BUMPER = 6;
        public static final int BUTTON_BACK = 7;
        public static final int BUTTON_START = 8;
        public static final int LEFT_JOYSTICK_BUTTON = 9;
        public static final int RIGHT_JOYSTICK_BUTTON = 10;

    //FMS Data
        public static String teamColor;

    //Speed Variables
        public static final double CLIMBER_ROTATION_SPEED = .3;
        public static final double CLIMBER_MOVEMENT_SPEED = -1;
        public static final double LEFT_RIGHT_TRIM = 0; // Min = -1, Max = 1;+
        public static final double INTAKE_LIFT_SPEED = .3;
        public static final double HORIZONTAL_INTAKE_SPEED = -.8;
        public static final double TRIGGER_SPEED = -1;
        public static final double SHOOTER_HI_SPEED = 12;
        public static final double SHOOTER_MID_SPEED = 10;
        public static final double SHOOTER_LOW_SPEED = 7.2;
        public static final double SHOOTER_IDLE_SPEED = 3;
        public static final double ROTATE_KP = 0.0017;
        public static final double ROTATE_FF = 0.02;

    //Characterization Constants
        public static final double ksVolts = 0.63969;
        public static final double kvVoltSecondsPerMeter = 0.34464;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;
        public static final double kPDriveVel = 3.9966;
        public static final double kTrackwidthMeters = Units.inchesToMeters(19.25);
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final double kEncoderDistancePerPulse = Math.PI * Units.inchesToMeters(4) / 3;

    //Limelight Distance Constants
        public static final double camHeight = 40;
        public static final double goalHeight = 102.5;
        public static final double camAngle = 15;

    //Climber Constants
        public static final double m_armReduction = 60;
        public static final double kRotatorEncoderDistPerPulse = 360 / m_armReduction;
        public static final double kClimberEncoderDistPerPulse =  1/8*48/64;

        public static double kLRotatorKp = 1;
        public static double kRRotatorKp = 1;
        public static double RotatorFullPositionDeg = 21.0;
        public static double RotatorUnhookPositionDeg = 1.0;
        public static double RotatorVerticalPositionDeg = 0.0;

    //Intake Lift Constants
        public static final double m_IntakeLiftReduction = 125;
        public static final double kIntakeLiftEncoderDistPerRot = 360 / m_IntakeLiftReduction;

        public static double kIntakeLiftKp = 0.09;
        public static double hiILPositionDeg = 20;
        public static double midILPositionDeg = 30;
        public static double loILPositionDeg = 80;
}
