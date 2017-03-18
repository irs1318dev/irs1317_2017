package org.usfirst.frc.team1318.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = true;
    public static final boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    //================================================== Autonomous ==============================================================

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 1.0;

    // Acceptable vision centering range values in degrees
    public static final double MAX_VISION_CENTERING_RANGE_DEGREES = 5.0;

    // Acceptable vision distance from tape in inches
    public static final double MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE = 24.0;

    // PID settings for Centering the robot on a vision target
    public static final double VISION_CENTERING_PID_KP = 0.075; // TuningConstants.COMPETITION_ROBOT ? 0.1 : 0.075;
    public static final double VISION_CENTERING_PID_KI = 0.0;
    public static final double VISION_CENTERING_PID_KD = 0.1;
    public static final double VISION_CENTERING_PID_KF = 0.0;
    public static final double VISION_CENTERING_PID_KS = 1.0;
    public static final double VISION_CENTERING_PID_MIN = -0.3;
    public static final double VISION_CENTERING_PID_MAX = 0.3;

    // PID settings for Advancing the robot towards a vision target
    public static final double VISION_ADVANCING_PID_KP = 0.005;
    public static final double VISION_ADVANCING_PID_KI = 0.0;
    public static final double VISION_ADVANCING_PID_KD = 0.0;
    public static final double VISION_ADVANCING_PID_KF = 0.0;
    public static final double VISION_ADVANCING_PID_KS = 1.0;
    public static final double VISION_ADVANCING_PID_MIN = -0.3;
    public static final double VISION_ADVANCING_PID_MAX = 0.3;

    // Distance from alliance station wall to the airship
    public static final double AIRSHIP_DISTANCE = 80.0;

    //================================================== DriveTrain ==============================================================

    // Drivetrain PID keys/default values:
    public static final boolean DRIVETRAIN_USE_PID = true;

    // Velocity PID (right)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP = 0.02; // TuningConstants.COMPETITION_ROBOT ? 0.015 : 0.02;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS = 100.0;

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP = 0.02; // TuningConstants.COMPETITION_ROBOT ? 0.015 : 0.02;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KF = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KS = 100.0;

    // Position PID (right)
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP = 0.35;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF = 0.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP = 0.35;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF = 0.0;

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

    public static final double INTAKE_MAX_MOTOR_SPEED = 0.95; //0.9; //0.8;
    public static final double THROUGH_BEAM_BROKEN_VOLTAGE_MIN = 0.6;

    //================================================== Climber ==============================================================

    public static final double CLIMBER_MAX_MOTOR_POWER = 0.9;
    public static final double CLIMBER_MAX_CURRENT_DRAW = 70.0;

    public static final double CLIMBER_THROTTLE_DEAD_ZONE = 0.25;

    //================================================== Shooter ==============================================================

    public static final double SHOOTER_MAX_FEEDER_POWER = 0.5;

    public static final boolean SHOOTER_USE_CAN_PID = true;
    public static final double SHOOTER_CAN_PID_KP = 0.0825;
    public static final double SHOOTER_CAN_PID_KI = 0.0;
    public static final double SHOOTER_CAN_PID_KD = 0.0;
    public static final double SHOOTER_CAN_PID_KF = 0.026;

    public static final double SHOOTER_MIN_POWER = -1.0;
    public static final double SHOOTER_MAX_POWER = 1.0;

    public static final double SHOOTER_CAN_MAX_VELOCITY = 40000.0; // practice robot...
    public static final double SHOOTER_ALLOWABLE_ERROR = 0.035; // plus or minus 3.5% error is max allowed  .035

    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.84;
    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.63;
    public static final double SHOOTER_CLOSE_SHOT_LOW_GOAL_VELOCITY = 0.22;

    public static final double SHOOTER_CLOSE_SHOT_BACKWARDS_DISTANCE = 24.0;
}
