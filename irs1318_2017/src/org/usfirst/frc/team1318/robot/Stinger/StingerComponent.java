package org.usfirst.frc.team1318.robot.Stinger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import edu.wpi.first.wpilibj.Talon;

@Singleton
public class StingerComponent
{
    private final Talon talon;

    @Inject
    public StingerComponent(@Named("STINGER_MOTOR") Talon motor)
    {
        this.talon = motor;
    }

    public void setMotor(double speed)
    {
        this.talon.set(-speed);
    }

    public void stop()
    {
        this.talon.set(0.0);
    }
}
