package org.usfirst.frc.team1318.robot.intake;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

public class IntakeComponentTest
{
    @Test
    public void testExtendOrRetract_true()
    {
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeSolenoid = mock(IDoubleSolenoid.class);
        ISolenoid intakeLight = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeSolenoid, intakeLight, throughBeamSensor);

        intakeComponent.extendOrRetract(true);

        verify(intakeSolenoid).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, intakeSolenoid, intakeLight, throughBeamSensor);
    }

    @Test
    public void testExtendOrRetract_false()
    {
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid intakeSolenoid = mock(IDoubleSolenoid.class);
        ISolenoid intakeLight = mock(ISolenoid.class);
        IAnalogInput throughBeamSensor = mock(IAnalogInput.class);
        
        // Mock/fake a return value when get method is called
        doReturn(1.0).when(throughBeamSensor.getVoltage());

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeSolenoid, intakeLight, throughBeamSensor);

        intakeComponent.extendOrRetract(false);
        
        // Assert true/false when method that returns true/false is called
        assertTrue(intakeComponent.getThroughBeamBroken());
        
        // Assert double value is equal when get method is called, within acceptable range
        assertEquals(3.0/*expected*/, 3.001/*actual, the mocked return value from a get method*/, 0.001 /*allowed error*/);

        verify(intakeSolenoid).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, intakeSolenoid, intakeLight, throughBeamSensor);
    }
}
