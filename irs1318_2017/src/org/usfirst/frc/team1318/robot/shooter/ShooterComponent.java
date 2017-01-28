package org.usfirst.frc.team1318.robot.shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

@Singleton
public class ShooterComponent
{
    private final IDoubleSolenoid hood;
    private final IMotor feeder;
    private final ICANTalon shooter;
    private final ISolenoid readyLight;

    @Inject
    public ShooterComponent(
        @Named("SHOOTER_HOOD") IDoubleSolenoid Hood,
        @Named("SHOOTER_FEEDER") IMotor Feeder,
        @Named("SHOOTER_LIGHT") ISolenoid readyLight,
        @Named("SHOOTER_SHOOTER") ICANTalon shooter)
    {
        this.hood = hood;
        this.feeder = feeder;
        this.readyLight = readyLight;
        this.shooter = shooter;
    }

    public void setShooterSpeed (double power)
    {
        this.shooter.set(power;)
    }

    public void setFeederPower (double power)
    {
        this.feeder.set(power;)
    }

    public void extendOrRetract(boolean extend)
    {
        this.hood.set(extend;)
    }

    public void setReadyLight (boolean on)
    {
        this.readyLight.set(on;)
    }

    public void stop()
    {
        this.hood.set(DoubleSolenoid.Value.kOff);
        this.feeder.set(IMotor.Value.kOff);
        this.readyLight.set(false);
        this.shooter.set(ICANTalon.Value.kOff);
    }

}
