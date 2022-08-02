package frc.robot.Commands.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

public class PreMove extends SequentialCommandGroup {
    
    public PreMove(String mode){
        if (mode == "One Ball Auto"){
            SmartDashboard.putString("Auto Step", "Delay");
            Timer.delay(0);
            SmartDashboard.putString("Auto Step", "Intake Down");
            AutoCommand.lowerIntake();
            SmartDashboard.putString("Auto Step", "Shooting");
            AutoCommand.limelightShoot(Constants.SHOOTER_HI_SPEED);
            SmartDashboard.putString("Auto Step", "Run Away");
        } else if (mode == "Two Ball Auto"){
            SmartDashboard.putString("Auto Step", "Intake Down");
            AutoCommand.lowerIntake();
            SmartDashboard.putString("Auto Step", "Run Intake");
            AutoCommand.runIntake(Constants.HORIZONTAL_INTAKE_SPEED);
            SmartDashboard.putString("Auto Step", "Collecting");
        } else if (mode == "Three Ball Auto") {
            SmartDashboard.putString("Auto Step", "Shooting");
            AutoCommand.limelightShoot(Constants.SHOOTER_HI_SPEED);
            SmartDashboard.putString("Auto Step", "Intake Down");
            AutoCommand.lowerIntake();
            SmartDashboard.putString("Auto Step", "Run Intake");
            AutoCommand.runIntake(Constants.HORIZONTAL_INTAKE_SPEED);
            SmartDashboard.putString("Auto Step", "Collecting");
        }
    }
}