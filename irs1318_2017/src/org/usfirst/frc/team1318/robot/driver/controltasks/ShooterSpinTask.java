package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class ShooterSpinTask extends ControlTaskBase implements IControlTask
{
    private final boolean extendHood;
    private final double shooterVelocity;

    public ShooterSpinTask(boolean extendHood, double shooterVelocity)
    {
        this.extendHood = extendHood;
        this.shooterVelocity = shooterVelocity;
    }

    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.ShooterExtendHood, extendHood);
        this.setAnalogOperationState(Operation.ShooterSpeed, shooterVelocity);
    }

    @Override
    public void update()
    {
        this.setAnalogOperationState(Operation.ShooterSpeed, shooterVelocity);
        this.setDigitalOperationState(Operation.ShooterExtendHood, extendHood);
    }

    @Override
    public void stop()
    {
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
        this.setDigitalOperationState(Operation.ShooterExtendHood, false);
    }

    @Override
    public void end()
    {
    }

    @Override
    public boolean hasCompleted()
    {
        return false;
    }

    @Override
    public boolean shouldCancel()
    {
        return false;
    }

}
