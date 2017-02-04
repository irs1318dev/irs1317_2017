package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeController implements IController
{

    private IntakeComponent intakeArm;
    private Driver driver;

    public IntakeController(IntakeComponent intakeArm)
    {
        this.intakeArm = intakeArm;
    }

    @Override
    public void update()
    {
        // spin intake motor at speed if button is clicked
        if (this.driver.getDigital(Operation.SetMotorSpeed))
        {
            this.intakeArm.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else
        {
            this.intakeArm.setMotorSpeed(0.0);
        }

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeExtendArm))
        {
            this.intakeArm.extend(true);
        }
        else
        {
            this.intakeArm.extend(false);
        }
    }

    @Override
    public void stop()
    {
        this.intakeArm.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

}
