package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class IntakeComponent
{

    private final IMotor motor;
    private final IDoubleSolenoid intakeExtender;
    private final IDoubleSolenoid gearExtender;

    @Inject
    public IntakeComponent(
        @Named("INTAKE_MOTOR") IMotor motor,
        @Named("INTAKE_SOLENOID") IDoubleSolenoid intakeExtender,
        @Named("GEAR_SOLENOID") IDoubleSolenoid gearExtender)
    {
        this.motor = motor;
        this.intakeExtender = intakeExtender;
        this.gearExtender = gearExtender;
    }

    public void extend(boolean extend)
    {
        if (extend)
        {
            this.intakeExtender.set(DoubleSolenoidValue.kForward); //extend
        }

        else
        {
            this.intakeExtender.set(DoubleSolenoidValue.kReverse); //retract
        }

    }

    public void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
    }

    public void extendGearHolder(boolean extend)
    {
        if (extend)
        {
            this.gearExtender.set(DoubleSolenoidValue.kForward); //extend
        }

        else
        {
            this.gearExtender.set(DoubleSolenoidValue.kReverse); //retract
        }

    }

    public void stop()
    {
        this.motor.set(0.0);
        this.intakeExtender.set(DoubleSolenoidValue.kOff);
    }
}
