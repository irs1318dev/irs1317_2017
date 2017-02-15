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
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

public class ShooterComponentTest
{
    @Test
    public void testSetShooterPower_MAX_SHOOTER_POWER()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.setMotorSpeed(TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY);

        verify(motor).set(eq(TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testSetShooterPower_0()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.setMotorSpeed(0.0);

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testExtendHood_true()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.extendHood(true);

        verify(hood).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testExtendOrRetract_false()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.extendHood(false);

        verify(hood).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testSetReadyLight_on()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.setReadyLight(true);

        verify(readyLight).set(eq(true));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testSetReadyLight_off()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.setReadyLight(false);

        verify(readyLight).set(eq(false));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testStop()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        shooterComponent.stop();

        verify(kicker).set(eq(DoubleSolenoidValue.kOff));
        verify(hood).set(eq(DoubleSolenoidValue.kOff));
        verify(motor).set(eq(0.0));
        verify(readyLight).set(eq(false));
        verifyNoMoreInteractions(kicker, hood, motor, encoder, readyLight);
    }

    @Test
    public void testShooter_0()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        doReturn(0.0).when(encoder).getRate();

        double rate = shooterComponent.getCounterRate();
        assertEquals(0.0, rate, 0.001);
    }

    @Test
    public void testShooter_1()
    {
        IDoubleSolenoid kicker = mock(IDoubleSolenoid.class);
        IDoubleSolenoid hood = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IEncoder encoder = mock(IEncoder.class);
        ISolenoid readyLight = mock(ISolenoid.class);

        ShooterComponent shooterComponent = new ShooterComponent(kicker, hood, motor, encoder, readyLight);

        doReturn(1.0).when(encoder).getRate();

        double rate = shooterComponent.getCounterRate();
        assertEquals(1.0, rate, 0.001);
    }
}
