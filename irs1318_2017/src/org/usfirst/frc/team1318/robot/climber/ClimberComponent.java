package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ClimberComponent
{
    private IMotor motor;
    private final IDashboardLogger logger;
    private final String key = "climber";

    @Inject
    public ClimberComponent(IDashboardLogger logger, @Named("CLIMBER_MOTOR") IMotor motor)
    {
        this.logger = logger;
        this.motor = motor;
    }

    public void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
        this.logger.logNumber(key, "climberSpeed", speed);
    }

    public void stop()
    {
        this.setMotorSpeed(0.0);
    }
}
