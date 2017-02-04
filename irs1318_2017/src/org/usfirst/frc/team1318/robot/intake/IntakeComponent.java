package org.usfirst.frc.team1318.robot.intake;

@Singleton
public class IntakeComponent
{

    private final IMotor motor;
    private final IDoubleSolenoid intakeExtender;

    @Inject
    public IntakeComponent(
        @Named("INTAKE_MOTOR") IMotor motor,
        @Named("INTAKE_SOLENOID") IDoubleSolenoid intakeExtender)
    {
        this.motor = motor;
        this.intakeExtender = intakeExtender;
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

    public void stop()
    {
        this.motor.set(0.0);
        this.intakeExtender.set(DoubleSolenoidValue.kOff);
    }
}
