package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Commands.ShooterTOCom;

public class Shooter extends SubsystemBase{
    private CANSparkMax shooter;
    private static RelativeEncoder shooterEncoder;

    public Shooter(int shoot){
        shooter = new CANSparkMax(shoot, MotorType.kBrushless);
        shooterEncoder = shooter.getEncoder();
    }

    public void setShooterMotor(double speed){
        shooter.setVoltage(-speed);
    }

    public double shooterSpeedAdjust(double distance){
        double outputVoltage = (4-Math.sqrt(16+0.8*(-3.5-distance)))/0.4;
        if (Double.isNaN(outputVoltage)){
            outputVoltage = Constants.SHOOTER_IDLE_SPEED;
            Robot.ledStrip.solid(15);
        }
        return outputVoltage;
    }

    public void updateDashboard(){
        SmartDashboard.putNumber("Shooter Speed ", shooterEncoder.getVelocity());
    } 

    @Override
    public void periodic(){
        setDefaultCommand(new ShooterTOCom());
    }
}