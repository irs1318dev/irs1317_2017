package org.usfirst.frc.team1318.robot.vision;

import org.opencv.core.Scalar;
import org.usfirst.frc.team1318.robot.TuningConstants;

public class VisionConstants
{
    // Debug output settings:
    public static final boolean DEBUG = false;
    public static final boolean DEBUG_PRINT_OUTPUT = false;
    public static final boolean DEBUG_PRINT_ANALYZER_DATA = false;
    public static final int DEBUG_FPS_AVERAGING_INTERVAL = 25;
    public static final boolean DEBUG_OUTPUT_FRAMES = false;
    public static final boolean DEBUG_OUTPUT_GEAR_FRAMES = true;
    public static final boolean DEBUG_OUTPUT_SHOOTER_FRAMES = false;
    public static final boolean DEBUG_SAVE_FRAMES = false;
    public static final int DEBUG_FRAME_OUTPUT_GAP = 50; // the number of frames to wait between saving debug image output
    public static final String DEBUG_OUTPUT_FOLDER = "/home/lvuser/vision/";

    // Conversion constants...
    public static final double ANGLE_TO_RADIANS = (Math.PI / 180.0f);
    public static final double RADIANS_TO_ANGLE = (180.0f / Math.PI);

    // Settings for AXIS IP-based camera
    public static final String AXIS_CAMERA_IP_ADDRESS = "10.13.18.11";
    public static final String AXIS_CAMERA_USERNAME_PASSWORD = "root:1318";
    public static final int AXIS_CAMERA_RESOLUTION_X = 320;
    public static final int AXIS_CAMERA_RESOLUTION_Y = 240;
    public static final double AXIS_CAMERA_ANGLE_OF_VIEW = 50.0; // note that documentation says 47 degrees, so we'll have to see whether this is accurate enough.
    public static final int AXIS_CAMERA_CENTER_WIDTH = VisionConstants.AXIS_CAMERA_RESOLUTION_X / 2; // distance from center to left/right sides in pixels
    public static final int AXIS_CAMERA_CENTER_HEIGHT = VisionConstants.AXIS_CAMERA_RESOLUTION_Y / 2; // distance from center to top/bottom in pixels
    public static final double AXIS_CAMERA_CENTER_VIEW_ANGLE = VisionConstants.AXIS_CAMERA_ANGLE_OF_VIEW / 2.0;

    // Settings for Microsoft LifeCam HD-3000 USB-based camera
    public static final int LIFECAM_CAMERA_RESOLUTION_X = 320;
    public static final int LIFECAM_CAMERA_RESOLUTION_Y = 240;
    public static final double LIFECAM_CAMERA_CENTER_WIDTH = VisionConstants.LIFECAM_CAMERA_RESOLUTION_X / 2.0 - 0.5; // distance from center to left/right sides in pixels
    public static final double LIFECAM_CAMERA_CENTER_HEIGHT = VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y / 2.0 - 0.5; // distance from center to top/bottom in pixels
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_X = 48.4; // 4:3 field of view along x axis. note that documentation says 68.5 degrees diagonal (at 16:9), so this is an estimate.
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_Y = 36.3; // 4:3 field of view along y axis
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_X_RADIANS = VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_X
        * VisionConstants.ANGLE_TO_RADIANS;
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_Y_RADIANS = VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_Y
        * VisionConstants.ANGLE_TO_RADIANS;
    public static final double LIFECAM_CAMERA_CENTER_VIEW_ANGLE = VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_X / 2.0;
    public static final double LIFECAM_CAMERA_FOCAL_LENGTH_X = 356.016; // focal_length = res_* / (2.0 * tan (FOV_* / 2.0)
    public static final double LIFECAM_CAMERA_FOCAL_LENGTH_Y = 366.058; // focal_length = res_* / (2.0 * tan (FOV_* / 2.0)
    public static final int LIFECAM_CAMERA_EXPOSURE = 1;
    public static final int LIFECAM_CAMERA_BRIGHTNESS = 1;
    public static final int LIFECAM_CAMERA_FPS = 20; // Max supported value is 30

    // Undistort constants
    public static final boolean SHOULD_UNDISTORT = false;

    // HSV Filtering constants
    public static final Scalar AXIS_HSV_FILTER_LOW = new Scalar(85, 65, 65);
    public static final Scalar AXIS_HSV_FILTER_HIGH = new Scalar(90, 255, 255);
    public static final Scalar LIFECAM_HSV_FILTER_LOW = new Scalar(52, 150, 100);
    public static final Scalar LIFECAM_HSV_FILTER_HIGH = new Scalar(80, 255, 255);

    // Contour filtering constants
    public static final double CONTOUR_MIN_AREA = 25.0;

    // Real Measurements
    public static final double GEAR_RETROREFLECTIVE_TAPE_HEIGHT = 5.0; // 5 inches tall
    public static final double GEAR_RETROREFLECTIVE_TAPE_WIDTH = 2.0; // 2 inches wide
    public static final double GEAR_RETROREFLECTIVE_TAPE_HxW_RATIO = 2.5; // height-to-width ratio
    public static final double GEAR_HxW_ALLOWABLE_RATIO_RANGE = 1.0; // allowable height-to-width ratio range
    public static final double GEAR_CONTOUR_ALLOWABLE_RATIO = 0.9; // the ratio of the second-largest contour to the largest
    public static final double GEAR_CAMERA_MOUNTING_DISTANCE = 0.0; // camera is 0 inches back from the end of the robot in front of the camera
    public static final double GEAR_CAMERA_HORIZONTAL_MOUNTING_OFFSET = 8.0; // camera is 12 inches (~11 for practice) to the left from center line of the robot, tape center is 4 inches to the left of the center of the robot
    public static final double GEAR_CAMERA_HORIZONTAL_MOUNTING_ANGLE = TuningConstants.COMPETITION_ROBOT ? 0.0 : -3.0; // practice robot camera is pointed 3 degrees to the right

    public static final double SHOOTER_LARGER_RETROREFLECTIVE_TAPE_HEIGHT_CENTROID_FROM_GROUND = 86; // 7 feet, 2 inches from floor to center of upper retroreflective tape on boiler
    public static final double SHOOTER_CAMERA_VERTICAL_MOUNTING_HEIGHT = 16.25; // 16.25 inches from ground
    public static final double SHOOTER_CAMERA_VERTICAL_MOUNTING_ANGLE = 45.0; // 45 degrees
    public static final double SHOOTER_CAMERA_MOUNTING_DISTANCE = 0.0; // camera is ? inches back from the end of the robot in front of the camera
    public static final double SHOOTER_CAMERA_HORIZONTAL_MOUNTING_OFFSET = TuningConstants.COMPETITION_ROBOT ? -12.0 : -11.0; // camera is 12 inches (~11 for practice) to the right from the center line of the robot
    public static final double SHOOTER_CAMERA_HORIZONTAL_MOUNTING_ANGLE = 0.0; // camera is pointed 0 degrees to the right
    public static final double SHOOTER_CAMERA_TO_RETROREFLECTIVE_TAPE_HEIGHT = VisionConstants.SHOOTER_LARGER_RETROREFLECTIVE_TAPE_HEIGHT_CENTROID_FROM_GROUND - VisionConstants.SHOOTER_CAMERA_VERTICAL_MOUNTING_HEIGHT;
}
