package frc.robot.Commands.Autonomous;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoCommands {
    public static Trajectory trajectoryA;

    public static Command drivetrainMotion(Trajectory trajectory){
        RamseteCommand ramseteCommand =
        new RamseteCommand(
            trajectory,
            Robot.drivetrain::getPose,
            new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.ksVolts,
                Constants.kvVoltSecondsPerMeter,
                Constants.kaVoltSecondsSquaredPerMeter),
            Robot.drivetrain.kinematics,
            Robot.drivetrain::getWheelSpeeds,
            new PIDController(Constants.kPDriveVel, 0, 0),
            new PIDController(Constants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            Robot.drivetrain::tankDriveVolts,
            Robot.drivetrain);
            return ramseteCommand.andThen(() -> Robot.drivetrain.tankDriveVolts(0, 0));
    }
}
