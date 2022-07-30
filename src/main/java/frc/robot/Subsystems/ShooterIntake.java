package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Commands.ShooterIntakeCom;


public class ShooterIntake extends SubsystemBase{

    private VictorSPX horizontalIntake;
    private CANSparkMax intakeLift;
    private RelativeEncoder intakeLiftEncoder;
    private VictorSPX verticalIntake;

    public ShooterIntake (int horIntake, int vertIntake, int inLift) {
        horizontalIntake = new VictorSPX(horIntake);
        verticalIntake = new VictorSPX(vertIntake);
        intakeLift = new CANSparkMax(inLift, MotorType.kBrushless);
        intakeLiftEncoder = intakeLift.getEncoder();
    }

    public void setHorizontalIntake(double speed) {
        horizontalIntake.set(ControlMode.PercentOutput, speed);
    }

    public void setVerticalIntake(double speed) {
        verticalIntake.set(ControlMode.PercentOutput, speed);
    }

    public void setIntakeLift(double speed){
        intakeLift.set(-speed);
    }

    public void resetEncoder(){
        intakeLiftEncoder.setPosition(0.0);
    }

    @Override
    public void periodic(){
        setDefaultCommand(new ShooterIntakeCom());
    }
}