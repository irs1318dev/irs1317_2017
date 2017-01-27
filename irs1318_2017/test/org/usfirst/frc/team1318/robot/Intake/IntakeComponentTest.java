package org.usfirst.frc.team1318.robot.Intake;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.intake.IntakeComponent;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class IntakeComponentTest
{
    @Test
    public void testExtendOrRetract_true()
    {
        Talon motor = mock(Talon.class);
        DoubleSolenoid intakeSolenoid = mock(DoubleSolenoid.class);
        Solenoid intakeLight = mock(Solenoid.class);
        AnalogInput throughBeamSensor = mock(AnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeSolenoid, intakeLight, throughBeamSensor);

        intakeComponent.extendOrRetract(true);

        verify(intakeSolenoid).set(eq(Value.kForward));
        verifyNoMoreInteractions(motor, intakeSolenoid, intakeLight, throughBeamSensor);
    }

    @Test
    public void testExtendOrRetract_false()
    {
        Talon motor = mock(Talon.class);
        DoubleSolenoid intakeSolenoid = mock(DoubleSolenoid.class);
        Solenoid intakeLight = mock(Solenoid.class);
        AnalogInput throughBeamSensor = mock(AnalogInput.class);

        IntakeComponent intakeComponent = new IntakeComponent(motor, intakeSolenoid, intakeLight, throughBeamSensor);

        intakeComponent.extendOrRetract(false);

        verify(intakeSolenoid).set(eq(Value.kReverse));
        verifyNoMoreInteractions(motor, intakeSolenoid, intakeLight, throughBeamSensor);
    }
}
