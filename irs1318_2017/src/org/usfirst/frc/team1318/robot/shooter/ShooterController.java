package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.PIDHandler;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ShooterController implements IController
{
    private static final String LogName = "shooter";

    private final ShooterComponent shooter;
    private final IDashboardLogger logger;
    private final ITimer timer;
    private final PIDHandler pidHandler;

    private Driver driver;

    @Inject
    public ShooterController(
        IDashboardLogger logger,
        ITimer timer,
        ShooterComponent shooter)
    {
        this.logger = logger;
        this.timer = timer;
        this.shooter = shooter;
        if (TuningConstants.SHOOTER_USE_ROBORIO_PID)
        {
            this.pidHandler = new PIDHandler(
                TuningConstants.SHOOTER_ROBORIO_PID_KP,
                TuningConstants.SHOOTER_ROBORIO_PID_KI,
                TuningConstants.SHOOTER_ROBORIO_PID_KD,
                TuningConstants.SHOOTER_ROBORIO_PID_KF,
                TuningConstants.SHOOTER_ROBORIO_PID_KS,
                TuningConstants.SHOOTER_MIN_POWER,
                TuningConstants.SHOOTER_MAX_POWER,
                this.timer);
        }
        else
        {
            this.pidHandler = null;
        }
    }

    @Override
    public void update()
    {
        boolean shooterExtendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        this.shooter.extendOrRetract(shooterExtendHood);

        double shooterSpeedPercentage = this.driver.getAnalog(Operation.ShooterSpeed);
        double shooterSpeedGoal = shooterSpeedPercentage * TuningConstants.SHOOTER_ROBORIO_PID_KS;

        int shooterTicks = this.shooter.getShooterTicks();

        double shooterPower;
        if (TuningConstants.SHOOTER_USE_CAN_PID)
        {
            shooterPower = shooterSpeedGoal;
        }
        else if (TuningConstants.SHOOTER_USE_ROBORIO_PID && shooterSpeedPercentage != 0.0)
        {
            shooterPower = this.pidHandler.calculateVelocity(shooterSpeedPercentage, shooterTicks);
        }
        else
        {
            shooterPower = shooterSpeedPercentage;
        }

        this.shooter.setShooterPower(shooterPower);

        double error = shooterSpeedGoal - this.shooter.getShooterSpeed();
        double errorPercentage = error / shooterSpeedGoal;

        this.logger.logNumber(ShooterController.LogName, "shooterSpeedGoal", shooterSpeedGoal);
        this.logger.logNumber(ShooterController.LogName, "shooterError", error);
        this.logger.logNumber(ShooterController.LogName, "shooterErrorPercentage", errorPercentage);
        boolean shooterIsUpToSpeed = true; // false;
        if (shooterSpeedPercentage != 0.0)
        {
            shooterIsUpToSpeed = Math.abs(errorPercentage) < TuningConstants.SHOOTER_ALLOWABLE_ERROR;
        }

        this.shooter.setReadyLight(shooterIsUpToSpeed);

        boolean shooterFeed = this.driver.getDigital(Operation.ShooterFeed);
        if (shooterFeed && shooterIsUpToSpeed)
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

        if (this.pidHandler != null)
        {
            this.pidHandler.reset();
        }
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
