package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Compressor.CompressorController;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainController;
import org.usfirst.frc.team1318.robot.General.PositionManager;
import org.usfirst.frc.team1318.robot.General.PowerManager;
import org.usfirst.frc.team1318.robot.Intake.IntakeController;
import org.usfirst.frc.team1318.robot.Shooter.ShooterController;
import org.usfirst.frc.team1318.robot.Stinger.StingerController;
import org.usfirst.frc.team1318.robot.Vision.VisionManager;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class RobotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
    }

    @Singleton
    @Provides
    public ControllerManager getControllerManager(Injector injector)
    {
        List<IController> controllerList = new ArrayList<>();
        controllerList.add(injector.getInstance(PowerManager.class));
        controllerList.add(injector.getInstance(PositionManager.class));
        controllerList.add(injector.getInstance(VisionManager.class));
        controllerList.add(injector.getInstance(CompressorController.class));
        controllerList.add(injector.getInstance(DriveTrainController.class));
        controllerList.add(injector.getInstance(IntakeController.class));
        controllerList.add(injector.getInstance(ShooterController.class));
        controllerList.add(injector.getInstance(StingerController.class));
        return new ControllerManager(controllerList);
    }

    @Singleton
    @Provides
    @Named("COMPRESSOR")
    public Compressor getCompressor()
    {
        return new Compressor(ElectronicsConstants.PCM_B_MODULE);
    }

    @Singleton
    @Provides
    @Named("POWERMANAGER_PDP")
    public PowerDistributionPanel getPowerManagerPdp()
    {
        return new PowerDistributionPanel();
    }

    @Singleton
    @Provides
    @Named("INTAKE_MOTOR")
    public Talon getIntakeMotor()
    {
        return new Talon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("INTAKE_SOLENOID")
    public DoubleSolenoid getIntakeSolenoid()
    {
        return new DoubleSolenoid(
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("INTAKE_LIGHT")
    public Solenoid getIntakeLight()
    {
        return new Solenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_LIGHT_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("INTAKE_THROUGHBEAM")
    public AnalogInput getIntakeThroughBeamSensor()
    {
        return new AnalogInput(ElectronicsConstants.INTAKE_THROUGH_BEAM_SENSOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_LEFTMOTOR")
    public Victor getDriveTrainLeftMotor()
    {
        return new Victor(ElectronicsConstants.DRIVETRAIN_LEFT_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_RIGHTMOTOR")
    public Victor getDriveTrainRightMotor()
    {
        return new Victor(ElectronicsConstants.DRIVETRAIN_RIGHT_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_LEFTENCODER")
    public Encoder getDriveTrainLeftEncoder()
    {
        Encoder encoder = new Encoder(
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);

        encoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE);

        return encoder;
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_RIGHTENCODER")
    public Encoder getDriveTrainRightEncoder()
    {
        Encoder encoder = new Encoder(
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);

        encoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE);

        return encoder;
    }

    @Singleton
    @Provides
    @Named("SHOOTER_KICKER")
    public DoubleSolenoid getShooterKicker()
    {
        return new DoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A,
            ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_HOOD")
    public DoubleSolenoid getShooterHood()
    {
        return new DoubleSolenoid(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_MOTOR")
    public Talon getShooterMotor()
    {
        return new Talon(ElectronicsConstants.SHOOTER_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_ENCODER")
    public Encoder getShooterEncoder()
    {
        return new Encoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_LIGHT")
    public Solenoid getShooterReadyLight()
    {
        return new Solenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_READY_LIGHT_PORT);
    }

    @Singleton
    @Provides
    @Named("STINGER_MOTOR")
    public Talon getStingerMotor()
    {
        return new Talon(ElectronicsConstants.STINGER_MOTOR_CHANNEL);
    }
}
