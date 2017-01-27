package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.DashboardLogger;
import org.usfirst.frc.team1318.robot.common.Helpers;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.PIDHandler;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

import com.google.inject.Inject;

/**
 * Controller for the shooter. Needs to contain velocity PID.
 * @author Corbin
 *
 */
public class ShooterController implements IController
{
    public static final String LogName = "sh";

    private final ShooterComponent shooter;
    private final PowerManager powerManager;

    private Driver driver;
    private PIDHandler PID;

    @Inject
    public ShooterController(ShooterComponent shooter, PowerManager powerManager)
    {
        this.shooter = shooter;
        this.powerManager = powerManager;

        this.createPIDHandler();
    }

    @Override
    public void update()
    {
        boolean spin = this.driver.getDigital(Operation.ShooterSpin);

        // The actual velocity of the shooter wheel
        double currentRate = this.shooter.getCounterRate();
        int currentTicks = this.shooter.getCounterTicks();
        DashboardLogger.logNumber(ShooterController.LogName, "rate", currentRate);
        DashboardLogger.logNumber(ShooterController.LogName, "ticks", currentTicks);

        // The velocity set in the analog operation
        double velocityGoal = this.driver.getAnalog(Operation.ShooterSpeed);
        DashboardLogger.logNumber(ShooterController.LogName, "velocityGoal", velocityGoal);

        double power = 0.0;
        boolean shouldLight = false;
        if (spin)
        {
            double speedPercentage = this.shooter.getCounterRate() / TuningConstants.SHOOTER_MAX_COUNTER_RATE;
            shouldLight = velocityGoal != 0.0
                && speedPercentage > velocityGoal - TuningConstants.SHOOTER_DEVIANCE && speedPercentage < velocityGoal
                    + TuningConstants.SHOOTER_DEVIANCE;

            // Calculate the power required to reach the velocity goal     
            power = this.PID.calculateVelocity(velocityGoal, currentTicks);

            if (TuningConstants.SHOOTER_SCALE_BASED_ON_VOLTAGE)
            {
                power *= (TuningConstants.SHOOTER_VELOCITY_TUNING_VOLTAGE / this.powerManager.getVoltage());
                power = Helpers.EnforceRange(power, -TuningConstants.SHOOTER_MAX_POWER_LEVEL, TuningConstants.SHOOTER_MAX_POWER_LEVEL);
            }
        }

        this.shooter.setReadyLight(shouldLight);

        // Set the motor power with the calculated value
        this.shooter.setMotorSpeed(power);
        DashboardLogger.logNumber(ShooterController.LogName, "power", power);

        // lower the kicker whenever we are rotating in or out, or when we are performing a shot macro
        boolean lowerKicker = this.driver.getDigital(Operation.ShooterLowerKicker)
            || this.driver.getDigital(Operation.IntakeRotatingIn)
            || this.driver.getDigital(Operation.IntakeRotatingOut);

        // control the kicker:
        this.shooter.kick(!lowerKicker);

        boolean extendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        this.shooter.extendHood(extendHood);
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

    public void createPIDHandler()
    {
        this.PID = new PIDHandler(
            TuningConstants.SHOOTER_VELOCITY_PID_KP_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KI_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KD_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KF_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KS_DEFAULT,
            -TuningConstants.SHOOTER_MAX_POWER_LEVEL,
            TuningConstants.SHOOTER_MAX_POWER_LEVEL);
    }
}
