package org.usfirst.frc.team1318.robot.climber;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

public class ClimberComponentTest
{
	@Test
	public void testSetMotorSpeed_MaxClimberPower() {
		//mock motor
			IMotor mockMotor = mock(IMotor.class);
		
		//mock dashboard logger
			IDashboardLogger mockDashboardLogger = mock(IDashboardLogger.class);
			
		//new ClimberComponent accepts logger/motor
			ClimberComponent climberComponent = new ClimberComponent(mockDashboardLogger, mockMotor);
			
		//sets speed to max motor power
			climberComponent.setMotorSpeed(TuningConstants.CLIMBER_MAX_MOTOR_POWER);
		
		//test
			verify(mockMotor).set(eq(TuningConstants.CLIMBER_MAX_MOTOR_POWER));
			
		//stop
			verifyNoMoreInteractions(mockMotor);
	}
	
	@Test
	public void testSetMotorSpeed_ZeroPower() {
		//mock motor
			IMotor mockMotor = mock(IMotor.class);
		
		//mock dashboard logger
			IDashboardLogger mockDashboardLogger = mock(IDashboardLogger.class);
			
		//new ClimberComponent accepts logger/motor
			ClimberComponent climberComponent = new ClimberComponent(mockDashboardLogger, mockMotor);
			
		//sets speed to max motor power
			climberComponent.setMotorSpeed(0);
		
		//test
			verify(mockMotor).set(eq(0));
			
		//stop
			verifyNoMoreInteractions(mockMotor);
	}
	
	@Test
	public void testStop() {
		//mock motor
			IMotor mockMotor = mock(IMotor.class);
		
		//mock dashboard logger
			IDashboardLogger mockDashboardLogger = mock(IDashboardLogger.class);
			
		//new ClimberComponent accepts logger/motor
			ClimberComponent climberComponent = new ClimberComponent(mockDashboardLogger, mockMotor);
			
		//stops 
			climberComponent.stop();
			
		//test
			verify(mockMotor).set(eq(0));
			
		//stop
			verifyNoMoreInteractions(mockMotor);
	}
}
