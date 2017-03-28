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

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeArmExtend()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(true).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).extendArm(eq(true));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeArmRetract()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).extendArm(eq(false));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeGearHolderExtend()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).extendConveyor(eq(true));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeGearHolderRetract()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).extendConveyor(eq(false));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeIn()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_IntakeOut()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(true).when(driver).getDigital(Operation.IntakeOut);
        doReturn(false).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(-TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(false));
        verifyNoMoreInteractions(intake);
    }

    @Test
    public void testUpdate_GearIndicator()
    {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);

        doReturn(true).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(true).when(intake).getThroughBeamBroken();

        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);

        controller.update();

        verify(intake).setMotorSpeed(eq(0.0));
        verify(intake).extendArm(eq(true));
        verify(intake).extendConveyor(eq(false));
        verify(intake).getThroughBeamBroken();
        verify(intake).setIndicator(eq(true));
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
