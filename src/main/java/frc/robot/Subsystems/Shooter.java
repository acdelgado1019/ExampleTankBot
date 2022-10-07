package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
    private VictorSPX trigger;

    public boolean autoShoot = true;

    public Shooter(int shoot, int trig){
        shooter = new CANSparkMax(shoot, MotorType.kBrushless);
        shooterEncoder = shooter.getEncoder();
        trigger = new VictorSPX(trig);
    }

    public void setShooterMotor(double speed){
        shooter.setVoltage(-speed);
    }

    public double shooterSpeedAdjust(double distance){
        double outputVoltage = 9;
        //(4-Math.sqrt(16+0.8*(-3.5-distance)))/0.4;
        if (Double.isNaN(outputVoltage)){
            outputVoltage = Constants.SHOOTER_IDLE_SPEED;
            Robot.ledStrip.solid(15);
        }
        return outputVoltage;
    }

    public void setTrigger(double speed) {
        trigger.set(ControlMode.PercentOutput, speed);
        if (speed < 0){
            Robot.ledStrip.solid(60);
        } else if (speed > 0){
            Robot.ledStrip.solid(30);
        } else {
            Robot.ledStrip.solid(90);
        }
    }

    //Pulses the trigger in half-second increments to allow for flywheel recovery

    public boolean getAutoShootEnable(){
        return autoShoot;
    }

    public void changeAutoShootState(){
        autoShoot = !autoShoot;
    }

    public void updateDashboard(){
        SmartDashboard.putNumber("Shooter Speed", -shooterEncoder.getVelocity());
        SmartDashboard.putBoolean("Auto Shoot", getAutoShootEnable());
    } 

    @Override
    public void periodic(){
        setDefaultCommand(new ShooterTOCom());
    }
}