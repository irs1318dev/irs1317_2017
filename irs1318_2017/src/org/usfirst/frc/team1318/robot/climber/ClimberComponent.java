package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ClimberComponent
{
    private static final String LogName = "climber";

    private final IDashboardLogger logger;
    private final IMotor motor;

    @Inject
    public ClimberComponent(
        IDashboardLogger logger,
        @Named("CLIMBER_MOTOR") IMotor motor)
    {
        this.logger = logger;
        this.motor = motor;
    }

    public void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
        this.logger.logNumber(LogName, "Speed", speed);
    }

    public void stop()
    {
        this.setMotorSpeed(0.0);
    }
}
