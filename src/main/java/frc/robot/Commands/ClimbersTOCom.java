package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Subsystems.Climbers.AutoClimbStep;

public class ClimbersTOCom extends CommandBase{    

    public ClimbersTOCom(){
        addRequirements(Robot.climbers);
    }

    @Override
    public void execute(){    
        double controller1_leftJoystickY = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
        double controller1_rightJoystickY = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
        boolean controller1_leftJoystickButton = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
        boolean controller1_rightJoystickButton = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);

        boolean controller1_rightTrigger = Robot.controller1.getButton(Constants.RIGHT_TRIGGER);
        boolean controller1_rightBumper = Robot.controller1.getButton(Constants.RIGHT_BUMPER);

        boolean controller1_buttonB = Robot.controller1.getButton(Constants.BUTTON_B);
        boolean controller1_BACK = Robot.controller1.getButton(Constants.BUTTON_BACK);

        int val = (int)Math.max(Math.min(Robot.climbers.getLeftClimbEncoder(),255.0),0.0);


        if(controller1_BACK){
            Robot.climbers.resetClimbMode();
            Robot.climbers.autoClimbStep = AutoClimbStep.MANUAL_MODE;
        }

        if(controller1_leftJoystickButton && controller1_rightJoystickButton){
            Robot.climbers.autoClimbStep = AutoClimbStep.MID_BAR_RETRACT;
        }

        switch (Robot.climbers.autoClimbStep){
            case MANUAL_MODE :
                if(controller1_rightTrigger){
                    Robot.climbers.setClimbMode();
                    Robot.climbers.setLeftClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                    Robot.climbers.setRightClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                    Robot.ledStrip.rainbow();
                } else if (controller1_rightBumper) {
                    Robot.climbers.setClimbMode();
                    Robot.climbers.setLeftClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                    Robot.climbers.setRightClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                    Robot.ledStrip.rainbow();
                } else if (Robot.climbers.getClimbMode()){
                    Robot.climbers.setLeftClimber(controller1_leftJoystickY);
                    Robot.climbers.setRightClimber(controller1_rightJoystickY);
                    Robot.ledStrip.rainbow();
                } else {
                    Robot.climbers.setLeftClimber(0);
                    Robot.climbers.setRightClimber(0);
                }
            

                if (controller1_buttonB){
                    Robot.climbers.setClimberRotation(Constants.RotatorFullPositionDeg);
                } else if(!controller1_rightBumper){
                    Robot.climbers.setClimberRotation(Constants.RotatorVerticalPositionDeg);
                }
                break;
            case MID_BAR_RETRACT :
                Robot.ledStrip.varSat(255,val);
                Robot.climbers.setClimberRotation(Constants.RotatorVerticalPositionDeg);
                Robot.climbers.setLeftClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() < 3 && Robot.climbers.getRightClimbEncoder() < 3){
                    Robot.climbers.autoClimbStep = AutoClimbStep.MID_BAR_RELEASE;
                }
                break;
            case MID_BAR_RELEASE :
                Robot.ledStrip.varSat(255,val);
                Robot.climbers.setClimberRotation(Constants.RotatorUnhookPositionDeg);
                Robot.climbers.setLeftClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() > 100 && Robot.climbers.getRightClimbEncoder() > 100){
                    Robot.climbers.autoClimbStep = AutoClimbStep.HIGH_BAR_EXTEND;
                }
                break;
            case HIGH_BAR_EXTEND :
                Robot.ledStrip.varSat(255,val);
                Robot.climbers.setClimberRotation(Constants.RotatorFullPositionDeg);
                Robot.climbers.setLeftClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() > 272 && Robot.climbers.getRightClimbEncoder() > 272){
                    Robot.climbers.autoClimbStep = AutoClimbStep.HIGH_BAR_RETRACT;
                }
                break;
            case HIGH_BAR_RETRACT :
                Robot.ledStrip.varSat(255,val);
                Robot.climbers.setClimberRotation(Constants.RotatorVerticalPositionDeg);
                Robot.climbers.setLeftClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() < 3 && Robot.climbers.getRightClimbEncoder() < 3){
                    Robot.climbers.autoClimbStep = AutoClimbStep.HIGH_BAR_RELEASE;
                }
                break;
            case HIGH_BAR_RELEASE :
                Robot.ledStrip.varSat(255-val,val);
                Robot.climbers.setClimberRotation(Constants.RotatorUnhookPositionDeg);
                Robot.climbers.setLeftClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() > 100 && Robot.climbers.getRightClimbEncoder() > 100){
                    Robot.climbers.autoClimbStep = AutoClimbStep.TRAVERSAL_BAR_EXTEND;
                }
                break;
            case TRAVERSAL_BAR_EXTEND :
                Robot.ledStrip.varSat(255-val,val);
                Robot.climbers.setClimberRotation(Constants.RotatorFullPositionDeg);
                Robot.climbers.setLeftClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(-Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() > 272 && Robot.climbers.getRightClimbEncoder() > 272){
                    Robot.climbers.autoClimbStep = AutoClimbStep.TRAVERSAL_BAR_RETRACT;
                }
                break;
            case TRAVERSAL_BAR_RETRACT :
                Robot.ledStrip.varRainbow(255-val);
                Robot.climbers.setClimberRotation(Constants.RotatorVerticalPositionDeg);
                Robot.climbers.setLeftClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                Robot.climbers.setRightClimber(Constants.CLIMBER_MOVEMENT_SPEED);
                if (Robot.climbers.getLeftClimbEncoder() < 3 && Robot.climbers.getRightClimbEncoder() < 3){
                    Robot.climbers.autoClimbStep = AutoClimbStep.CLIMB_COMPLETE;
                }
                break;
            case CLIMB_COMPLETE : 
                Robot.ledStrip.rainbow();
                Robot.climbers.setLeftClimber(0);
                Robot.climbers.setRightClimber(0);
                Robot.climbers.setClimberRotation(Constants.RotatorVerticalPositionDeg);
                break;
        }
        Robot.climbers.updateDashboard();
    }
}