package frc.robot.Commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;

public class DrivetrainTOCom extends CommandBase{

    private double leftMotorSet = 0;
    private double rightMotorSet = 0;

    public DrivetrainTOCom() {
        addRequirements(Robot.drivetrain);
    }

    @Override
    public void execute(){
        // Calculate values to set motors
        leftMotorSet = PlayerConfigs.driveSpeed * (PlayerConfigs.accelerator + (PlayerConfigs.steering * PlayerConfigs.turnSpeed));
        rightMotorSet = Constants.LEFT_RIGHT_TRIM * PlayerConfigs.driveSpeed * (PlayerConfigs.accelerator - (PlayerConfigs.steering * PlayerConfigs.turnSpeed));

        //Set motors
        Robot.drivetrain.tankDriveVolts(leftMotorSet*12,rightMotorSet*12);
    }
}