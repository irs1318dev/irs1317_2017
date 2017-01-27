package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.common.DashboardLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

@Singleton
public class IntakeComponent
{
    public static final String LogName = "int";

    private final Talon motor;
    private final DoubleSolenoid intakeSolenoid;
    private final Solenoid intakeLight;
    private final AnalogInput throughBeamSensor;

    @Inject
    public IntakeComponent(
        @Named("INTAKE_MOTOR") Talon motor,
        @Named("INTAKE_SOLENOID") DoubleSolenoid intakeSolenoid,
        @Named("INTAKE_LIGHT") Solenoid intakeLight,
        @Named("INTAKE_THROUGHBEAM") AnalogInput throughBeamSensor)
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
            this.intakeSolenoid.set(Value.kForward);
        }
        else
        {
            this.intakeSolenoid.set(Value.kReverse);
        }
    }

    public void stop()
    {
        this.motor.set(0.0);
        this.intakeSolenoid.set(Value.kOff);
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
