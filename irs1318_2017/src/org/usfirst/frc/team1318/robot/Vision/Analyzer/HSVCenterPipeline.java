package org.usfirst.frc.team1318.robot.Vision.Analyzer;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1318.robot.Vision.VisionConstants;
import org.usfirst.frc.team1318.robot.Vision.Helpers.ContourHelper;
import org.usfirst.frc.team1318.robot.Vision.Helpers.HSVFilter;
import org.usfirst.frc.team1318.robot.Vision.Helpers.ImageUndistorter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;

public class HSVCenterPipeline implements VisionPipeline
{
    private final ImageUndistorter undistorter;
    private final HSVFilter hsvFilter;

    private Point center;

    // FPS Measurement
    private long analyzedFrameCount;
    private double lastMeasuredTime;
    private double lastFpsMeasurement;

    /**
     * Initializes a new instance of the HSVCenterAnalyzer class.
     * @param output point writer
     */
    public HSVCenterPipeline()
    {
        this.undistorter = new ImageUndistorter();
        this.hsvFilter = new HSVFilter(VisionConstants.HSV_FILTER_LOW, VisionConstants.HSV_FILTER_HIGH);

        this.center = null;
        this.analyzedFrameCount = 0;
        this.lastMeasuredTime = Timer.getFPGATimestamp();
    }

    /**
     * Process a single image frame
     * @param frame image to analyze
     */
    @Override
    public void process(Mat image)
    {
        this.analyzedFrameCount++;
        if (VisionConstants.DEBUG
            && VisionConstants.DEBUG_PRINT_OUTPUT && this.analyzedFrameCount % VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL == 0)
        {
            double now = Timer.getFPGATimestamp();
            double elapsedTime = now - this.lastMeasuredTime;

            this.lastFpsMeasurement = ((double)VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL) / elapsedTime;
            this.lastMeasuredTime = now;
        }

        // first, undistort the image.
        image = this.undistorter.undistortFrame(image);
        if (VisionConstants.DEBUG
            && VisionConstants.DEBUG_FRAME_OUTPUT && this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
        {
            Imgcodecs.imwrite(String.format("%simage%d-1.undistorted.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount),
                image);
        }

        // save the undistorted image for possible output later...
        Mat undistortedImage = image.clone();

        // second, filter HSV
        image = this.hsvFilter.filterHSV(image);
        if (VisionConstants.DEBUG
            && VisionConstants.DEBUG_FRAME_OUTPUT && this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
        {
            Imgcodecs.imwrite(String.format("%simage%d-2.hsvfiltered.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount),
                image);
        }

        // third, find the largest contour.
        MatOfPoint largestContour = ContourHelper.findLargestContour(image);
        if (largestContour == null)
        {
            if (VisionConstants.DEBUG && VisionConstants.DEBUG_PRINT_OUTPUT && VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                System.out.println("could not find any contour");
            }
        }

        // fourth, find the center of mass for the largest contour
        Point centerOfMass = null;
        if (largestContour != null)
        {
            centerOfMass = ContourHelper.findCenterOfMass(largestContour);
            largestContour.release();
        }

        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_PRINT_OUTPUT && VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                if (centerOfMass == null)
                {
                    System.out.println("couldn't find the center of mass!");
                }
                else
                {
                    System.out.println(String.format("Center of mass: %f, %f", centerOfMass.x, centerOfMass.y));
                }
            }

            if (centerOfMass != null
                && VisionConstants.DEBUG_FRAME_OUTPUT && this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
            {
                Imgproc.circle(undistortedImage, centerOfMass, 2, new Scalar(0, 0, 255), -1);
                Imgcodecs.imwrite(String.format("%simage%d-3.redrawn.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount),
                    undistortedImage);
            }
        }

        // finally, record that center of mass
        this.center = centerOfMass;

        undistortedImage.release();
    }

    public Point getCenter()
    {
        return this.center;
    }

    public double getFps()
    {
        return this.lastFpsMeasurement;
    }
}
