package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

import com.google.inject.Inject;

public class ClimberController implements IController
{
    private final String LogName = "climber";
    private final IDashboardLogger logger;
    private final ClimberComponent climber;
    private final PowerManager powerManager;
    private Driver driver;

    @Inject
    public ClimberController(IDashboardLogger logger, ClimberComponent climber, PowerManager powerManager)
    {
        this.logger = logger;
        this.climber = climber;
        this.powerManager = powerManager;
    }

    @Override
    public void update()
    {
        double climberSpeed = this.driver.getAnalog(Operation.ClimberSpeed);
        double currentDraw = this.powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A) +
            this.powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);
        this.logger.logNumber(LogName, "current", currentDraw);
        if (currentDraw > TuningConstants.CLIMBER_MAX_CURRENT_DRAW)
        {
            climberSpeed = 0.0;
        }

        this.climber.setMotorSpeed(climberSpeed * TuningConstants.CLIMBER_MAX_MOTOR_POWER);
    }

    @Override
    public void stop()
    {
        this.climber.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

}
