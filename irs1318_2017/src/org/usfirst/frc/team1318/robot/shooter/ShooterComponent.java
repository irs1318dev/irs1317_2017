package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Component for the shooter mechanism. Has a talon, and one counter, 
 * a kicker that loads the ball, an actuating hood, a targeting light, and a light to show when it is ready to fire.
 * @author Corbin
 *
 */
@Singleton
public class ShooterComponent
{
    private final IDoubleSolenoid kicker;
    private final IDoubleSolenoid hood;
    private final IMotor motor;
    private final IEncoder encoder;
    private final ISolenoid readyLight;

    @Inject
    public ShooterComponent(
        @Named("SHOOTER_KICKER") IDoubleSolenoid kicker,
        @Named("SHOOTER_HOOD") IDoubleSolenoid hood,
        @Named("SHOOTER_MOTOR") IMotor motor,
        @Named("SHOOTER_ENCODER") IEncoder encoder,
        @Named("SHOOTER_LIGHT") ISolenoid readyLight)
    {
        this.kicker = kicker;
        this.hood = hood;
        this.motor = motor;
        this.encoder = encoder;
        this.readyLight = readyLight;
    }

    public void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
    }

    public int getCounterTicks()
    {
        int counterTicks = this.encoder.get();
        return counterTicks;
    }

    public double getCounterRate()
    {
        double counterRate = this.encoder.getRate();
        return counterRate;
    }

    /**
     * Actuates the kicker. 
     * 
     * @param up - true is up, false is down
     */
    public void kick(boolean up)
    {
        if (up)
        {
            this.kicker.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.kicker.set(DoubleSolenoidValue.kReverse);
        }
    }

    /**
     * Extends or retracts the hood.
     * 
     * @param up - true extends, false retracts
     */
    public void extendHood(boolean up)
    {
        if (up)
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
        this.kicker.set(DoubleSolenoidValue.kOff);
        this.hood.set(DoubleSolenoidValue.kOff);
        this.setMotorSpeed(0.0);
        this.readyLight.set(false);
    }
}
