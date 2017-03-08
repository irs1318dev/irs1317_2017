package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeSpinTask extends TimedTask implements IControlTask
{
    private final Boolean in;

    public IntakeSpinTask(Boolean in, double duration)
    {
        super(duration);

        this.in = in;
    }

    @Override
    public void begin()
    {
        super.begin();

        if (this.in == null)
        {
            this.setDigitalOperationState(Operation.IntakeIn, false);
            this.setDigitalOperationState(Operation.IntakeOut, false);
        }
        else
        {
            this.setDigitalOperationState(Operation.IntakeIn, this.in);
            this.setDigitalOperationState(Operation.IntakeOut, !this.in);
        }
    }

    @Override
    public void update()
    {
        if (this.in == null)
        {
            this.setDigitalOperationState(Operation.IntakeIn, false);
            this.setDigitalOperationState(Operation.IntakeOut, false);
        }
        else
        {
            this.setDigitalOperationState(Operation.IntakeIn, this.in);
            this.setDigitalOperationState(Operation.IntakeOut, !this.in);
        }
    }

    @Override
    public void stop()
    {
        super.stop();

        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, false);
    }

    @Override
    public void end()
    {
        super.end();

        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, false);
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
