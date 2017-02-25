package org.usfirst.frc.team1318.robot;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_Y_AXIS = true;

    public static final int PCM_A_MODULE = 0;
    public static final int PCM_B_MODULE = 1;

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== Auto ==============================================================

    public static final int AUTO_SIDE_OF_FIELD_CHANNEL = 6;
    public static final int AUTO_DIP_SWITCH_A_CHANNEL = 7;
    public static final int AUTO_DIP_SWITCH_B_CHANNEL = 8;

    //================================================== Vision ==============================================================

    public static final int VISION_SHOOTER_LIGHT_CHANNEL = 0;
    public static final int VISION_GEAR_LIGHT_CHANNEL = 1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_LEFT_TALON_CHANNEL = 0;
    public static final int DRIVETRAIN_RIGHT_TALON_CHANNEL = 1;

    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = 2;
    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = 3;

    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = 0;
    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = 1;

    //================================================== Climber ==============================================================

    public static final int CLIMBER_MOTOR_CHANNEL = 4;
    public static final int CLIMBER_PDP_CHANNEL_A = 4;
    public static final int CLIMBER_PDP_CHANNEL_B = 11;

    //================================================== Intake ==============================================================

    public static final int INTAKE_MOTOR_CHANNEL = 3;

    public static final int INTAKE_EXTENDER_SOLENOID_CHANNEL_A = 4;
    public static final int INTAKE_EXTENDER_SOLENOID_CHANNEL_B = 5;

    public static final int INTAKE_GEAR_EXTENDER_SOLENOID_CHANNEL_A = 2;
    public static final int INTAKE_GEAR_EXTENDER_SOLENOID_CHANNEL_B = 3;

    //================================================== Shooter ==============================================================

    public static final int SHOOTER_HOOD_CHANNEL_A = 6;
    public static final int SHOOTER_HOOD_CHANNEL_B = 7;

    public static final int SHOOTER_FEEDER_CHANNEL = 2;

    public static final int SHOOTER_READY_LIGHT_CHANNEL = 0; // PCM B
    public static final int SHOOTER_TARGETING_LIGHT_CHANNEL = 0;

    public static final int SHOOTER_MOTOR_CHANNEL = 5;
    //    public static final int SHOOTER_MASTER_MOTOR_CHANNEL = 1;
    //    public static final int SHOOTER_FOLLOWER_MOTOR_CHANNEL = 2;

    public static final int SHOOTER_ENCODER_CHANNEL_A = 4;
    public static final int SHOOTER_ENCODER_CHANNEL_B = 5;
}
