package org.usfirst.frc.team1318.robot.intake;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeControllerTest
{
    @Test
    public void testUpdate_doNothing()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingOut);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setIntakeSpeed(0.0);
        verify(intake).setIntakeLight(eq(false));
        verify(intake).getThroughBeamBroken();
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeArmExtend()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(true).when(driver).getDigital(Operation.IntakeExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingOut);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setIntakeSpeed(0.0);
        verify(intake).extendOrRetract(eq(true));
        verify(intake).setIntakeLight(eq(false));
        verify(intake).getThroughBeamBroken();
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeArmRetract()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingOut);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setIntakeSpeed(0.0);
        verify(intake).extendOrRetract(eq(false));
        verify(intake).setIntakeLight(eq(false));
        verify(intake).getThroughBeamBroken();
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeIn()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeRotatingIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingOut);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setIntakeSpeed(eq(TuningConstants.INTAKE_IN_POWER_LEVEL));
        verify(intake).setIntakeLight(eq(false));
        verify(intake).getThroughBeamBroken();
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeOut()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeRotatingIn);
        doReturn(true).when(driver).getDigital(Operation.IntakeRotatingOut);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setIntakeSpeed(eq(TuningConstants.INTAKE_OUT_POWER_LEVEL));
        verify(intake).setIntakeLight(eq(false));
        verify(intake).getThroughBeamBroken();
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testStop()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.stop();

        verify(intake).stop();
        verifyNoMoreInteractions(intake);
    }
}
