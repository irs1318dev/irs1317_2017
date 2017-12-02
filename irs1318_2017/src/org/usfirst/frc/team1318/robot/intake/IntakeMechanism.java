package org.usfirst.frc.team1318.robot.intake;

import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilib.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class IntakeMechanism implements IMechanism
{
    private static final String LogName = "intake";

    private final IDashboardLogger logger;
    private final IMotor motor;
    private final IDoubleSolenoid armExtender;
    private final IDoubleSolenoid conveyorExtender;
    private final IDoubleSolenoid mouthExtender;
    private final IAnalogInput throughBeamSensor;
    private final ISolenoid gearIndicator;

    private Driver driver;

    private boolean isThroughBeamBroken;

    @Inject
    public IntakeMechanism(
        IDashboardLogger logger,
        IWpilibProvider provider)
    {
        this.logger = logger;
        this.motor = provider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
        this.armExtender = provider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        this.conveyorExtender = provider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        this.mouthExtender = provider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);
        this.throughBeamSensor = provider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        this.gearIndicator = provider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        this.isThroughBeamBroken = false;
    }

    public boolean getThroughBeamBroken()
    {
    	return this.isThroughBeamBroken;
    }

    @Override
    public void readSensors()
    {
        double voltage = this.throughBeamSensor.getVoltage();
        this.logger.logNumber(IntakeMechanism.LogName, "throughBeam", voltage);
        this.isThroughBeamBroken = voltage > TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN;
    }

    @Override
    public void update()
    {
        boolean isClimbing = this.driver.getAnalog(Operation.ClimberSpeed) > .01;

        // spin intake motor at speed if button is clicked
        double speed = 0.0;
        if (this.driver.getDigital(Operation.IntakeIn))
        {
            speed = TuningConstants.INTAKE_MAX_MOTOR_SPEED;
        }
        else if (this.driver.getDigital(Operation.IntakeOut))
        {
            speed = -TuningConstants.INTAKE_MAX_MOTOR_SPEED;
        }

        this.motor.set(speed);
        this.logger.logNumber(IntakeMechanism.LogName, "motor", speed);

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeArmRetract) || isClimbing)
        {
            this.armExtender.set(DoubleSolenoidValue.kReverse);
        }
        else if (this.driver.getDigital(Operation.IntakeArmExtend))
        {
            this.armExtender.set(DoubleSolenoidValue.kForward);
        }

        // extend conveyor
        if (this.driver.getDigital(Operation.IntakeConveyorRetract) || isClimbing)
        {
            this.conveyorExtender.set(DoubleSolenoidValue.kReverse);
        }
        else if (this.driver.getDigital(Operation.IntakeConveyorExtend))
        {
            this.conveyorExtender.set(DoubleSolenoidValue.kForward);
        }

        // extend mouth
        if (this.driver.getDigital(Operation.IntakeMouthRetract) || isClimbing)
        {
            this.mouthExtender.set(DoubleSolenoidValue.kReverse);
        }
        else if (this.driver.getDigital(Operation.IntakeMouthExtend))
        {
            this.mouthExtender.set(DoubleSolenoidValue.kForward);
        }

        this.gearIndicator.set(this.isThroughBeamBroken);
        this.logger.logBoolean(IntakeMechanism.LogName, "gearIndicator", this.isThroughBeamBroken);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
        this.armExtender.set(DoubleSolenoidValue.kOff);
        this.conveyorExtender.set(DoubleSolenoidValue.kOff);
        this.mouthExtender.set(DoubleSolenoidValue.kOff);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
