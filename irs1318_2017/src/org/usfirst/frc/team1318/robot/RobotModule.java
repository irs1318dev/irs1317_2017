package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.AnalogInputWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.CompressorWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.EncoderWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ICompressor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IPowerDistributionPanel;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.PowerDistributionPanelWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.SolenoidWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.TalonWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.VictorWrapper;
import org.usfirst.frc.team1318.robot.compressor.CompressorController;
import org.usfirst.frc.team1318.robot.drivetrain.DriveTrainController;
import org.usfirst.frc.team1318.robot.general.PositionManager;
import org.usfirst.frc.team1318.robot.general.PowerManager;
import org.usfirst.frc.team1318.robot.intake.IntakeController;
import org.usfirst.frc.team1318.robot.shooter.ShooterController;
import org.usfirst.frc.team1318.robot.stinger.StingerController;
import org.usfirst.frc.team1318.robot.vision.VisionManager;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

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
    public ICompressor getCompressor()
    {
        return new CompressorWrapper(ElectronicsConstants.PCM_B_MODULE);
    }

    @Singleton
    @Provides
    @Named("POWERMANAGER_PDP")
    public IPowerDistributionPanel getPowerManagerPdp()
    {
        return new PowerDistributionPanelWrapper();
    }

    @Singleton
    @Provides
    @Named("INTAKE_MOTOR")
    public IMotor getIntakeMotor()
    {
        return new TalonWrapper(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("INTAKE_SOLENOID")
    public IDoubleSolenoid getIntakeSolenoid()
    {
        return new DoubleSolenoidWrapper(
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("INTAKE_LIGHT")
    public ISolenoid getIntakeLight()
    {
        return new SolenoidWrapper(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_LIGHT_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("INTAKE_THROUGHBEAM")
    public IAnalogInput getIntakeThroughBeamSensor()
    {
        return new AnalogInputWrapper(ElectronicsConstants.INTAKE_THROUGH_BEAM_SENSOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_LEFTMOTOR")
    public IMotor getDriveTrainLeftMotor()
    {
        return new VictorWrapper(ElectronicsConstants.DRIVETRAIN_LEFT_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_RIGHTMOTOR")
    public IMotor getDriveTrainRightMotor()
    {
        return new VictorWrapper(ElectronicsConstants.DRIVETRAIN_RIGHT_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_LEFTENCODER")
    public IEncoder getDriveTrainLeftEncoder()
    {
        EncoderWrapper encoder = new EncoderWrapper(
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);

        encoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE);

        return encoder;
    }

    @Singleton
    @Provides
    @Named("DRIVETRAIN_RIGHTENCODER")
    public IEncoder getDriveTrainRightEncoder()
    {
        EncoderWrapper encoder = new EncoderWrapper(
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);

        encoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE);

        return encoder;
    }

    @Singleton
    @Provides
    @Named("SHOOTER_KICKER")
    public IDoubleSolenoid getShooterKicker()
    {
        return new DoubleSolenoidWrapper(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A,
            ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_HOOD")
    public IDoubleSolenoid getShooterHood()
    {
        return new DoubleSolenoidWrapper(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_MOTOR")
    public IMotor getShooterMotor()
    {
        return new TalonWrapper(ElectronicsConstants.SHOOTER_TALON_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_ENCODER")
    public IEncoder getShooterEncoder()
    {
        return new EncoderWrapper(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
    }

    @Singleton
    @Provides
    @Named("SHOOTER_LIGHT")
    public ISolenoid getShooterReadyLight()
    {
        return new SolenoidWrapper(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_READY_LIGHT_PORT);
    }

    @Singleton
    @Provides
    @Named("STINGER_MOTOR")
    public IMotor getStingerMotor()
    {
        return new TalonWrapper(ElectronicsConstants.STINGER_MOTOR_CHANNEL);
    }
}
