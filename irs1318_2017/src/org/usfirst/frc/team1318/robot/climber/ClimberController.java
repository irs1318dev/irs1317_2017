package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

import com.google.inject.Inject;

public class ClimberController implements IController
{
    private final ClimberComponent climber;
    private Driver driver;
    private final PowerManager powerManager;

    @Inject
    public ClimberController(ClimberComponent climber, PowerManager powerManager)
    {
        this.climber = climber;
        this.powerManager = powerManager;
    }

    @Override
    public void update()
    {
        double ClimberSpeed = this.driver.getAnalog(Operation.ClimberSpeed);
        double currentDraw = powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A) + 
            powerManager.getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);
        if (currentDraw > TuningConstants.CLIMBER_MAX_CURRENT_DRAW) {
            ClimberSpeed = 0.0;
        }
        this.climber.setMotorSpeed(ClimberSpeed * TuningConstants.CLIMBER_MAX_MOTOR_POWER);
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
