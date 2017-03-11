package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.CANTalonControlMode;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IRelay;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.RelayValue;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ShooterComponent
{
    private static final String LogName = "shooter";

    private final IDashboardLogger logger;
    private final IDoubleSolenoid hood;
    private final IMotor feeder;
    private final ISolenoid readyLight;
    private final IRelay targetingLight;
    private final ICANTalon shooter;

    @Inject
    public ShooterComponent(
        IDashboardLogger logger,
        ITimer timer,
        @Named("SHOOTER_HOOD") IDoubleSolenoid hood,
        @Named("SHOOTER_FEEDER") IMotor feeder,
        @Named("SHOOTER_READY_LIGHT") ISolenoid readyLight,
        @Named("SHOOTER_TARGETING_LIGHT") IRelay targetingLight,
        @Named("SHOOTER_SHOOTER") ICANTalon shooter)
    {
        this.logger = logger;

        this.hood = hood;
        this.feeder = feeder;
        this.readyLight = readyLight;
        this.targetingLight = targetingLight;
        this.shooter = shooter;
    }

    public void setShooterPower(double power, boolean usePID)
    {
        if (power == 0.0 || !usePID)
        {
            this.shooter.changeControlMode(CANTalonControlMode.PercentVbus);
        }
        else
        {
            this.shooter.changeControlMode(CANTalonControlMode.Speed);
        }

        this.logger.logNumber(ShooterComponent.LogName, "power", power);
        this.shooter.set(power);
    }

    public void setFeederPower(double power)
    {
        this.logger.logNumber(ShooterComponent.LogName, "feederPower", power);
        this.feeder.set(-power); // motor installed backwards
    }

    public int getShooterTicks()
    {
        int ticks = this.shooter.getTicks();
        this.logger.logNumber(ShooterComponent.LogName, "ticks", ticks);
        return ticks;
    }

    public double getShooterSpeed()
    {
        double speed = this.shooter.getSpeed();
        this.logger.logNumber(ShooterComponent.LogName, "speed", speed);
        return speed;
    }

    public double getShooterError()
    {
        double error = this.shooter.getError();
        this.logger.logNumber(ShooterComponent.LogName, "error", error);
        return error;
    }

    public void extendOrRetract(boolean extend)
    {
        this.logger.logString(ShooterComponent.LogName, "hood", extend ? "extend" : "retract");
        if (extend)
        {
            this.hood.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.hood.set(DoubleSolenoidValue.kReverse);
        }
    }

    public void setReadyLight(boolean on)
    {
        this.readyLight.set(on);
    }

    public void setTargetingLight(boolean on)
    {
        this.targetingLight.set(on ? RelayValue.kForward : RelayValue.kOff);
    }

    public void stop()
    {
        this.hood.set(DoubleSolenoidValue.kOff);
        this.feeder.set(0.0);
        this.readyLight.set(false);
        this.targetingLight.set(RelayValue.kOff);
        this.shooter.set(0.0);
    }
}
