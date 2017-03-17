package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
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
    private static final String LogName = "intake";

    private final IDashboardLogger logger;
    private final IMotor motor;
    private final IDoubleSolenoid armExtender;
    private final IDoubleSolenoid conveyorExtender;
    private final IAnalogInput throughBeamSensor;
    private final ISolenoid gearIndicator;

    @Inject
    public IntakeComponent(
        IDashboardLogger logger,
        @Named("INTAKE_MOTOR") IMotor motor,
        @Named("INTAKE_ARM_SOLENOID") IDoubleSolenoid armExtender,
        @Named("INTAKE_CONVEYOR_SOLENOID") IDoubleSolenoid conveyorExtender,
        @Named("INTAKE_GEAR_THROUGH_BEAM_SENSOR") IAnalogInput throughBeamSensor,
        @Named("INTAKE_GEAR_INDICATOR_LIGHT") ISolenoid gearIndicator)
    {
        this.logger = logger;
        this.motor = motor;
        this.armExtender = armExtender;
        this.conveyorExtender = conveyorExtender;
        this.throughBeamSensor = throughBeamSensor;
        this.gearIndicator = gearIndicator;
    }

    public void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
    }

    public void extendArm(boolean extend)
    {
        if (extend)
        {
            this.armExtender.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.armExtender.set(DoubleSolenoidValue.kReverse);
        }
    }

    public void extendConveyor(boolean extend)
    {
        if (extend)
        {
            this.conveyorExtender.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.conveyorExtender.set(DoubleSolenoidValue.kReverse);
        }
    }

    public boolean getThroughBeamBroken()
    {
        double voltage = this.throughBeamSensor.getVoltage();
        this.logger.logNumber(IntakeComponent.LogName, "throughBeam", voltage);
        return voltage > TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN;
    }

    public void setIndicator(boolean on)
    {
        this.gearIndicator.set(on);
        this.logger.logBoolean(IntakeComponent.LogName, "gearIndicator", on);
    }

    public void stop()
    {
        this.motor.set(0.0);
        this.armExtender.set(DoubleSolenoidValue.kOff);
        this.conveyorExtender.set(DoubleSolenoidValue.kOff);
    }
}
