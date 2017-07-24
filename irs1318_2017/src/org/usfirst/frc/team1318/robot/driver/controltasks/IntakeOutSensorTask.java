package org.usfirst.frc.team1318.robot.driver.controltasks;

import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.intake.IntakeMechanism;

public class IntakeOutSensorTask extends ControlTaskBase
{
    private IntakeMechanism intakeMechanism;
    
    public IntakeOutSensorTask() 
    {
    }
    
    @Override
    public void begin()
    {
        this.intakeMechanism = this.getInjector().getInstance(IntakeMechanism.class);

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
        return this.intakeMechanism.getThroughBeamBroken();
    }

    @Override
    public boolean shouldCancel()
    {
        return false;
    }
}
