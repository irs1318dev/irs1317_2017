package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeControllerTest
{
    @Test
    public void testUpdate_doNothing() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(0.0);
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeArmExtend() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(true).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(0.0);
        verify(intake).extendIntake(eq(true));
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeArmRetract() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(0.0);
        verify(intake).retractIntake(eq(true));
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeGearHolderExtend() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(0.0);
        verify(intake).extendGearHolder(eq(true));
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeGearHolderRetract() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(0.0);
        verify(intake).retractGearHolder(eq(true));
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeIn() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        verifyNoMoreInteractions(intake);
    }
    
    @Test
    public void testUpdate_IntakeOut() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeGearHolderRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(true).when(driver).getDigital(Operation.IntakeOut);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.update();
        
        verify(intake).setIntakePower(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        verifyNoMoreInteractions(intake);
    }
    
    @test
    public void testStop() {
        IntakeComponent intake = mock(IntakeComponent.class);
        Driver driver = mock(Driver.class);
        
        IntakeController controller = new IntakeController(intake);
        controller.setDriver(driver);
        
        controller.stop();
        
        verify(intake).stop();
        verifyNoMoreInteractions(intake);
    }

}
