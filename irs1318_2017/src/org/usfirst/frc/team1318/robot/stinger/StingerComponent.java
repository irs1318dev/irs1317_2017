package org.usfirst.frc.team1318.robot.stinger;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class StingerComponent
{
    private final IMotor motor;

    @Inject
    public StingerComponent(@Named("STINGER_MOTOR") IMotor motor)
    {
        this.motor = motor;
    }

    public void setMotor(double speed)
    {
        this.motor.set(-speed);
    }

    public void stop()
    {
        this.motor.set(0.0);
    }
}
