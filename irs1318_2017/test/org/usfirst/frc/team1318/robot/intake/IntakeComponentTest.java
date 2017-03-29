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
    public void testExtendGearHolder_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendConveyor(true);

        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testExtendGearHolder_false()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendConveyor(false);

        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testExtend_false()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendArm(false);

        verify(armExtender).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testExtend_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendArm(true);

        verify(armExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }
    
    @Test
    public void testMouthExtend_true()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendMouth(true);

        verify(mouthExtender).set(eq(DoubleSolenoidValue.kForward));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }
    
    @Test
    public void testMouthExtend_false()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.extendMouth(false);

        verify(mouthExtender).set(eq(DoubleSolenoidValue.kReverse));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testMotorSpeed_MAX_POWER_LEVEL()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);

        verify(motor).set(eq(TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testMotorSpeed_0()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.setMotorSpeed(0.0);

        verify(motor).set(eq(0.0));
        verifyNoMoreInteractions(motor, armExtender, conveyorExtender, throughBeam, gearIndicator, mouthExtender);
    }

    @Test
    public void testStop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        IMotor motor = mock(IMotor.class);
        IDoubleSolenoid armExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid conveyorExtender = mock(IDoubleSolenoid.class);
        IDoubleSolenoid mouthExtender = mock(IDoubleSolenoid.class);
        IAnalogInput throughBeam = mock(IAnalogInput.class);
        ISolenoid gearIndicator = mock(ISolenoid.class);

        IntakeComponent intakeComponent = new IntakeComponent(logger, motor, armExtender, conveyorExtender, mouthExtender, throughBeam, gearIndicator);

        intakeComponent.stop();

        verify(motor).set(eq(0.0));
        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(armExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(mouthExtender).set(eq(DoubleSolenoidValue.kOff));
        verifyNoMoreInteractions(motor, conveyorExtender, armExtender, mouthExtender);
    }
}
