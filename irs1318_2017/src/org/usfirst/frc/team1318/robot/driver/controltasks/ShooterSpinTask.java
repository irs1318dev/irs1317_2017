package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.shooter.ShooterComponent;

public class ShooterSpinTask extends ControlTaskBase implements IControlTask
{
    private final boolean extendHood;
    private final double shooterVelocity;

    private ShooterComponent shooter;

    public ShooterSpinTask(boolean extendHood, double shooterVelocity)
    {
        this.extendHood = extendHood;
        this.shooterVelocity = shooterVelocity;
    }

    @Override
    public void begin()
    {
        this.shooter = this.getInjector().getInstance(ShooterComponent.class);

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
