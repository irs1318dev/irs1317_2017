package org.usfirst.frc.team1318.robot.intake;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

public class IntakeComponentTest
{
    //I don't really know what I'm doing so this might all be totally garbage
    @Test
    public void testExtendGearHolder_true()
    {
        IDoubleSolenoid gearExtender = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearExtender);

        intakeComponent.extendGearHolder(true);

        verify(gearExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, intakeExtender, gearExtender);

    }

    @Test
    public void testExtendGearHolder_false()
    {
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.extendGearHolder(false);

        verify(gearholderSolenoid).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, intakeExtender, gearholderSolenoid);
    }

    @Test
    public void testExtend_false()
    {
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.extendIntake(false);

        verify(intakeExtender).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, intakeExtender, gearholderSolenoid);
    }

    @Test
    public void testExtend_true()
    {
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.extendIntake(true);

        verify(intakeExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, intakeExtender, gearholderSolenoid);
    }

    //none of this is going to work... I don't know what I'm doing
    @Test
    public void testMotorSpeed_MAX_POWER_LEVEL()
    {
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);

        verify(motor).set(eq(TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verifyNoMoreInteractions(motor, intakeExtender, gearholderSolenoid);

    }

    @Test
    public void testMotorSpeed_o()
    {
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.setMotorSpeed(0.0);

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(motor, intakeExtender, gearholderSolenoid);

    }

    @Test
    public void testStop()
    {
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid gearholderSolenoid = mock(IDoubleSolenoid.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeExtender, gearholderSolenoid);

        intakeComponent.stop();

        verify(motor).set(eq(0.0));
        verify(gearholderSolenoid).set(eq(DoubleSolenoidValue.kOff));
        verify(intakeExtender).set(eq(DoubleSolenoidValue.kOff));
        verifyNoMoreInteractions(motor, gearholderSolenoid, intakeExtender);

    }
}
