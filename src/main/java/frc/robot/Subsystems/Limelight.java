package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Commands.LimelightTOCom;
import edu.wpi.first.math.util.Units;
/* LimeLight specific Imports*/
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight extends SubsystemBase {

    public double distance;
    public double offset;

    // Creates a new LimeLight.
    private NetworkTable table;
    private NetworkTableEntry tx;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry ty;

    public void updateData() {
        // update table, then update from updated table
        table = NetworkTableInstance.getDefault().getTable("limelight");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
    }

    public double getTX() {
        updateData();
        return tx.getDouble(0.0);
    }

    public double getTY() {
        updateData();
        return ty.getDouble(0.0);
    }

    public double getTA() {
        updateData();
        return ta.getDouble(0.0);
    }

    public double getTV() {
        updateData();
        return tv.getDouble(0.0);
    }
    public boolean getRange(){
        boolean limeRange = Math.abs(tx.getDouble(0.0))<15 && tx.getDouble(0.0)!= 0.0;
        boolean localRange = Math.abs(getOffset()-Robot.drivetrain.getNormHeading())<15;
        SmartDashboard.putBoolean("IN RANGE", (limeRange || localRange) ? true : false);
        return limeRange || localRange;
    }

    public double getDistance(){
        if(getTV() == 1.0){distance = (Constants.goalHeight - Constants.camHeight)/Math.tan((Constants.camAngle + getTY()) * (Math.PI / 180.0))/12;
        } else {
            double x = Robot.drivetrain.odometry.getPoseMeters().getX();
            double y = Robot.drivetrain.odometry.getPoseMeters().getY();
            distance = Units.metersToFeet(Math.sqrt((x-8.2)*(x-8.2)+(y-4.1)*(y-4.1)));
        }
        SmartDashboard.putNumber("Distance", distance);
        return distance;
    }

    public double getOffset(){
        double x = (Robot.drivetrain.odometry.getPoseMeters().getX()-8.2);
        double y = (Robot.drivetrain.odometry.getPoseMeters().getY()-4.1);
        
        if (x<0){offset = Units.radiansToDegrees(Math.atan(y/x));} 
        else {offset = Units.radiansToDegrees(Math.atan(y/x))-180*(y/Math.abs(y));}
        return offset;
    }

    public void switchCameraMode(){
        table.getEntry("camMode").setNumber(table.getEntry("camMode").getDouble(0.0) == 0 ? 1 : 0);
        table.getEntry("ledMode").setNumber(table.getEntry("ledMode").getDouble(0.0) == 0 ? 3 : 0);
    }

    @Override
    public void periodic() {
        setDefaultCommand(new LimelightTOCom());
        // This method will be called once per scheduler run
    }
}