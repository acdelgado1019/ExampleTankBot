package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;

public class OneBallAuto extends SequentialCommandGroup {
    
    public OneBallAuto(){
        String team = Robot.t_chooser.getSelected();
        int path=0;
        if (team == "RED"){path = 6;}
        else if (team == "BLUE"){path = 5;};

        AutoCommand.delay(0);
        AutoCommand.lowerIntake();
        AutoCommand.limelightShoot(.8);
        AutoCommand.runRamsete(path);
        AutoCommand.runIntake(-Constants.HORIZONTAL_INTAKE_SPEED);
        // AutoCommand.rotate(-90);
    }
}