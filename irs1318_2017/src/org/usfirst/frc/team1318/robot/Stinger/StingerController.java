package org.usfirst.frc.team1318.robot.Stinger;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import com.google.inject.Inject;

public class StingerController implements IController
{
    private final StingerComponent stinger;

    private Driver driver;

    @Inject
    public StingerController(StingerComponent stinger)
    {
        this.stinger = stinger;
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(Operation.StingerOut))
        {
            this.stinger.setMotor(TuningConstants.STINGER_MAX_VELOCTIY);
        }
        else if (this.driver.getDigital(Operation.StingerIn))
        {
            this.stinger.setMotor(-TuningConstants.STINGER_MAX_VELOCTIY);
        }
        else
        {
            this.stinger.setMotor(TuningConstants.STINGER_SLOW_BACK_VELOCTIY);
        }
    }

    @Override
    public void stop()
    {
        this.stinger.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

}
