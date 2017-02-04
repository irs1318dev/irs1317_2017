package org.usfirst.frc.team1318.robot.climber;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

public class ClimberComponentTest
{

    @Test
    public void testSetMotorSpeed_MaxClimberPower()
    {
        IMotor motor = mock(IMotor.class);

        ClimberComponent climberComponent = new ClimberComponent(motor);

        climberComponent.setMotorSpeed(TuningConstants.CLIMBER_MAX_MOTOR_POWER);

        verify(motor).set(eq(TuningConstants.CLIMBER_MAX_MOTOR_POWER));
        verifyNoMoreInteractions(motor);
    }

    @Test
    public void testSetMotorSpeed_Zero()
    {
        IMotor motor = mock(IMotor.class);

        ClimberComponent climberComponent = new ClimberComponent(motor);

        climberComponent.setMotorSpeed(0.0);

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(motor);
    }

    @Test
    public void testStop()
    {
        IMotor motor = mock(IMotor.class);

        ClimberComponent climberComponent = new ClimberComponent(motor);

        climberComponent.stop();

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(motor);
    }

}
