package org.usfirst.frc.team1318.robot.Vision;

import org.opencv.core.Scalar;

public class VisionConstants
{
    // Debug output settings:
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PRINT_OUTPUT = true;
    public static final boolean DEBUG_PRINT_ANALYZER_DATA = false;
    public static final int DEBUG_FPS_AVERAGING_INTERVAL = 30;
    public static final boolean DEBUG_FRAME_OUTPUT = false;
    public static final int DEBUG_FRAME_OUTPUT_GAP = 50; // the number of frames to wait between saving debug image output
    public static final String DEBUG_OUTPUT_FOLDER = "/C/vision/";

    // Settings for AXIS IP-based Camera
    public static final String CAMERA_IP_ADDRESS = "10.13.18.11";
    public static final String CAMERA_USERNAME_PASSWORD = "root:1318";
    public static final int CAMERA_RESOLUTION_X = 320;
    public static final int CAMERA_RESOLUTION_Y = 240;

    // HSV Filtering constants
    public static final Scalar HSV_FILTER_LOW = new Scalar(65, 100, 100);
    public static final Scalar HSV_FILTER_HIGH = new Scalar(90, 255, 255);
}
