package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeExtendTask extends TimedTask implements IControlTask
{
    private final boolean extend;

    public IntakeExtendTask(boolean extend, double duration)
    {
        super(duration);

        this.extend = extend;
    }

    @Override
    public void begin()
    {
        super.begin();

        this.setDigitalOperationState(Operation.IntakeArmExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeArmRetract, !this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeArmExtend, this.extend);
        this.setDigitalOperationState(Operation.IntakeArmRetract, !this.extend);
    }

    @Override
    public void stop()
    {
        super.stop();

        this.setDigitalOperationState(Operation.IntakeArmExtend, false);
        this.setDigitalOperationState(Operation.IntakeArmRetract, false);
    }

    @Override
    public void end()
    {
        super.end();

        this.setDigitalOperationState(Operation.IntakeArmExtend, false);
        this.setDigitalOperationState(Operation.IntakeArmRetract, false);
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
