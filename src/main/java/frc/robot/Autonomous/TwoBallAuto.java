package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;

public class TwoBallAuto extends SequentialCommandGroup {
    
    public TwoBallAuto(){
        String team = Robot.t_chooser.getSelected();
        int path=0;
        if (team == "RED"){path = 3;}
        else if (team == "BLUE"){path = 1;};
        
        AutoCommand.lowerIntake();
        AutoCommand.runIntake(Constants.HORIZONTAL_INTAKE_SPEED);
        AutoCommand.runRamsete(path);
        AutoCommand.runIntake(0);
        AutoCommand.limelightShoot(Constants.SHOOTER_LOW_SPEED);
    }
}
