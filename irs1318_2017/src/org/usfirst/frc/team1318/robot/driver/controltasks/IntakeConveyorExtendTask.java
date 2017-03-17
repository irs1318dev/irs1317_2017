package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeConveyorExtendTask extends TimedTask implements IControlTask
{
    private final boolean extend;

    public IntakeConveyorExtendTask(boolean extend, double duration)
    {
        super(duration);

        this.extend = extend;
    }

    @Override
    public void begin()
    {
        super.begin();

        this.setDigitalOperationState(Operation.IntakeConveyorExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeGearHolderRetract, !this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeConveyorExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeGearHolderRetract, !this.extend);
    }

    @Override
    public void stop()
    {
        super.stop();

        this.setDigitalOperationState(Operation.IntakeConveyorExtend, false);
        this.setDigitalOperationState(Operation.IntakeGearHolderRetract, false);
    }

    @Override
    public void end()
    {
        super.end();

        this.setDigitalOperationState(Operation.IntakeConveyorExtend, false);
        this.setDigitalOperationState(Operation.IntakeGearHolderRetract, false);
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
