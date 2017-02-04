package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.CANTalonControlMode;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ShooterComponent
{
    private final IDoubleSolenoid hood;
    private final IMotor feeder;
    private final ICANTalon shooter;
    private final ISolenoid readyLight;

    @Inject
    public ShooterComponent(
        @Named("SHOOTER_HOOD") IDoubleSolenoid hood,
        @Named("SHOOTER_FEEDER") IMotor feeder,
        @Named("SHOOTER_LIGHT") ISolenoid readyLight,
        @Named("SHOOTER_SHOOTER") ICANTalon shooter)
    {
        this.hood = hood;
        this.feeder = feeder;
        this.readyLight = readyLight;
        this.shooter = shooter;
    }

    public void setShooterPower(double power)
    {
        if (power == 0.0)
        {
            this.shooter.changeControlMode(CANTalonControlMode.Voltage);
        }
        else
        {
            this.shooter.changeControlMode(CANTalonControlMode.Speed);
        }

        this.shooter.set(power);
    }

    public void setFeederPower(double power)
    {
        this.feeder.set(power);
    }

    public double getShooterError()
    {
        return this.shooter.getError();
    }

    public void extendOrRetract(boolean extend)
    {
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

    public void stop()
    {
        this.hood.set(DoubleSolenoidValue.kOff);
        this.feeder.set(0.0);
        this.readyLight.set(false);
        this.shooter.set(0.0);
    }
}
