package org.usfirst.frc.team1318.robot.climber;

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
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

public class ClimberMechanismTest
{
    @Test
    public void testUpdate_SetClimberSpeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        PowerManager powerManager = mock(PowerManager.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor climberMotor = testProvider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(0.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberMechanism climber = new ClimberMechanism(logger, powerManager, testProvider);
        climber.setDriver(driver);

        climber.update();

        verify(climberMotor).set(eq(0.0));
        verifyNoMoreInteractions(climberMotor);
    }

    @Test
    public void testUpdate_SetClimberSpeed_MAX_CLIMBER_SPEED()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        PowerManager powerManager = mock(PowerManager.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor climberMotor = testProvider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberMechanism climber = new ClimberMechanism(logger, powerManager, testProvider);
        climber.setDriver(driver);

        climber.update();

        verify(climberMotor).set(eq(TuningConstants.CLIMBER_MAX_MOTOR_POWER));
        verifyNoMoreInteractions(climberMotor);
    }

    @Test
    public void testUpdate_SetClimberSpeed_MIN_CLIMBER_SPEED()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        PowerManager powerManager = mock(PowerManager.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor climberMotor = testProvider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(-1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberMechanism climber = new ClimberMechanism(logger, powerManager, testProvider);
        climber.setDriver(driver);

        climber.update();

        verify(climberMotor).set(eq(0.0));
        verifyNoMoreInteractions(climberMotor);
    }

    @Test
    public void testUpdate_OverDrawCurrent()
    {
    	IDashboardLogger logger = mock(IDashboardLogger.class);
        PowerManager powerManager = mock(PowerManager.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor climberMotor = testProvider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(-1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW / 2.0 + 1.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW / 2.0 + 1.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberMechanism climber = new ClimberMechanism(logger, powerManager, testProvider);
        climber.setDriver(driver);

        climber.update();

        verify(climberMotor).set(eq(0.0));
        verifyNoMoreInteractions(climberMotor);
    }

    @Test
    public void testStop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        PowerManager powerManager = mock(PowerManager.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor climberMotor = testProvider.getTalon(ElectronicsConstants.CLIMBER_MOTOR_CHANNEL);

        Driver driver = mock(Driver.class);

        ClimberMechanism climber = new ClimberMechanism(logger, powerManager, testProvider);
        climber.setDriver(driver);

        climber.stop();

        verify(climberMotor).set(eq(0.0));
        verifyNoMoreInteractions(climberMotor);
    }
}
