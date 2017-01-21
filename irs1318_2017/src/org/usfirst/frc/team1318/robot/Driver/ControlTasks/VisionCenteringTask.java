package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;

/**
 * Task that turns the robot a certain amount clockwise or counterclockwise in-place using Positional PID based on vision center
 * 
 * @author William
 */
public class VisionCenteringTask extends MoveDistanceTaskBase implements IControlTask
{
    private Double centerDegrees;

    /**
    * Initializes a new VisionCenteringTask
    */
    public VisionCenteringTask()
    {
        this(true);
    }

    /**
    * Initializes a new VisionCenteringTask
    */
    public VisionCenteringTask(boolean resetPositionOnEnd)
    {
        super(resetPositionOnEnd);
    }

    /**
     * Determine the final encoder distance based off center in pixels
     */
    @Override
    protected void determineFinalEncoderDistance()
    {
        double currentLeftEncoderDistance = this.getComponents().getDriveTrain().getLeftEncoderDistance();
        double currentRightEncoderDistance = this.getComponents().getDriveTrain().getRightEncoderDistance();

        // Convert center in pixels to degrees with 0 degrees being desired place
        Double centerAngle = this.getComponents().getVisionManager().getCenter1Angle();
        if (centerAngle != null)
        {
            // Set desired encoder distances based on degrees off of center
            double arcLength = Math.PI * HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE * (this.centerDegrees / 360.0);
            this.desiredFinalLeftEncoderDistance = currentLeftEncoderDistance + arcLength;
            this.desiredFinalRightEncoderDistance = currentRightEncoderDistance - arcLength;
        }
        else
        {
            this.desiredFinalLeftEncoderDistance = currentLeftEncoderDistance;
            this.desiredFinalRightEncoderDistance = currentRightEncoderDistance;
        }
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        // Update encoder distances based on new center of retroreflective tape
        //this.determineFinalEncoderDistance();

        super.update();
    }

    /**
    * Checks if center of retroreflective tape is within acceptable distance of center of camera
    */
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
        //return Math.abs(this.centerDegrees) < TuningConstants.MAX_VISION_CENTERING_RANGE_DEGREES;
    }
}
