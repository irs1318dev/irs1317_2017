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

    private boolean feederWait;
    private boolean usePID;
    private PIDHandler pidHandler;
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
        this.feederWait = TuningConstants.SHOOTER_USE_ROBORIO_PID;
        this.usePID = TuningConstants.SHOOTER_USE_ROBORIO_PID;

        this.createPIDHandler();
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(Operation.ShooterEnablePID))
        {
            this.usePID = true;
            this.createPIDHandler();
        }
        else if (this.driver.getDigital(Operation.ShooterDisablePID))
        {
            this.feederWait = false;
            this.usePID = false;
            this.createPIDHandler();
        }

        if (this.driver.getDigital(Operation.ShooterEnableFeederWait))
        {
            this.feederWait = true;
        }
        else if (this.driver.getDigital(Operation.ShooterDisableFeederWait))
        {
            this.feederWait = false;
        }

        boolean shooterExtendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        this.shooter.extendOrRetract(shooterExtendHood);

        double shooterSpeedPercentage = this.driver.getAnalog(Operation.ShooterSpeed);
        double shooterSpeedGoal = shooterSpeedPercentage * TuningConstants.SHOOTER_MAX_VELOCITY;

        int shooterTicks = this.shooter.getShooterTicks();

        double shooterPower;
        if (this.usePID && shooterSpeedPercentage != 0.0)
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
        boolean shooterIsUpToSpeed = false;
        if (shooterSpeedPercentage != 0.0)
        {
            shooterIsUpToSpeed = Math.abs(errorPercentage) < TuningConstants.SHOOTER_ALLOWABLE_ERROR;
        }

        this.shooter.setReadyLight(shooterIsUpToSpeed);

        boolean targetingLightOn = this.driver.getDigital(Operation.ShooterTargetingLight);
        this.shooter.setTargetingLight(targetingLightOn);

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

    private void createPIDHandler()
    {
        if (this.usePID)
        {
            this.pidHandler = new PIDHandler(
                TuningConstants.SHOOTER_ROBORIO_PID_KP,
                TuningConstants.SHOOTER_ROBORIO_PID_KI,
                TuningConstants.SHOOTER_ROBORIO_PID_KD,
                TuningConstants.SHOOTER_ROBORIO_PID_KF,
                TuningConstants.SHOOTER_ROBORIO_PID_KS,
                TuningConstants.SHOOTER_MIN_POWER,
                TuningConstants.SHOOTER_MAX_POWER,
                "shooter",
                this.logger,
                this.timer);
        }
        else
        {
            this.pidHandler = null;
        }
    }
}
