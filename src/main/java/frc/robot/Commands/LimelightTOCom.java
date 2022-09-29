package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;

public class LimelightTOCom extends CommandBase{

    private boolean prev_StartButton = false;

    public LimelightTOCom(){
        addRequirements(Robot.limelight);
    }

    @Override
    public void execute(){
        if(PlayerConfigs.autoTarget || PlayerConfigs.lowPowerShooter || 
           PlayerConfigs.midPowerShooter || PlayerConfigs.highPowerShooter){
            Robot.limelight.setLED(3);
        } else{
            Robot.limelight.setLED(0);
        }

        Robot.limelight.updateData();
        Robot.limelight.getRange();
        Robot.limelight.getDistance();
        
        if(PlayerConfigs.switchPipeline != prev_StartButton){
            prev_StartButton = PlayerConfigs.switchPipeline;
            if(PlayerConfigs.switchPipeline){
                Robot.limelight.switchCameraMode();
            }
        }
        
    }
}