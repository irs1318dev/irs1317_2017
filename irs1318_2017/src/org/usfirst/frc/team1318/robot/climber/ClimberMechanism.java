package org.usfirst.frc.team1318.robot.climber;

import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.Driver;
import org.usfirst.frc.team1318.robot.general.PowerManager;

import com.google.inject.Inject;

@Singleton
public class ClimberMechanism implements IMechanism
{
    private static final String LogName = "climber";

    private final IDashboardLogger logger;
    private final PowerManager powerManager;
    private final IMotor motor;

    private Driver driver;

    @Inject
    public ClimberMechanism(
        IDashboardLogger logger,
        PowerManager powerManager,
        IWpilibProvider provider)
    {
        this.logger = logger;
        this.powerManager = powerManager;

        this.motor = provider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);
    }

    @Override
    public void update()
    {
        double climberSpeed = this.driver.getAnalog(Operation.ClimberSpeed);
        double currentDraw =
            this.powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A) +
            this.powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);
        this.logger.logNumber(ClimberMechanism.LogName, "current", currentDraw);
        if (currentDraw > TuningConstants.CLIMBER_MAX_CURRENT_DRAW)
        {
            climberSpeed = 0.0;
        }

        if (climberSpeed < 0.0)
        {
            climberSpeed = 0.0;
        }
        
        climberSpeed *= TuningConstants.CLIMBER_MAX_MOTOR_POWER;

        this.logger.logNumber(ClimberMechanism.LogName, "speed", climberSpeed);
        this.motor.set(climberSpeed);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
