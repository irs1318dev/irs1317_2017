package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class ClimberControllerTest
{
    @Test
    public void testUpdate_SetClimberSpeed_0() {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(0.0).when(driver).getAnalog(Operation.ClimberSpeed);
        
        ClimberController controller= new ClimberController(climber);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(climber).setMotorSpeed(0.0);
        verifyNoMoreInteractions(climber);
    }
    
    @Test
    public void testUpdate_SetClimberSpeed_MAX_CLIMBER_SPEED() {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        
        ClimberController controller= new ClimberController(climber);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(climber).setMotorSpeed(TuningConstants.CLIMBER_MAX_MOTOR_POWER);
        verifyNoMoreInteractions(climber);
    }
    
    @Test
    public void testUpdate_SetClimberSpeed_MIN_CLIMBER_SPEED() {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(-1.0).when(driver).getAnalog(Operation.ClimberSpeed);
        
        ClimberController controller= new ClimberController(climber);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(climber).setMotorSpeed(-TuningConstants.CLIMBER_MAX_MOTOR_POWER);
        verifyNoMoreInteractions(climber);
    }
    
    @Test
    public void testStop() {
        ClimberComponent climber = mock(ClimberComponent.class);
        Driver driver = mock(Driver.class);
        
        ClimberController controller= new ClimberController(climber);
        controller.setDriver(driver);
        
        controller.stop();
        
        verify(climber).stop();
        verifyNoMoreInteractions(climber);
    }
}
