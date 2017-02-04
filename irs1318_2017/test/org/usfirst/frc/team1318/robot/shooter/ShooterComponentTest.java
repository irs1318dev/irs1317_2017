package org.usfirst.frc.team1318.robot.shooter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

public class ShooterComponentTest
{
    @Test
    public void testSetFeederPower_MAX_FEEDER_POWER()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setFeederPower(TuningConstants.SHOOTER_MAX_FEEDER_POWER);

        verify(feeder).set(eq(TuningConstants.SHOOTER_MAX_FEEDER_POWER));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);

    }

    @Test
    public void testSetFeederPower_0()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setFeederPower(0.0);

        verify(feeder).set(eq(0.0));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);

    }

    @Test
    public void testSetShooterPower_MAX_SHOOTER_POWER()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setShooterPower(TuningConstants.SHOOTER_MAX_SHOOTER_POWER);

        verify(shooter).set(eq(TuningConstants.SHOOTER_MAX_SHOOTER_POWER));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testSetShooterSpeed_0()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setShooterPower(0.0);

        verify(shooter).set(eq(0.0));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testExtendOrRetract_true()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.extendOrRetract(true);

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testExtendOrRetract_false()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.extendOrRetract(false);

        verify(hood).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testSetReadyLight_on()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setReadyLight(true);

        verify(readyLight).set(eq(true));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testSetReadyLight_off()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.setReadyLight(false);

        verify(readyLight).set(eq(false));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testStop()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);

        shooterComponent.stop();

        verify(hood).set(eq(DoubleSolenoidValue.kOff));
        verify(feeder).set(eq(0.0));
        verify(readyLight).set(eq(false));
        verify(shooter).set(eq(0.0));
        verifyNoMoreInteractions(hood, feeder, shooter, readyLight);
    }

    @Test
    public void testGetShooterError_0()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        doReturn(0.0).when(shooter).getError();

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);
        double error = shooterComponent.getShooterError();
        assertEquals(0.0, error, 0.001);

    }

    @Test
    public void testGetShooterError_1()
    {
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor feeder = mock(IMotor.class);
        ICANTalon shooter = mock(ICANTalon.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        doReturn(1.0).when(shooter).getError();

        ShooterComponent shooterComponent = new ShooterComponent(hood, feeder, readyLight, shooter);
        double error = shooterComponent.getShooterError();
        assertEquals(1.0, error, 0.001);

    }

}
