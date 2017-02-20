package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class GearExtendTask extends TimedTask implements IControlTask
{
    public GearExtendTask(double duration)
    {
        super(duration);
    }

    @Override
    public void begin()
    {
        super.begin();

        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, true);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, true);
    }

    @Override
    public void stop()
    {
        super.stop();

        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, false);
    }

    @Override
    public void end()
    {
        super.end();

        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, false);
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
