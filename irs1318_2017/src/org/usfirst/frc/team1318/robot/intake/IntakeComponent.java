package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.common.DashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ISolenoid;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class IntakeComponent
{
    public static final String LogName = "int";

    private final IMotor motor;
    private final IDoubleSolenoid intakeSolenoid;
    private final ISolenoid intakeLight;
    private final IAnalogInput throughBeamSensor;

    @Inject
    public IntakeComponent(
        @Named("INTAKE_MOTOR") IMotor motor,
        @Named("INTAKE_SOLENOID") IDoubleSolenoid intakeSolenoid,
        @Named("INTAKE_LIGHT") ISolenoid intakeLight,
        @Named("INTAKE_THROUGHBEAM") IAnalogInput throughBeamSensor)
    {
        this.motor = motor;
        this.intakeSolenoid = intakeSolenoid;
        this.intakeLight = intakeLight;
        this.throughBeamSensor = throughBeamSensor;
    }

    /**
     * Extend or retract the "base" part of the intake
     * @param extend - true extends
     */
    public void extendOrRetract(boolean extend)
    {
        if (extend)
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.kReverse);
        }
    }

    public void stop()
    {
        this.motor.set(0.0);
        this.intakeSolenoid.set(DoubleSolenoidValue.kOff);
        this.intakeLight.set(false);
    }

    /**
     * Take a speed and sets the motor to that speed
     * @param speed - the speed to be set
     */
    public void setIntakeSpeed(double speed)
    {
        this.motor.set(speed);
    }

    public boolean getThroughBeamBroken()
    {
        boolean valueBool = (this.throughBeamSensor.getVoltage() < 2.5);
        DashboardLogger.logBoolean(IntakeComponent.LogName, "through-beam", valueBool);
        return valueBool;
    }

    public void setIntakeLight(boolean enable)
    {
        this.intakeLight.set(enable);
    }
}
