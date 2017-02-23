package org.usfirst.frc.team1318.robot.shooter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class ShooterControllerTest
{
    @Test
    public void updateTest_ExtendHood()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.0));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_RetractHood()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(false));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.0));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_SetShooterSpeed_MAX_SHOOTER_POWER_WITH_LOW_ERROR()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(1.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(TuningConstants.SHOOTER_MAX_VELOCITY));
        verify(shooter).setReadyLight(eq(true));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_SetShooterSpeed_MAX_SHOOTER_POWER_WITH_HIGH_ERROR()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(1.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(TuningConstants.SHOOTER_MAX_VELOCITY));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_SetShooterSpeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.0));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_SetShooterFeed_MAX_FEEDER_POWER()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(true).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.1).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(TuningConstants.SHOOTER_MAX_FEEDER_POWER));
        verify(shooter).setShooterPower(eq(0.1 * TuningConstants.SHOOTER_MAX_VELOCITY));
        verify(shooter).setReadyLight(eq(true));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_SetShooterFeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(true).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(true));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.0));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_getShooterError_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.5).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(false));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.5 * TuningConstants.SHOOTER_MAX_VELOCITY));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }

    @Test
    public void updateTest_Stop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        ShooterComponent shooter = mock(ShooterComponent.class);
        ShooterController shooterController = new ShooterController(logger, timer, shooter);

        Driver driver = mock(Driver.class);
        shooterController.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ShooterExtendHood);
        doReturn(false).when(driver).getDigital(Operation.ShooterFeed);
        doReturn(0.0).when(driver).getAnalog(Operation.ShooterSpeed);

        shooterController.update();

        verify(shooter).extendOrRetract(eq(false));
        verify(shooter).setFeederPower(eq(0.0));
        verify(shooter).setShooterPower(eq(0.0));
        verify(shooter).setReadyLight(eq(false));
        verifyNoMoreInteractions(shooter);
    }
}
