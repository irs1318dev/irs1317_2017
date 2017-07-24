package org.usfirst.frc.team1318.robot.intake;

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
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

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

    private boolean isArmExtended;
    private boolean isConveyorExtended;

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

        this.isArmExtended = false;
        this.isConveyorExtended = false;
    }

    @Override
    public void update()
    {
        boolean isClimbing = this.driver.getAnalog(Operation.ClimberSpeed) > .01;
        
        // spin intake motor at speed if button is clicked
        if (this.driver.getDigital(Operation.IntakeIn))
        {
            this.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else if (this.driver.getDigital(Operation.IntakeOut))
        {
            this.setMotorSpeed(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else
        {
            this.setMotorSpeed(0.0);
        }

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeArmRetract) || isClimbing)
        {
            this.extendArm(false);
            this.isArmExtended = false;
        }
        else if (this.driver.getDigital(Operation.IntakeArmExtend))
        {
            this.extendArm(true);
            this.isArmExtended = true;
        }

        // extend conveyor
        if (this.driver.getDigital(Operation.IntakeConveyorRetract) || isClimbing)
        {
            this.extendConveyor(false);
            this.isConveyorExtended = false;
        }
        else if (this.driver.getDigital(Operation.IntakeConveyorExtend))
        {
            this.extendConveyor(true);
            this.isConveyorExtended = true;
        }

        // extend mouth
        if (this.driver.getDigital(Operation.IntakeMouthRetract) || isClimbing)
        {
            this.extendMouth(false);
        }
        else if (this.driver.getDigital(Operation.IntakeMouthExtend))
        {
            this.extendMouth(true);
        }

        boolean throughBeamBroken = this.getThroughBeamBroken();
        this.setIndicator(throughBeamBroken && this.isArmExtended && !this.isConveyorExtended);
    }

    @Override
    public void stop()
    {
        this.setMotorSpeed(0.0);
        this.armExtender.set(DoubleSolenoidValue.kOff);
        this.conveyorExtender.set(DoubleSolenoidValue.kOff);
        this.mouthExtender.set(DoubleSolenoidValue.kOff);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public boolean getThroughBeamBroken()
    {
        double voltage = this.throughBeamSensor.getVoltage();
        this.logger.logNumber(IntakeMechanism.LogName, "throughBeam", voltage);
        return voltage > TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN;
    }

    private void setMotorSpeed(double speed)
    {
        this.motor.set(speed);
        this.logger.logNumber(IntakeMechanism.LogName, "motor", speed);
    }

    private void extendArm(boolean extend)
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

    private void extendConveyor(boolean extend)
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
    
    private void extendMouth(boolean extend)
    {
        if (extend)
        {
            this.mouthExtender.set(DoubleSolenoidValue.kForward);
        }
        else
        {
            this.mouthExtender.set(DoubleSolenoidValue.kReverse);
        }
    }

    private void setIndicator(boolean on)
    {
        this.gearIndicator.set(on);
        this.logger.logBoolean(IntakeMechanism.LogName, "gearIndicator", on);
    }
}
