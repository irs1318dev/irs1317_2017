package org.usfirst.frc.team1318.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean THROW_EXCEPTIONS = true;

    //================================================== Autonomous ==============================================================

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 1.0;

    // Acceptable vision centering range values in degrees
    public static final double MAX_VISION_CENTERING_RANGE_DEGREES = 0.5;

    // Acceptable vision distance from tape in inches
    public static final double MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE = 18.0;

    //================================================== DriveTrain ==============================================================

    // Drivetrain PID keys/default values:
    public static final boolean DRIVETRAIN_USE_PID = true;

    // Velocity PID (right)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP_DEFAULT = 0.02; // 0.0275;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF_DEFAULT = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS_DEFAULT = 100.0;

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP_DEFAULT = 0.02; //0.0275;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KF_DEFAULT = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KS_DEFAULT = 100.0;

    // Position PID (right)
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP_DEFAULT = 0.35;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF_DEFAULT = 0.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT = 0.35;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF_DEFAULT = 0.0;

    // Drivetrain choices for one-stick drive
    public static final double DRIVETRAIN_K1 = 1.4;
    public static final double DRIVETRAIN_K2 = 0.5;

    public static final double DRIVETRAIN_A = 0.4;// "a" coefficient (advancing turn)
    public static final double DRIVETRAIN_B = 0.4;// "b" coefficient (in-place turn)

    // Drivetrain deadzone/max power levels
    public static final double DRIVETRAIN_X_DEAD_ZONE = .05;
    public static final double DRIVETRAIN_Y_DEAD_ZONE = .1;
    public static final double DRIVETRAIN_MAX_POWER_LEVEL = 0.775;// max power level (velocity)
    public static final double DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID = 0.2;// max power level (positional, non-PID)

    public static final double DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL = 0.6;
    public static final double DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL = 1.0;

    public static final double DRIVETRAIN_REVERSE_RIGHT_SCALE_FACTOR = 1.05;
    public static final double DRIVETRAIN_REVERSE_LEFT_SCALE_FACTOR = 1.05;

    public static final double DRIVETRAIN_ENCODER_ODOMETRY_ANGLE_CORRECTION = 1.0; // account for turning weirdness (any degree offset in the angle)

    //================================================== Intake ==============================================================

    public static final double INTAKE_MAX_MOTOR_SPEED = 0.9;

    //================================================== Climber ==============================================================

    public static final double CLIMBER_MAX_MOTOR_POWER = 0.9;
    public static final double CLIMBER_MAX_CURRENT_DRAW = 70.0;

    public static final double CLIMBER_THROTTLE_DEAD_ZONE = 0.25;

    //================================================== Shooter ==============================================================

    public static final double SHOOTER_MAX_FEEDER_POWER = 0.4;

    public static final boolean SHOOTER_USE_CAN_PID = false;
    public static final boolean SHOOTER_USE_ROBORIO_PID = true;

    public static final double SHOOTER_CAN_PID_KP = 0.024;
    public static final double SHOOTER_CAN_PID_KI = 0.0;
    public static final double SHOOTER_CAN_PID_KD = 0.0;
    public static final double SHOOTER_CAN_PID_KF = 0.025;

    public static final double SHOOTER_ROBORIO_PID_KP = 0.002;
    public static final double SHOOTER_ROBORIO_PID_KI = 0.0;
    public static final double SHOOTER_ROBORIO_PID_KD = 0.0;
    public static final double SHOOTER_ROBORIO_PID_KF = 0.8;
    public static final double SHOOTER_ROBORIO_PID_KS = 2000.0;

    public static final double SHOOTER_MIN_POWER = -1.0;
    public static final double SHOOTER_MAX_POWER = 1.0;

    public static final double SHOOTER_PID_MAX_VELOCITY = 95000.0; // 40000.0;
    public static final double SHOOTER_ALLOWABLE_ERROR = 0.035; // plus or minus 3.5% error is max allowed

    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.7;
    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.6;
    public static final double SHOOTER_CLOSE_SHOT_LOW_GOAL_VELOCITY = 0.2;
}
