package org.usfirst.frc.team1318.robot.shooter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TestWpilibProvider;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilib.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IRelay;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.RelayDirection;
import org.usfirst.frc.team1318.robot.common.wpilib.RelayValue;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.Driver;

public class ShooterMechanismTest
{
    @Test
    public void updateTest_ExtendHood()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_RetractHood()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kReverse));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_TargetingLight()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterDisablePID);
        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(true).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kForward));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_SetShooterSpeed_MAX_SHOOTER_POWER_WITH_LOW_ERROR()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(1.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.999 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(TuningConstants.SHOOTER_CAN_MAX_VELOCITY));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(true));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_SetShooterSpeed_MAX_SHOOTER_POWER_WITH_HIGH_ERROR()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(1.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.5 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getSpeed();
        doReturn(0.5 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(TuningConstants.SHOOTER_CAN_MAX_VELOCITY));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_SetShooterSpeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_SetShooterFeed_MAX_FEEDER_POWER()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(true).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.1).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.101 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-TuningConstants.SHOOTER_MAX_FEEDER_POWER));
        verify(shooterMotor).set(eq(0.1 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(true));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_SetShooterFeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_getShooterError_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.5).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.6 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getSpeed();
        doReturn(0.1 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kReverse));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.5 * TuningConstants.SHOOTER_CAN_MAX_VELOCITY));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }

    @Test
    public void updateTest_Stop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        
        IDoubleSolenoid hood = testProvider.getDoubleSolenoid(
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A,
            ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        IMotor feeder = testProvider.getTalon(ElectronicsConstants.SHOOTER_FEEDER_CHANNEL);
        ISolenoid readyLight = testProvider.getSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.SHOOTER_READY_LIGHT_CHANNEL);
        IRelay targetingLight = testProvider.getRelay(
            ElectronicsConstants.SHOOTER_TARGETING_LIGHT_CHANNEL,
            RelayDirection.kForward);
        ICANTalon shooterMotor = testProvider.getCANTalon(ElectronicsConstants.SHOOTER_MASTER_MOTOR_CHANNEL);
        
        ShooterMechanism shooter = new ShooterMechanism(logger, testProvider);

        Driver driver = mock(Driver.class);
        shooter.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(false).when(driver).getDigital(Operation.ShooterTargetingLight);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);
        doReturn(0).when(shooterMotor).getTicks();
        doReturn(0.0).when(shooterMotor).getSpeed();
        doReturn(0.0).when(shooterMotor).getError();

        shooter.readSensors();
        shooter.update();

        verify(hood).set(eq(DoubleSolenoidValue.kReverse));
        verify(feeder).set(eq(-0.0));
        verify(shooterMotor).set(eq(0.0));
        verify(shooterMotor).getTicks();
        verify(shooterMotor).getSpeed();
        verify(shooterMotor).getError();
        verify(readyLight).set(eq(false));
        verify(targetingLight).set(eq(RelayValue.kOff));
        verifyNoMoreInteractions(hood, feeder, readyLight, targetingLight);
    }
}
