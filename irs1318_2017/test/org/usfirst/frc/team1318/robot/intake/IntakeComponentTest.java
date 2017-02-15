package org.usfirst.frc.team1318.robot.intake;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

public class IntakeComponentTest
{
    @Test
    public void testExtend_false()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);
        ISolenoid light = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, intakeExtender, light, throughBeamSensor);

        intakeComponent.extendOrRetract(false);

        verify(intakeExtender).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, intakeExtender, light);
    }

    @Test
    public void testExtend_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);
        ISolenoid light = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, intakeExtender, light, throughBeamSensor);

        intakeComponent.extendOrRetract(true);

        verify(intakeExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, intakeExtender, light);
    }

    @Test
    public void testMotorSpeed_MAX_POWER_LEVEL()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);
        ISolenoid light = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, intakeExtender, light, throughBeamSensor);

        intakeComponent.setIntakeSpeed(TuningConstants.INTAKE_IN_POWER_LEVEL);

        verify(motor).set(eq(TuningConstants.INTAKE_IN_POWER_LEVEL));
        verifyNoMoreInteractions(motor, intakeExtender, light);
    }

    @Test
    public void testMotorSpeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);
        ISolenoid light = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, intakeExtender, light, throughBeamSensor);

        intakeComponent.setIntakeSpeed(0.0);

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(motor, intakeExtender, light);
    }

    @Test
    public void testStop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeExtender = mock(IDoubleSolenoid.class);
        ISolenoid light = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, intakeExtender, light, throughBeamSensor);

        intakeComponent.stop();

        verify(motor).set(eq(0.0));
        verify(intakeExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(light).set(eq(false));
        verifyNoMoreInteractions(motor, intakeExtender, light);
    }
}
