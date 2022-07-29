package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ShooterCom extends CommandBase{

    public ShooterCom(){
        addRequirements(Robot.shooter);
    }

    @Override
    public void execute(){
        boolean controller1_buttonA = Robot.controller1.getButton(Constants.BUTTON_A);
        boolean controller1_buttonB = Robot.controller1.getButton(Constants.BUTTON_B);
        boolean controller1_buttonX = Robot.controller1.getButton(Constants.BUTTON_X);
        boolean controller1_buttonY = Robot.controller1.getButton(Constants.BUTTON_Y);

        if(controller1_buttonA == true){
            Robot.shooter.limelightShoot(Constants.SHOOTER_LOW_SPEED);
        }else if(controller1_buttonB == true){
            Robot.shooter.setShooterMotor(Constants.SHOOTER_HI_SPEED);
        }else if(controller1_buttonX == true){
            Robot.shooter.setShooterMotor(Constants.SHOOTER_MID_SPEED);
        }else if(controller1_buttonY == true){
            Robot.shooter.setShooterMotor(Constants.SHOOTER_LOW_SPEED);
        }else{
            Robot.shooter.setShooterMotor(Constants.SHOOTER_IDLE_SPEED);
        }
        Robot.shooter.updateDashboard();
    }
}