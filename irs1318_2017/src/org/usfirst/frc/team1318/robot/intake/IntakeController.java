package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeController implements IController
{
    private final IntakeComponent intake;

    private Driver driver;

    public IntakeController(IntakeComponent intakeArm)
    {
        this.intake = intakeArm;
    }

    @Override
    public void update()
    {
        // spin intake motor at speed if button is clicked
        if (this.driver.getDigital(Operation.IntakeIn))
        {
            this.intake.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else if (this.driver.getDigital(Operation.IntakeOut))
        {
            this.intake.setMotorSpeed(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else
        {
            this.intake.setMotorSpeed(0.0);
        }

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeArmExtend))
        {
            this.intake.extendIntake(true);
        }
        else if (this.driver.getDigital(Operation.IntakeArmRetract))
        {
            this.intake.extendIntake(false);
        }

        // extend gear holder
        if (this.driver.getDigital(Operation.IntakeGearHolderExtend))
        {
            this.intake.extendGearHolder(true);
        }
        else if (this.driver.getDigital(Operation.IntakeGearHolderRetract))
        {
            this.intake.extendGearHolder(false);
        }
    }

    @Override
    public void stop()
    {
        this.intake.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
