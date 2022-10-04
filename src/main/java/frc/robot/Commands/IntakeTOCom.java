package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.PlayerConfigs;
import frc.robot.Robot;

public class IntakeTOCom extends CommandBase{

    public IntakeTOCom(){
        addRequirements(Robot.intake);
    }

    @Override
    public void execute(){
        Robot.intake.setHorizontalIntake(PlayerConfigs.acceptIntake ? Constants.HORIZONTAL_INTAKE_SPEED : (PlayerConfigs.rejectIntake ? -Constants.HORIZONTAL_INTAKE_SPEED : 0));
        SmartDashboard.putNumber("Intake Lift", Robot.intake.getEncoder());

        Robot.intake.setIntakeLift(PlayerConfigs.intakeLiftHi ? Constants.hiILPositionDeg : (PlayerConfigs.acceptIntake ? Constants.loILPositionDeg : Constants.midILPositionDeg));
    }
}
