package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class ThreeBallAuto extends SequentialCommandGroup {
    
    public ThreeBallAuto(){
        String team = Robot.t_chooser.getSelected();
        int path=0;
        if (team == "RED"){path = 4;}
        else if (team == "BLUE"){path = 2;};
        
        AutoCommand.limelightShoot(1);
        AutoCommand.lowerIntake();
        AutoCommand.runIntake(0.8);
        AutoCommand.runRamsete(path);
        AutoCommand.runIntake(0);
        AutoCommand.limelightShoot(.6);
    }
}