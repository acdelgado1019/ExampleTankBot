package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class TwoBallAuto extends SequentialCommandGroup {
    
    public TwoBallAuto(){
        String team = Robot.t_chooser.getSelected();
        int path=0;
        if (team == "RED"){path = 3;}
        else if (team == "BLUE"){path = 1;};
        
        AutoCommand.lowerIntake();
        AutoCommand.runIntake(0.8);
        AutoCommand.runRamsete(path);
        AutoCommand.runIntake(0);
        AutoCommand.limelightShoot(.6);
    }
}
