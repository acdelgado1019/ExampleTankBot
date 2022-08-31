package frc.robot.Commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;

public class DrivetrainTOCom extends CommandBase{

    public DrivetrainTOCom() {
        addRequirements(Robot.drivetrain);
    }

    @Override
    public void execute(){
        double leftMotorSet = 0;
        double rightMotorSet = 0;

        // Calculate values to set motors
        leftMotorSet = ((PlayerConfigs.accelerator - (Constants.LEFT_RIGHT_TRIM + (PlayerConfigs.steering * Constants.MAX_TURN_SPEED))) * Constants.MAX_DRIVE_SPEED);
        rightMotorSet = ((PlayerConfigs.accelerator + (Constants.LEFT_RIGHT_TRIM + (PlayerConfigs.steering * Constants.MAX_TURN_SPEED))) * Constants.MAX_DRIVE_SPEED);

        //Set motors
        if(PlayerConfigs.autoTarget){Robot.drivetrain.hubTrack();
        } else {
            Robot.drivetrain.setLeftDrivetrain(leftMotorSet);
            Robot.drivetrain.setRightDrivetrain(rightMotorSet);
        }
    }
}