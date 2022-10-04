package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;
import frc.robot.Robot.AutoSection;

public class IntakeTOCom extends CommandBase{

    public IntakeTOCom(){
        addRequirements(Robot.intake);
    }

    @Override
    public void execute(){
        if (Robot.autoSection == AutoSection.EXIT_AUTO){
            Robot.intake.setHorizontalIntake(PlayerConfigs.acceptIntake ? Constants.HORIZONTAL_INTAKE_SPEED : (PlayerConfigs.rejectIntake ? -Constants.HORIZONTAL_INTAKE_SPEED : 0));
        }
        Robot.intake.setIntakeLift(PlayerConfigs.intakeLiftHi ? Constants.hiILPositionDeg : ((PlayerConfigs.acceptIntake || Robot.autoSection!=AutoSection.EXIT_AUTO) ? Constants.loILPositionDeg : Constants.midILPositionDeg));
    }
}
