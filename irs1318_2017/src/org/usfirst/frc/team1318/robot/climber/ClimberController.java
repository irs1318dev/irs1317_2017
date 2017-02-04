package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ClimberController implements IController
{
    private final ClimberComponent climber;
    private Driver driver;

    @Inject
    public ClimberController(ClimberComponent climber)
    {
        this.climber = climber;
    }

    @Override
    public void update()
    {
        double ClimberSpeed = this.driver.getAnalog(Operation.ClimberSpeed);
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
