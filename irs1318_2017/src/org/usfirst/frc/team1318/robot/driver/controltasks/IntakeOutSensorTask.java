package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.intake.IntakeComponent;

public class IntakeOutSensorTask extends ControlTaskBase
{
    private IntakeComponent intakeComponent;
    
    public IntakeOutSensorTask() 
    {
    }
    
    @Override
    public void begin()
    {
        this.intakeComponent = this.getInjector().getInstance(IntakeComponent.class);

        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, true);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, true);
    }

    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, false);
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.IntakeIn, false);
        this.setDigitalOperationState(Operation.IntakeOut, false);
    }

    @Override
    public boolean hasCompleted()
    {
        return this.intakeComponent.getThroughBeamBroken();
    }

    @Override
    public boolean shouldCancel()
    {
        return false;
    }
}
