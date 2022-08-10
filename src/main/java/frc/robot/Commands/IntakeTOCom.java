package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class IntakeTOCom extends CommandBase{

    public IntakeTOCom(){
        addRequirements(Robot.shooterIntake);
    }

    @Override
    public void execute(){
        //lift
        boolean controller0_leftBumper = Robot.controller0.getButton(Constants.LEFT_BUMPER);
        boolean controller0_leftTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);

        //horizontal
        boolean controller0_rightBumper = Robot.controller0.getButton(Constants.RIGHT_BUMPER);
        boolean controller0_rightTrigger = Robot.controller0.getTrigger(Constants.RIGHT_TRIGGER);
        
        //vertical
        boolean controller1_leftBumper = Robot.controller1.getButton(Constants.LEFT_BUMPER);
        boolean controller1_leftTrigger = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

        Robot.shooterIntake.setHorizontalIntake(controller0_leftTrigger ? Constants.HORIZONTAL_INTAKE_SPEED : (controller0_leftBumper ? -Constants.HORIZONTAL_INTAKE_SPEED : 0));
        
        if (controller0_rightBumper){
            var pidOutput =
                Robot.shooterIntake.Lift_controller.calculate(Robot.shooterIntake.getEncoder(), Constants.hiILPositionDeg);
            Robot.shooterIntake.setIntakeLift(pidOutput);
        } else if(controller0_rightTrigger){
            var pidOutput =
                Robot.shooterIntake.Lift_controller.calculate(Robot.shooterIntake.getEncoder(), Constants.midILPositionDeg);
            Robot.shooterIntake.setIntakeLift(pidOutput);
        } else{
            var pidOutput =
                Robot.shooterIntake.Lift_controller.calculate(Robot.shooterIntake.getEncoder(), Constants.loILPositionDeg);
            Robot.shooterIntake.setIntakeLift(pidOutput);
        }   

        if (controller1_leftBumper){
            Robot.shooterIntake.pulse();
        } else if (controller1_leftTrigger){
            Robot.shooterIntake.setTrigger(-Constants.TRIGGER_SPEED);
        } else if (!Robot.climbers.getClimbMode()){
            Robot.shooterIntake.setTrigger(0);
            Robot.shooterIntake.stopPulse();
            Robot.ledStrip.teamColor(Constants.teamColor);
        }
    }
}
