package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class GearExtendTask extends ControlTaskBase implements IControlTask
{
    public GearExtendTask()
    {
    }

    @Override
    public void begin()
    {
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
        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, true);
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.IntakeGearHolderExtend, true);
    }

    @Override
    public boolean hasCompleted()
    {
        return true;
    }

    @Override
    public boolean shouldCancel()
    {
        return false;
    }
}
