package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Commands.IntakeTOCom;

public class Intake extends SubsystemBase{

    private VictorSPX horizontalIntake;
    private CANSparkMax intakeLift;
    private RelativeEncoder intakeLiftEncoder;
    private VictorSPX trigger;
    public final PIDController Lift_controller = new PIDController(Constants.kIntakeLiftKp, 0, 0);

    private boolean pulsing = false;

    public Intake (int horIntake, int vertIntake, int inLift) {
        horizontalIntake = new VictorSPX(horIntake);
        trigger = new VictorSPX(vertIntake);
        intakeLift = new CANSparkMax(inLift, MotorType.kBrushless);
        intakeLift.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        intakeLift.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        intakeLift.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
        intakeLift.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -60);

        intakeLiftEncoder = intakeLift.getEncoder();
        intakeLiftEncoder.setPositionConversionFactor(Constants.kIntakeLiftEncoderDistPerRot);
    }

    public void setHorizontalIntake(double speed) {
        horizontalIntake.set(ControlMode.PercentOutput, speed);
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

    public void setIntakeLift(double setpoint){
        var pidOutput = Robot.intake.Lift_controller.calculate(
            Robot.intake.getEncoder(), 
            Units.degreesToRadians(setpoint));
        intakeLift.setVoltage(pidOutput);
    }

    public void pulse(){
        if (pulsing == false){
            pulsing = true;
        }
        if (Timer.getFPGATimestamp() % 1 < 0.5){
            setTrigger(Constants.TRIGGER_SPEED);
        } else {
            setTrigger(0);
        }
        SmartDashboard.putBoolean("Firing", (Timer.getFPGATimestamp() % 1)<0.5);
    }

    public void stopPulse(){
        SmartDashboard.putBoolean("Firing", false);
        setTrigger(0);
        if (pulsing == true){
            pulsing = false;
        }
    }

    public void resetEncoder(){
        intakeLiftEncoder.setPosition(0.0);
    }

    public double getEncoder(){
        return intakeLiftEncoder.getPosition();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new IntakeTOCom());
    }
}