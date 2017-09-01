package org.usfirst.frc.team1318.robot.shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.CANTalonControlMode;
import org.usfirst.frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilib.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IRelay;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.common.wpilib.RelayDirection;
import org.usfirst.frc.team1318.robot.common.wpilib.RelayValue;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.Driver;

import com.google.inject.Inject;

public class ShooterMechanism implements IMechanism
{
    private static final String LogName = "shooter";

    private final IDashboardLogger logger;

    private final IDoubleSolenoid hood;
    private final IMotor feeder;
    private final ISolenoid readyLight;
    private final IRelay targetingLight;
    private final ICANTalon shooter;

    private boolean feederWait;
    private boolean usePID;
    private Driver driver;

    @Inject
    public ShooterMechanism(
        IDashboardLogger logger,
        IWpilibProvider provider)
    {
        this.logger = logger;

        this.hood = provider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        this.feeder = provider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        this.readyLight = provider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        this.targetingLight = provider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        this.shooter = provider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        this.shooter.enableBrakeMode(false);
        this.shooter.reverseSensor(false);

        ICANTalon follower = provider.getCANTalon(ElectronicsConstants.SHOOTER_FOLLOWER_MOTOR_CHANNEL);
        follower.enableBrakeMode(false);
        follower.reverseOutput(true);
        follower.changeControlMode(CANTalonControlMode.Follower);
        follower.set(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);

        if (TuningConstants.SHOOTER_USE_CAN_PID)
        {
        	this.shooter.changeControlMode(CANTalonControlMode.Speed);
            this.shooter.setPIDF(
                TuningConstants.SHOOTER_CAN_PID_KP,
                TuningConstants.SHOOTER_CAN_PID_KI,
                TuningConstants.SHOOTER_CAN_PID_KD,
                TuningConstants.SHOOTER_CAN_PID_KF);
        }
        else
        {
        	this.shooter.changeControlMode(CANTalonControlMode.PercentVbus);
        };

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
        this.extendHood(shooterExtendHood || isClimberRunning);

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

        this.setShooterPower(shooterPower, this.usePID);

        // determine if shooter is up to speed, set ready indicator light
        this.getShooterTicks();
        this.getShooterSpeed();
        double error = this.getShooterError();
        double errorPercentage = error / shooterSpeedGoal;
        this.logger.logNumber(ShooterMechanism.LogName, "speedGoal", shooterSpeedGoal);
        this.logger.logNumber(ShooterMechanism.LogName, "error%", errorPercentage * 100.0);
        boolean shooterIsUpToSpeed = false;
        if (shooterSpeedPercentage != 0.0)
        {
            shooterIsUpToSpeed = Math.abs(errorPercentage) < TuningConstants.SHOOTER_ALLOWABLE_ERROR;
        }

        this.setReadyLight(shooterIsUpToSpeed);

        // enable/disable targeting light
        boolean targetingLightOn = this.driver.getDigital(Operation.ShooterTargetingLight);
        this.setTargetingLight(targetingLightOn);

        // enable/disable feeding
        boolean shooterFeed = this.driver.getDigital(Operation.ShooterFeed);
        if (shooterFeed && (!this.feederWait || shooterIsUpToSpeed))
        {
            this.setFeederPower(TuningConstants.SHOOTER_MAX_FEEDER_POWER);
        }
        else
        {
            this.setFeederPower(0.0);
        }
    }

    @Override
    public void stop()
    {
        this.hood.set(DoubleSolenoidValue.kOff);
        this.feeder.set(0.0);
        this.readyLight.set(false);
        this.targetingLight.set(RelayValue.kOff);
        this.shooter.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public int getShooterTicks()
    {
        int ticks = this.shooter.getTicks();
        this.logger.logNumber(ShooterMechanism.LogName, "ticks", ticks);
        return ticks;
    }

    public double getShooterSpeed()
    {
        double speed = this.shooter.getSpeed();
        this.logger.logNumber(ShooterMechanism.LogName, "speed", speed);
        return speed;
    }

    public double getShooterError()
    {
        double error = this.shooter.getError();
        this.logger.logNumber(ShooterMechanism.LogName, "error", error);
        return error;
    }

    private void setShooterPower(double power, boolean usePID)
    {
        if (power == 0.0 || !usePID)
        {
            this.shooter.changeControlMode(CANTalonControlMode.PercentVbus);
        }
        else
        {
            this.shooter.changeControlMode(CANTalonControlMode.Speed);
        }

        this.logger.logNumber(ShooterMechanism.LogName, "power", power);
        this.shooter.set(power);
    }

    private void setFeederPower(double power)
    {
        this.logger.logNumber(ShooterMechanism.LogName, "feederPower", power);
        this.feeder.set(-power); // motor installed backwards
    }

    private void extendHood(boolean extend)
    {
        this.logger.logString(ShooterMechanism.LogName, "hood", extend ? "extend" : "retract");
        if (extend)
        {
            this.hood.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.hood.set(DoubleSolenoidValue.kReverse);
        }
    }

    private void setReadyLight(boolean on)
    {
        this.readyLight.set(on);
    }

    private void setTargetingLight(boolean on)
    {
        this.targetingLight.set(on ? RelayValue.kForward : RelayValue.kOff);
    }
}
