package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Commands.IntakeTOCom;

public class Intake extends SubsystemBase{

    private VictorSPX horizontalIntake;
    private CANSparkMax intakeLift;
    public RelativeEncoder intakeLiftEncoder;
    public final PIDController Lift_controller = new PIDController(Constants.kIntakeLiftKp, 0, 0);

    public Intake (int horIntake, int inLift) {
        horizontalIntake = new VictorSPX(horIntake);
        intakeLift = new CANSparkMax(inLift, MotorType.kBrushless);
        intakeLift.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        intakeLift.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        intakeLift.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 80);
        intakeLift.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 20);

        intakeLiftEncoder = intakeLift.getEncoder();
        intakeLiftEncoder.setPositionConversionFactor(Constants.kIntakeLiftEncoderDistPerRot);
    }

    public void setHorizontalIntake(double speed) {
        horizontalIntake.set(ControlMode.PercentOutput, speed);
    }

    public void setIntakeLift(double setpoint){
        var pidOutput = Robot.intake.Lift_controller.calculate(Robot.intake.getEncoder(), setpoint);
        intakeLift.setVoltage(pidOutput);
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