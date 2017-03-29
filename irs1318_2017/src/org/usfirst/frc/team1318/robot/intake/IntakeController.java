package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class IntakeController implements IController
{
    private final IntakeComponent intakeComponent;
    private Driver driver;

    private boolean isArmExtended;
    private boolean isConveyorExtended;

    @Inject
    public IntakeController(IntakeComponent intakeComponent)
    {
        this.intakeComponent = intakeComponent;

        this.isArmExtended = false;
        this.isConveyorExtended = false;
    }

    @Override
    public void update()
    {
        // spin intake motor at speed if button is clicked
        if (this.driver.getDigital(Operation.IntakeIn))
        {
            this.intakeComponent.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else if (this.driver.getDigital(Operation.IntakeOut))
        {
            this.intakeComponent.setMotorSpeed(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else
        {
            this.intakeComponent.setMotorSpeed(0.0);
        }

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeArmExtend))
        {
            this.intakeComponent.extendArm(true);
            this.isArmExtended = true;
        }
        else if (this.driver.getDigital(Operation.IntakeArmRetract))
        {
            this.intakeComponent.extendArm(false);
            this.isArmExtended = false;
        }

        // extend conveyor
        if (this.driver.getDigital(Operation.IntakeConveyorExtend))
        {
            this.intakeComponent.extendConveyor(true);
            this.isConveyorExtended = true;
        }
        else if (this.driver.getDigital(Operation.IntakeConveyorRetract))
        {
            this.intakeComponent.extendConveyor(false);
            this.isConveyorExtended = false;
        }
        
        // extend mouth
        if (this.driver.getDigital(Operation.IntakeMouthExtend))
        {
            this.intakeComponent.extendMouth(true);
        }
        else if (this.driver.getDigital(Operation.IntakeMouthRetract))
        {
            this.intakeComponent.extendMouth(false);
        }

        boolean throughBeamBroken = this.intakeComponent.getThroughBeamBroken();
        this.intakeComponent.setIndicator(throughBeamBroken && this.isArmExtended && !this.isConveyorExtended);
    }

    @Override
    public void stop()
    {
        this.intakeComponent.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
