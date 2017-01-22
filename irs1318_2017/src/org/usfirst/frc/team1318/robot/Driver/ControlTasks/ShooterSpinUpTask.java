package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;

public class ShooterSpinUpTask extends TimedTask implements IControlTask
{
    private final boolean extendHood;
    private final double shooterVelocity;

    private ShooterComponent shooter;

    // True is a far shot, false is a close shot.
    public ShooterSpinUpTask(boolean extendHood, double shooterVelocity, double spinDuration)
    {
        super(spinDuration);
        this.extendHood = extendHood;
        this.shooterVelocity = shooterVelocity;
    }

    @Override
    public void begin()
    {
        super.begin();
        this.shooter = this.getInjector().getInstance(ShooterComponent.class);

        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        if (this.extendHood)
        {
            this.setDigitalOperationState(Operation.IntakeExtend, true);
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
        this.shooter.setReadyLight(false);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        this.setDigitalOperationState(Operation.ShooterExtendHood, this.extendHood);
    }

    @Override
    public boolean hasCompleted()
    {
        double speed = this.shooter.getCounterRate() / TuningConstants.SHOOTER_MAX_COUNTER_RATE;
        return (speed > this.shooterVelocity - TuningConstants.SHOOTER_DEVIANCE
            && speed < this.shooterVelocity + TuningConstants.SHOOTER_DEVIANCE)
            || super.hasCompleted();
    }

    @Override
    public void end()
    {
    }
}
