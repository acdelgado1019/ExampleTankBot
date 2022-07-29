package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Commands.ShooterCom;

public class Shooter extends SubsystemBase{
    public CANSparkMax shooter;
    private static RelativeEncoder shooterEncoder;

    public Shooter(int shoot){
        shooter = new CANSparkMax(shoot, MotorType.kBrushless);
        shooterEncoder = shooter.getEncoder();
    }

    public void setShooterMotor(double speed){
        shooter.setVoltage(speed);
    }

    public void limelightShoot(double power)
    {
        shooter.set(-power);
        double degOff = Robot.limelight.getTX();
        if(Math.abs(degOff) > 1 && Robot.limelight.getTV() != 0)
        {
            double speed = .15 * degOff/(Math.abs(degOff));
            Robot.drivetrain.setLeftDrivetrain(-speed);
            Robot.drivetrain.setRightDrivetrain(speed);
            degOff = Robot.limelight.getTX();
        }
    }

    public void updateDashboard(){
        SmartDashboard.putNumber("Shooter Speed ", shooterEncoder.getVelocity());
    } 

    @Override
    public void periodic(){
        setDefaultCommand(new ShooterCom());
    }
}