package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ShooterController implements IController
{
    private final ShooterComponent shooter;

    private Driver driver;

    @Inject
    public ShooterController(ShooterComponent shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void update()
    {
        boolean ShooterExtendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        this.shooter.extendOrRetract(ShooterExtendHood);

        boolean ShooterFeed = this.driver.getDigital(Operation.ShooterFeed);

        if (ShooterFeed)
        {
            this.shooter.setFeederPower(TuningConstants.SHOOTER_MAX_FEEDER_POWER);
        }
        else
        {
            this.shooter.setFeederPower(0.0);
        }

        double velocityGoal = this.driver.getAnalog(Operation.ShooterSpeed);

        this.shooter.setShooterPower(velocityGoal);
    }

    @Override
    public void stop()
    {
        this.shooter.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

}
