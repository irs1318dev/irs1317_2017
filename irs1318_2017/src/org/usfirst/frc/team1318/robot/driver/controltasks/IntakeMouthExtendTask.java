package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.IControlTask;

public class IntakeMouthExtendTask extends TimedTask implements IControlTask
{
    private final boolean extend;

    public IntakeMouthExtendTask(boolean extend, double duration)
    {
        super(duration);

        this.extend = extend;
    }

    @Override
    public void begin()
    {
        super.begin();

        this.setDigitalOperationState(Operation.IntakeMouthExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeMouthRetract, !this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeMouthExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeMouthRetract, !this.extend);
    }

    @Override
    public void stop()
    {
        super.stop();

        this.setDigitalOperationState(Operation.IntakeMouthExtend, false);
        this.setDigitalOperationState(Operation.IntakeMouthRetract, false);
    }

    @Override
    public void end()
    {
        super.end();

        this.setDigitalOperationState(Operation.IntakeMouthExtend, false);
        this.setDigitalOperationState(Operation.IntakeMouthRetract, false);
    }

    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

    @Override
    public boolean shouldCancel()
    {
        return super.shouldCancel();
    }
}
