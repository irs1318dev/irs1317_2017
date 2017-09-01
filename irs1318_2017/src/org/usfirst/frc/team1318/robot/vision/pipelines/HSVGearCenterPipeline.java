package org.usfirst.frc.team1318.robot.vision.pipelines;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1318.robot.common.wpilib.ITimer;
import org.usfirst.frc.team1318.robot.vision.VisionConstants;
import org.usfirst.frc.team1318.robot.vision.common.ContourHelper;
import org.usfirst.frc.team1318.robot.vision.common.HSVFilter;
import org.usfirst.frc.team1318.robot.vision.common.ImageUndistorter;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;

public class HSVGearCenterPipeline implements ICentroidVisionPipeline
{
    private final ITimer timer;
    private final boolean shouldUndistort;
    private final ImageUndistorter undistorter;
    private final HSVFilter hsvFilter;

    private final CvSource frameInput;
    private final CvSource hsvOutput;

    // measured values
    private Point largestCenter;
    private Point secondLargestCenter;

    private Double measuredAngleX;

    private Double desiredAngleX;
    private Double distanceFromRobot;

    // FPS Measurement
    private long analyzedFrameCount;
    private double lastMeasuredTime;
    private double lastFpsMeasurement;

    // active status
    private volatile boolean isActive;

    /**
     * Initializes a new instance of the HSVGearCenterPipeline class.
     * @param shouldUndistort whether to undistort the image or not
     */
    public HSVGearCenterPipeline(
        ITimer timer,
        boolean shouldUndistort)
    {
        this.shouldUndistort = shouldUndistort;

        this.undistorter = new ImageUndistorter();
        this.hsvFilter = new HSVFilter(VisionConstants.LIFECAM_HSV_FILTER_LOW, VisionConstants.LIFECAM_HSV_FILTER_HIGH);

        this.largestCenter = null;
        this.secondLargestCenter = null;

        this.measuredAngleX = null;

        this.desiredAngleX = null;
        this.distanceFromRobot = null;

        this.analyzedFrameCount = 0;
        this.timer = timer;
        this.lastMeasuredTime = this.timer.get();

        this.isActive = true;

        if (VisionConstants.DEBUG && VisionConstants.DEBUG_OUTPUT_GEAR_FRAMES)
        {
            this.frameInput = CameraServer.getInstance().putVideo("g.input", VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);
            this.hsvOutput = CameraServer.getInstance().putVideo("g.hsv", VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);
        }
        else
        {
            this.frameInput = null;
            this.hsvOutput = null;
        }
    }

    /**
     * Process a single image frame
     * @param frame image to analyze
     */
    @Override
    public void process(Mat image)
    {
        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_SAVE_FRAMES && this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
            {
                Imgcodecs.imwrite(String.format("%simage%d-1.undistorted.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount), image);
            }

            if (VisionConstants.DEBUG_OUTPUT_GEAR_FRAMES)
            {
                this.frameInput.putFrame(image);
            }
        }

        if (!this.isActive)
        {
            return;
        }

        this.analyzedFrameCount++;
        if (VisionConstants.DEBUG && VisionConstants.DEBUG_PRINT_OUTPUT && this.analyzedFrameCount % VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL == 0)
        {
            double now = this.timer.get();
            double elapsedTime = now - this.lastMeasuredTime;

            this.lastFpsMeasurement = ((double)VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL) / elapsedTime;
            this.lastMeasuredTime = this.timer.get();
        }

        // first, undistort the image.
        Mat undistortedImage;
        if (this.shouldUndistort)
        {
            image = this.undistorter.undistortFrame(image);
        }

        // save the undistorted image for possible output later...
        if (this.shouldUndistort)
        {
            undistortedImage = image.clone();
        }
        else
        {
            undistortedImage = image;
        }

        // second, filter HSV
        image = this.hsvFilter.filterHSV(image);
        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_SAVE_FRAMES && this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
            {
                Imgcodecs.imwrite(String.format("%simage%d-2.hsvfiltered.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount), image);
            }

            if (VisionConstants.DEBUG_OUTPUT_GEAR_FRAMES)
            {
                this.hsvOutput.putFrame(image);
            }
        }

        // third, find the largest contour.
        MatOfPoint[] largestContours = ContourHelper.findTwoLargestContours(
            image,
            VisionConstants.CONTOUR_MIN_AREA,
            VisionConstants.GEAR_RETROREFLECTIVE_TAPE_HxW_RATIO,
            VisionConstants.GEAR_HxW_ALLOWABLE_RATIO_RANGE,
            VisionConstants.GEAR_CONTOUR_ALLOWABLE_RATIO);
        MatOfPoint largestContour = largestContours[0];
        MatOfPoint secondLargestContour = largestContours[1];

        if (largestContour == null)
        {
            if (VisionConstants.DEBUG && VisionConstants.DEBUG_PRINT_OUTPUT && VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                System.out.println("could not find any contour");
            }
        }

        // fourth, find the center of mass for the largest two contours
        Point largestCenterOfMass = null;
        Point secondLargestCenterOfMass = null;
        Rect largestBoundingRect = null;
        Rect secondLargestBoundingRect = null;
        if (largestContour != null)
        {
            largestCenterOfMass = ContourHelper.findCenterOfMass(largestContour);
            largestBoundingRect = Imgproc.boundingRect(largestContour);
            largestContour.release();
        }

        if (secondLargestContour != null)
        {
            secondLargestCenterOfMass = ContourHelper.findCenterOfMass(secondLargestContour);
            secondLargestBoundingRect = Imgproc.boundingRect(secondLargestContour);
            secondLargestContour.release();
        }

        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_PRINT_OUTPUT && VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                if (largestCenterOfMass == null)
                {
                    System.out.println("couldn't find the center of mass!");
                }
                else
                {
                    System.out.println(String.format("Center of mass: %f, %f", largestCenterOfMass.x, largestCenterOfMass.y));
                }

                if (secondLargestCenterOfMass == null)
                {
                    System.out.println("couldn't find the center of mass!");
                }
                else
                {
                    System.out.println(String.format("Center of mass: %f, %f", secondLargestCenterOfMass.x, secondLargestCenterOfMass.y));
                }
            }
        }

        // finally, record the centers of mass
        this.largestCenter = largestCenterOfMass;
        this.secondLargestCenter = secondLargestCenterOfMass;

        undistortedImage.release();

        // GEAR CALCULATIONS
        if (this.largestCenter == null && this.secondLargestCenter == null)
        {
            this.desiredAngleX = null;
            this.distanceFromRobot = null;

            this.measuredAngleX = null;

            return;
        }

        // we want to get the leftmost centroid, representing the left piece of the retroreflective tape.
        Point gearMarkerCenter;
        Rect boundingRect;
        if (this.largestCenter != null && this.secondLargestCenter != null && this.largestCenter.x > this.secondLargestCenter.x)
        {
            gearMarkerCenter = this.secondLargestCenter;
            boundingRect = secondLargestBoundingRect;
        }
        else
        {
            gearMarkerCenter = this.largestCenter;
            boundingRect = largestBoundingRect;
        }

        int gearMarkerHeight = boundingRect.height;
        if (gearMarkerHeight == 0)
        {
            return;
        }

        // Find desired data
        double xOffsetMeasured = gearMarkerCenter.x - VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH;
        this.measuredAngleX = Math.atan(xOffsetMeasured / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_X) * VisionConstants.RADIANS_TO_ANGLE - VisionConstants.GEAR_CAMERA_HORIZONTAL_MOUNTING_ANGLE;
        // this.thetaXOffsetMeasured = xOffsetMeasured * VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_X / (double)VisionConstants.LIFECAM_CAMERA_RESOLUTION_X;

        double distanceFromCam = ((VisionConstants.GEAR_RETROREFLECTIVE_TAPE_HEIGHT) / (Math.tan(VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_Y_RADIANS)))
            * ((double)VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y / (double)gearMarkerHeight);
        this.distanceFromRobot = distanceFromCam * Math.cos(this.measuredAngleX * VisionConstants.ANGLE_TO_RADIANS) + VisionConstants.GEAR_CAMERA_MOUNTING_DISTANCE;
        this.desiredAngleX = Math.asin(VisionConstants.GEAR_CAMERA_HORIZONTAL_MOUNTING_OFFSET / distanceFromCam) * VisionConstants.RADIANS_TO_ANGLE;
    }

    public void setActivation(boolean isActive)
    {
        this.isActive = isActive;
    }

    public boolean isActive()
    {
        return this.isActive;
    }

    public Point getCenter()
    {
        return this.largestCenter;
    }

    public Double getDesiredAngleX()
    {
        return this.desiredAngleX;
    }

    public Double getMeasuredAngleX()
    {
        return this.measuredAngleX;
    }

    public Double getRobotDistance()
    {
        return this.distanceFromRobot;
    }

    public double getFps()
    {
        return this.lastFpsMeasurement;
    }
}
