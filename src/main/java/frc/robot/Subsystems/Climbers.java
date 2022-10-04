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
    public RelativeEncoder climberEncoderRight;
    public RelativeEncoder rightRotateEncoder;
    public RelativeEncoder climberEncoderLeft;
    public RelativeEncoder leftRotateEncoder;
    private boolean climbMode = false;
    public final PIDController L_controller = new PIDController(Constants.kLRotatorKp, 0, 0);
    public final PIDController R_controller = new PIDController(Constants.kRRotatorKp, 0, 0);

    public enum AutoClimbStep{
        MANUAL_MODE,
        MID_BAR_RETRACT,
        MID_BAR_RELEASE,
        HIGH_BAR_EXTEND,
        HIGH_BAR_RETRACT,
        HIGH_BAR_RELEASE,
        TRAVERSAL_BAR_EXTEND,
        TRAVERSAL_BAR_RETRACT,
        CLIMB_COMPLETE
    }

    public AutoClimbStep autoClimbStep;

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

        rightRotateEncoder.setPositionConversionFactor(Constants.kRotatorEncoderDistPerPulse);
        leftRotateEncoder.setPositionConversionFactor(Constants.kRotatorEncoderDistPerPulse);  
        climberEncoderRight.setPositionConversionFactor(Constants.kClimberEncoderDistPerPulse);
        climberEncoderLeft.setPositionConversionFactor(Constants.kClimberEncoderDistPerPulse); 

        leftClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward,true);
        leftClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,true);
        rightClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward,true);
        rightClimberRotate.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,true);
        leftClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) 0);
        leftClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float) -25);
        rightClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) 25);
        rightClimberRotate.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float) 0);

        leftClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        leftClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        rightClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        rightClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        leftClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        leftClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        rightClimber0.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        rightClimber1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        leftClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)25.3);
        leftClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float)0.5);
        rightClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)25.3);
        rightClimber0.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float)0.5);
        leftClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)25.3);
        leftClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float)0.5);
        rightClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)25.3);
        rightClimber1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float)0.5);
    }

    public void setLeftClimber(double speed){
        leftClimber0.set(speed);
        leftClimber1.set(speed);
    }

    public void setRightClimber(double speed){
        rightClimber0.set(speed);
        rightClimber1.set(speed);
    }

    public void setClimberRotation(double setpoint){
        var lPIDOutput = L_controller.calculate(getLeftRotEncoder(), -setpoint);
        setLeftClimberRotation(lPIDOutput);
        var rPIDOutput = R_controller.calculate(getRightRotEncoder(), setpoint);
        setRightClimberRotation(rPIDOutput);
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
        SmartDashboard.putNumber("Right Climber", getRightClimbEncoder());
        SmartDashboard.putNumber("Left Climber", getLeftClimbEncoder());
        SmartDashboard.putBoolean("Climber Mode", climbMode);
    }

    public double getRightClimbEncoder(){
        return climberEncoderRight.getPosition();
    }

    public double getLeftClimbEncoder(){
        return climberEncoderLeft.getPosition();
    }

    public double getRightRotEncoder(){
        return rightRotateEncoder.getPosition();
    }

    public double getLeftRotEncoder(){
        return leftRotateEncoder.getPosition();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new ClimbersTOCom());
    }
}