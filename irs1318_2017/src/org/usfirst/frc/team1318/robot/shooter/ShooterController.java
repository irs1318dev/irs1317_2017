package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ShooterController implements IController
{
    private static final String LogName = "shooter";

    private final ShooterComponent shooter;
    private final IDashboardLogger logger;

    private boolean feederWait;
    private boolean usePID;
    private Driver driver;

    @Inject
    public ShooterController(
        IDashboardLogger logger,
        ShooterComponent shooter)
    {
        this.logger = logger;
        this.shooter = shooter;
        this.feederWait = TuningConstants.SHOOTER_USE_CAN_PID;
        this.usePID = TuningConstants.SHOOTER_USE_CAN_PID;
    }

    @Override
    public void update()
    {
        // enable/disable PID, feeder-wait
        if (this.driver.getDigital(Operation.ShooterEnablePID))
        {
            this.usePID = true;
        }
        else if (this.driver.getDigital(Operation.ShooterDisablePID))
        {
            this.feederWait = false;
            this.usePID = false;
        }

        if (this.driver.getDigital(Operation.ShooterEnableFeederWait))
        {
            this.feederWait = true;
        }
        else if (this.driver.getDigital(Operation.ShooterDisableFeederWait))
        {
            this.feederWait = false;
        }

        // set hood extend/retract
        boolean shooterExtendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        boolean isClimberRunning = this.driver.getAnalog(Operation.ClimberSpeed) > 0.01;
        this.shooter.extendHood(shooterExtendHood || isClimberRunning);

        // calculate and apply desired shooter setting
        double shooterSpeedPercentage = this.driver.getAnalog(Operation.ShooterSpeed);
        double shooterSpeedGoal = shooterSpeedPercentage * TuningConstants.SHOOTER_CAN_MAX_VELOCITY;
        double shooterPower;
        if (this.usePID)
        {
            shooterPower = shooterSpeedGoal;
        }
        else
        {
            shooterPower = shooterSpeedPercentage;
        }

        this.shooter.setShooterPower(shooterPower, this.usePID);

        // determine if shooter is up to speed, set ready indicator light
        this.shooter.getShooterTicks();
        this.shooter.getShooterSpeed();
        double error = this.shooter.getShooterError();
        double errorPercentage = error / shooterSpeedGoal;
        this.logger.logNumber(ShooterController.LogName, "speedGoal", shooterSpeedGoal);
        this.logger.logNumber(ShooterController.LogName, "error%", errorPercentage * 100.0);
        boolean shooterIsUpToSpeed = false;
        if (shooterSpeedPercentage != 0.0)
        {
            shooterIsUpToSpeed = Math.abs(errorPercentage) < TuningConstants.SHOOTER_ALLOWABLE_ERROR;
        }

        this.shooter.setReadyLight(shooterIsUpToSpeed);

        // enable/disable targeting light
        boolean targetingLightOn = this.driver.getDigital(Operation.ShooterTargetingLight);
        this.shooter.setTargetingLight(targetingLightOn);

        // enable/disable feeding
        boolean shooterFeed = this.driver.getDigital(Operation.ShooterFeed);
        if (shooterFeed && (!this.feederWait || shooterIsUpToSpeed))
        {
            this.shooter.setFeederPower(TuningConstants.SHOOTER_MAX_FEEDER_POWER);
        }
        else
        {
            this.shooter.setFeederPower(0.0);
        }
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
