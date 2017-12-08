package org.usfirst.frc.team1318.robot.climber;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.general.PowerManager;

public class ClimberControllerTest
{
	@Test
	public void testUpdate_SetClimberSpeed_0() {
		//make things
			IDashboardLogger dashboard = mock(IDashboardLogger.class);
			ClimberComponent climber = mock(ClimberComponent.class);
			PowerManager manager = mock(PowerManager.class);	
			Driver driver = mock(Driver.class);
			
			doReturn(0.0).when(getAnalog(Operation.ClimberSpeed));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B));
			
			ClimberController controller = new ClimberController(dashboard, climber, manager);
			
			controller.update();
			
			verify(climber).setMotorSpeed(0.0);
			verifyNoMoreInteractions(climber);
	}
	
	@Test
	public void testUpdate_SetClimberSpeed_MAX_CLIMBER_SPEED() {
		//make things
			IDashboardLogger dashboard = mock(IDashboardLogger.class);
			ClimberComponent climber = mock(ClimberComponent.class);
			PowerManager manager = mock(PowerManager.class);	
			Driver driver = mock(Driver.class);
			
			doReturn(0.0).when(getAnalog(Operation.ClimberSpeed));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B));
			
			ClimberController controller = new ClimberController(dashboard, climber, manager);
			
			controller.update();
			
			verify(climber).setMotorSpeed(TuningConstants.CLIMBER_MAX_MOTOR_POWER);
			verifyNoMoreInteractions(climber);
	}
	
	@Test
	public void testUpdate_SetClimberSpeed_MIN_CLIMBER_SPEED() {
		//make things
			IDashboardLogger dashboard = mock(IDashboardLogger.class);
			ClimberComponent climber = mock(ClimberComponent.class);
			PowerManager manager = mock(PowerManager.class);	
			Driver driver = mock(Driver.class);
			
			doReturn(-1.0).when(getAnalog(Operation.ClimberSpeed));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A));
			doReturn(0.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B));
			
			ClimberController controller = new ClimberController(dashboard, climber, manager);
			
			controller.update();
			
			verify(climber).setMotorSpeed(0.0);
			verifyNoMoreInteractions(climber);
	}
	
	@Test
	public void testUpdate_OverDrawCurrent() {
		//make things
			IDashboardLogger dashboard = mock(IDashboardLogger.class);
			ClimberComponent climber = mock(ClimberComponent.class);
			PowerManager manager = mock(PowerManager.class);	
			Driver driver = mock(Driver.class);
			
			doReturn(-1.0).when(getAnalog(Operation.ClimberSpeed));
			doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW/2.0 + 1.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A));
			doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW/2.0 + 1.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B));
			
			ClimberController controller = new ClimberController(dashboard, climber, manager);
			
			controller.update();
			
			verify(climber).setMotorSpeed(0.0);
			verifyNoMoreInteractions(climber);
	}
	
	@Test
	public void testStop() {
		//make things
			IDashboardLogger dashboard = mock(IDashboardLogger.class);
			ClimberComponent climber = mock(ClimberComponent.class);
			PowerManager manager = mock(PowerManager.class);	
			Driver driver = mock(Driver.class);
			
			doReturn(-1.0).when(getAnalog(Operation.ClimberSpeed));
			doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW/2.0 + 1.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_A));
			doReturn(TuningConstants.CLIMBER_MAX_CURRENT_DRAW/2.0 + 1.0).when(getCurrent(ElectronicsConstants.CLIMBER_PDP_CHANNEL_B));
			
			ClimberController controller = new ClimberController(dashboard, climber, manager);
			
			controller.setDriver(driver);
			controller.stop();
			
			verify(climber).stop();
			verifyNoMoreInteractions(climber);
	}
}
