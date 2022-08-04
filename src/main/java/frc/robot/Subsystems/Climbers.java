package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Commands.ClimbersTOCom;

public class Climbers extends SubsystemBase{
    private CANSparkMax leftClimber0;
    private CANSparkMax leftClimber1;
    private CANSparkMax leftClimberRotate;
    private CANSparkMax rightClimber0;
    private CANSparkMax rightClimber1;
    private CANSparkMax rightClimberRotate;
    private RelativeEncoder climberEncoderRight;
    private RelativeEncoder rightRotateEncoder;
    private RelativeEncoder climberEncoderLeft;
    private RelativeEncoder leftRotateEncoder;
    private boolean climbMode = false;
    public final PIDController L_controller = new PIDController(Constants.kLArmKp, 0, 0);
    public final PIDController R_controller = new PIDController(Constants.kRArmKp, 0, 0);


    public Climbers(int climberL0, int climberL1, int climberLR, int climberR0, int climberR1, int climberRR) {
        leftClimber0 = new CANSparkMax(climberL0, MotorType.kBrushless);
        leftClimber1 = new CANSparkMax(climberL1, MotorType.kBrushless);
        leftClimberRotate = new CANSparkMax(climberLR, MotorType.kBrushless);
        rightClimber0 = new CANSparkMax(climberR0, MotorType.kBrushless);
        rightClimber1 = new CANSparkMax(climberR1, MotorType.kBrushless);
        rightClimberRotate = new CANSparkMax(climberRR, MotorType.kBrushless);
        
        climberEncoderLeft = leftClimber0.getEncoder();
        climberEncoderRight = rightClimber0.getEncoder();
        rightRotateEncoder = rightClimberRotate.getEncoder();
        leftRotateEncoder = leftClimberRotate.getEncoder();

        // leftClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward,true);
        // leftClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,true);
        // rightClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward,true);
        // rightClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,true);

        leftClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        leftClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        rightClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        rightClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        leftClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        leftClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        rightClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        rightClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        // leftClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float) -4);
        // rightClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) 4);

        leftClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 270);
        leftClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 2);
        rightClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 270);
        rightClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 2);
        leftClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 270);
        leftClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 2);
        rightClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 270);
        rightClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 2);
    }

    public void setLeftClimber(double speed){
        leftClimber0.set(-speed);
        leftClimber1.set(-speed);
    }

    public void setRightClimber(double speed){
        rightClimber0.set(-speed);
        rightClimber1.set(-speed);
    }

    public void setLeftClimberRotation(double voltage){
        leftClimberRotate.setVoltage(voltage);
    }

    public void setRightClimberRotation(double voltage){
        rightClimberRotate.setVoltage(voltage);
    }

    public void setClimbMode(){
        climbMode = true;
    }

    public void resetClimbMode(){
        climbMode = false;
    }

    public boolean getClimbMode(){
        return climbMode;
    }

    public void resetEncoders(){
        climberEncoderLeft.setPosition(0);
        climberEncoderRight.setPosition(0);
        rightRotateEncoder.setPosition(0);
        leftRotateEncoder.setPosition(0);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Right Climber Position ", climberEncoderRight.getPosition());
        SmartDashboard.putNumber("Left Climber Position ", climberEncoderLeft.getPosition());
        SmartDashboard.putNumber("Left Rotator Position ", -leftRotateEncoder.getPosition());
        SmartDashboard.putNumber("Right Rotator Position ", rightRotateEncoder.getPosition());
        SmartDashboard.putBoolean("Climber Mode", climbMode);
    }

    public double getRightEncoder(){
        return rightRotateEncoder.getPosition();
    }

    public double getLeftEncoder(){
        return leftRotateEncoder.getPosition();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new ClimbersTOCom());
    }
}