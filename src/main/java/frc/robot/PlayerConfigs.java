package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PlayerConfigs {
    public static enum Driver {
        ALLISON,
        CHANTELLE,
        ERIC,
        JULISSA,
        RYAN,
        MENTOR
    }

    public static enum CoDriver {
        ALLISON,
        HAMZA,
        KEVIN,
        KYLIE,
        SHANNON,
        MENTOR
    }

    public static SendableChooser<Driver> D_chooser = new SendableChooser<>();
    public static SendableChooser<CoDriver> CD_chooser = new SendableChooser<>();
    public static Driver driver;
    public static CoDriver coDriver;

    //drivetrain
    public static double accelerator;
    public static double steering;
    public static boolean autoTarget;
    public static double turnSpeed;
    public static double driveSpeed;

    //lift
    public static boolean intakeLiftMid;
    public static boolean intakeLiftHi;

    //horizontal
    public static boolean acceptIntake;
    public static boolean rejectIntake;

    //shooter
    public static boolean lowPowerShooter;
    public static boolean midPowerShooter;
    public static boolean highPowerShooter;
    public static boolean fireTrigger;
    public static boolean rejectTrigger;
    public static boolean changeAutoShootState;

    //limelight
    public static boolean switchPipeline;

    //climbers
    public static double climberLeftExtension;
    public static double climberRightExtension;
    public static boolean autoClimbTriggerA;
    public static boolean autoClimbTriggerB;
    public static boolean climberExtend;
    public static boolean climberRetract;
    public static boolean climberRotate;
    public static boolean climbModeReset;

    public static void initTeamSetup(){
        D_chooser.addOption("ALLISON", Driver.ALLISON);
        D_chooser.addOption("CHANTELLE", Driver.CHANTELLE);
        D_chooser.addOption("ERIC", Driver.ERIC);
        D_chooser.addOption("JULISSA", Driver.JULISSA);
        D_chooser.setDefaultOption("RYAN", Driver.RYAN);
        D_chooser.addOption("MENTOR", Driver.MENTOR);

        CD_chooser.setDefaultOption("ALLISON", CoDriver.ALLISON);
        CD_chooser.addOption("HAMZA", CoDriver.HAMZA);
        CD_chooser.addOption("KEVIN", CoDriver.KEVIN);
        CD_chooser.addOption("KYLIE", CoDriver.KYLIE);
        CD_chooser.addOption("SHANNON", CoDriver.SHANNON);
        CD_chooser.addOption("MENTOR", CoDriver.MENTOR);

        SmartDashboard.putData(D_chooser);
        SmartDashboard.putData(CD_chooser);
    }

    public static void getPlayers(){
        driver = D_chooser.getSelected();
        coDriver = CD_chooser.getSelected();
    }

    public static void getDriverConfig(){
        switch(driver){    
            case ALLISON :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.4;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                rejectTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);

            case CHANTELLE :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.5;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                rejectTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);

            case ERIC :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.5;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                rejectTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);
            
            case JULISSA :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.4;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                rejectTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);
            
            case RYAN :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.3;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                rejectTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);

            case MENTOR :
                //drivetrain
                accelerator = Robot.controller0.getJoystickAxis(Constants.LEFT_STICK_Y);
                steering = Robot.controller0.getJoystickAxis(Constants.RIGHT_STICK_X);
                autoTarget = Robot.controller0.getButton(Constants.BUTTON_A);
                turnSpeed = 0.4;
                driveSpeed = 0.7;

                //lift
                intakeLiftHi = Robot.controller0.getTrigger(Constants.RIGHT_BUMPER);

                //shooter
                fireTrigger = Robot.controller0.getButton(Constants.LEFT_BUMPER);
                rejectTrigger = Robot.controller0.getTrigger(Constants.LEFT_TRIGGER);
                changeAutoShootState = Robot.controller0.getButton(Constants.BUTTON_BACK);

                //limelight
                switchPipeline = Robot.controller0.getButton(Constants.BUTTON_START);
        }
    }

    public static void getCoDriverConfig(){
        switch(coDriver){
            case ALLISON :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);
                
                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRetract = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);

            case HAMZA :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);
                
                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRetract = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);
            
            
            case KEVIN :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);
                
                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRetract = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);
            
            case KYLIE :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);
                
                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRetract = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);

            case SHANNON :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);
                
                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRetract = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);
            
            case MENTOR :
                //horizontal
                acceptIntake = Robot.controller1.getButton(Constants.LEFT_BUMPER);
                rejectIntake = Robot.controller1.getTrigger(Constants.LEFT_TRIGGER);

                //shooter
                lowPowerShooter = Robot.controller1.getButton(Constants.BUTTON_A);
                midPowerShooter = Robot.controller1.getButton(Constants.BUTTON_X);
                highPowerShooter = Robot.controller1.getButton(Constants.BUTTON_Y);

                //climber
                climberLeftExtension = Robot.controller1.getJoystickAxis(Constants.LEFT_STICK_Y);
                climberRightExtension = Robot.controller1.getJoystickAxis(Constants.RIGHT_STICK_Y);
                autoClimbTriggerA = Robot.controller1.getButton(Constants.LEFT_JOYSTICK_BUTTON);
                autoClimbTriggerB = Robot.controller1.getButton(Constants.RIGHT_JOYSTICK_BUTTON);
                climberExtend = Robot.controller1.getTrigger(Constants.RIGHT_TRIGGER);
                climberRetract = Robot.controller1.getButton(Constants.RIGHT_BUMPER);
                climberRotate = Robot.controller1.getButton(Constants.BUTTON_B);
                climbModeReset = Robot.controller1.getButton(Constants.BUTTON_BACK);
        }
    }
}
