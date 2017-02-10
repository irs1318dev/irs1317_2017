package org.usfirst.frc.team1318.robot.climber;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

public class ClimberControllerTest
{
    @Test
    public void testUpdate_SetClimberSpeed_0()
    {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        PowerManager powerManager = mock(PowerManager.class);

        doReturn(0.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberController controller = new ClimberController(climber, powerManager);
        controller.setDriver(driver);

        controller.update();

        verify(climber).setMotorSpeed(0.0);
        verifyNoMoreInteractions(climber);
    }

    @Test
    public void testUpdate_SetClimberSpeed_MAX_CLIMBER_SPEED()
    {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        PowerManager powerManager = mock(PowerManager.class);

        doReturn(1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberController controller = new ClimberController(climber, powerManager);
        controller.setDriver(driver);

        controller.update();

        verify(climber).setMotorSpeed(TuningConstants.CLIMBER_MAX_MOTOR_POWER);
        verifyNoMoreInteractions(climber);
    }

    @Test
    public void testUpdate_SetClimberSpeed_MIN_CLIMBER_SPEED()
    {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        PowerManager powerManager = mock(PowerManager.class);

        doReturn(-1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(0.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberController controller = new ClimberController(climber, powerManager);
        controller.setDriver(driver);

        controller.update();

        verify(climber).setMotorSpeed(-TuningConstants.CLIMBER_MAX_MOTOR_POWER);
        verifyNoMoreInteractions(climber);
    }

    @Test
    public void testUpdate_OverDrawCurrent()
    {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        PowerManager powerManager = mock(PowerManager.class);

        doReturn(-1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        doReturn(1.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A);
        doReturn(1.0).when(powerManager).getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B);

        ClimberController controller = new ClimberController(climber, powerManager);
        controller.setDriver(driver);

        controller.update();

        verify(climber).setMotorSpeed(0.0);
        verifyNoMoreInteractions(climber);
    }

    @Test
    public void testStop()
    {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        PowerManager powerManager = mock(PowerManager.class);

        ClimberController controller = new ClimberController(climber, powerManager);
        controller.setDriver(driver);

        controller.stop();

        verify(climber).stop();
        verifyNoMoreInteractions(climber);
    }
}
