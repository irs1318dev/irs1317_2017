package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Vision.VisionConstants;

/**
 * Task that turns the robot a certain amount clockwise or counterclockwise in-place using Positional PID based on vision center
 * 
 * @author William
 */
public class VisionCenteringTask extends MoveDistanceTaskBase implements IControlTask
{
    private double center;
    private double centerDegrees;

    /**
    * Initializes a new VisionCenteringTask
    */
    public VisionCenteringTask()
    {
        this(true);
    }

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
        // Convert center in pixels to degrees with 0 degrees being desired place
        center = this.getComponents().getVisionManager().getCenter1().x;
        center = center - (VisionConstants.CAMERA_RESOLUTION_X / 2.0);
        centerDegrees = (center * 25.0) / (VisionConstants.CAMERA_RESOLUTION_X / 2.0);

        // Set desired encoder distances based on degrees off of center
        double arcLength = Math.PI * HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE * (this.centerDegrees / 360.0);
        this.desiredFinalLeftEncoderDistance = this.startLeftEncoderDistance + arcLength;
        this.desiredFinalRightEncoderDistance = this.startRightEncoderDistance - arcLength;
    }

    /**
     * Run an iteration of the current task and apply any control changes
     * Update encoder distances based on new center of retro-reflective tape
     */
    @Override
    public void update()
    {
        this.determineFinalEncoderDistance();
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.desiredFinalLeftEncoderDistance);
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.desiredFinalRightEncoderDistance);
    }

    /**
    * Checks if center of retro-reflective tap is within acceptable distance of center of camera
    */
    @Override
    public boolean hasCompleted()
    {
        return center < VisionConstants.MAX_VALUE && center > VisionConstants.MIN_VALUE;

    }
}
