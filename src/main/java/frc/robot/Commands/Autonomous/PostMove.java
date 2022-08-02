package frc.robot.Commands.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

public class PostMove extends SequentialCommandGroup {
    
    public PostMove(String mode){
        if (mode == "One Ball Auto"){
            Timer.delay(5);
            SmartDashboard.putString("Auto Step", "Run Intake");
            AutoCommand.runIntake(-Constants.HORIZONTAL_INTAKE_SPEED);
            Timer.delay(1);
            SmartDashboard.putString("Auto Step", "Rotate");
            AutoCommand.rotate(1);
        } else if (mode == "Two Ball Auto"){
            Timer.delay(5);
            SmartDashboard.putString("Auto Step", "Stop Intake");
            AutoCommand.runIntake(0);
            SmartDashboard.putString("Auto Step", "Shooting");
            AutoCommand.limelightShoot(Constants.SHOOTER_LOW_SPEED);
        } else if (mode == "Three Ball Auto") {
            Timer.delay(5);
            SmartDashboard.putString("Auto Step", "Stop Intake");
            AutoCommand.runIntake(0);
            SmartDashboard.putString("Auto Step", "Shooting");
            AutoCommand.limelightShoot(Constants.SHOOTER_LOW_SPEED);
        }
    }
}