package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.PIDHandler;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class VisionForwardAndCenterTask extends VisionCenteringTask implements IControlTask
{
    private PIDHandler forwardPIDHandler;

    /**
    * Initializes a new VisionForwardAndCenterTask
    * @param visionMode whether to use Gear (true) or Shooter (false) vision mode
    */
    public VisionForwardAndCenterTask(boolean visionMode)
    {
        super(visionMode);

        this.forwardPIDHandler = null;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();
        this.forwardPIDHandler = new PIDHandler(0.005, 0.0, 0.0, 0.0, 1.0, -0.3, 0.3, this.getInjector().getInstance(ITimer.class));
    }

    @Override
    public void update()
    {
        super.update();
        Double currentDistance = this.visionManager.getMeasuredDistance();
        if (currentDistance != null)
        {
            this.setAnalogOperationState(Operation.DriveTrainMoveForward, this.forwardPIDHandler.calculatePosition(0.0, -currentDistance));
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setAnalogOperationState(Operation.DriveTrainMoveForward, 0.0);
    }

    @Override
    public void end()
    {
        super.end();
        this.setAnalogOperationState(Operation.DriveTrainMoveForward, 0.0);
    }

    @Override
    public boolean hasCompleted()
    {
        Double currentDistance = this.visionManager.getMeasuredDistance();
        if (currentDistance == null)
        {
            return false;
        }

        return super.hasCompleted() && currentDistance <= TuningConstants.MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE;
    }
}
