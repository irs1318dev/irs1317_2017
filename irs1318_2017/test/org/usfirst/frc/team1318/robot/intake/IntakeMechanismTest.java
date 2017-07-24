package org.usfirst.frc.team1318.robot.intake;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TestWpilibProvider;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import org.usfirst.frc.team1318.robot.common.wpilib.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class IntakeMechanismTest
{
    @Test
    public void testUpdate_doNothing()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeArmExtend()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(true).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(armExtender).set(eq(DoubleSolenoidValue.kForward));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeArmRetract()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(armExtender).set(eq(DoubleSolenoidValue.kReverse));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeConveyorExtend()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kForward));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeConveyorRetract()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kReverse));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeMothExtend()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(mouthExtender).set(eq(DoubleSolenoidValue.kForward));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeMouthRetract()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(mouthExtender).set(eq(DoubleSolenoidValue.kReverse));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeIn()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(true).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(intakeMotor).set(eq(TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_IntakeOut()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(true).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN - 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(intakeMotor).set(eq(-TuningConstants.INTAKE_MAX_MOTOR_SPEED));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(false));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }

    @Test
    public void testUpdate_GearIndicator()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        doReturn(true).when(driver).getDigital(Operation.IntakeArmExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeArmRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeConveyorExtend);
        doReturn(true).when(driver).getDigital(Operation.IntakeConveyorRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthExtend);
        doReturn(false).when(driver).getDigital(Operation.IntakeMouthRetract);
        doReturn(false).when(driver).getDigital(Operation.IntakeIn);
        doReturn(false).when(driver).getDigital(Operation.IntakeOut);
        doReturn(TuningConstants.THROUGH_BEAM_BROKEN_VOLTAGE_MIN + 0.1).when(throughBeamSensor).getVoltage();

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.update();

        verify(armExtender).set(eq(DoubleSolenoidValue.kForward));
        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kReverse));
        verify(intakeMotor).set(eq(0.0));
        verify(throughBeamSensor).getVoltage();
        verify(gearIndicator).set(eq(true));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);

    }

    @Test
    public void testStop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();

        IMotor intakeMotor = testProvider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);

        IDoubleSolenoid armExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid conveyorExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_CONVEYOR_EXTENDER_SOLENOID_CHANNEL_B);
        IDoubleSolenoid mouthExtender = testProvider.getDoubleSolenoid(
            ElectronicsConstants.PCM_B_MODULE,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_A,
            ElectronicsConstants.INTAKE_MOUTH_EXTENDER_CHANNEL_B);

        IAnalogInput throughBeamSensor = testProvider.getAnalogInput(ElectronicsConstants.INTAKE_GEAR_THROUGH_BEAM_SENSOR_CHANNEL);
        ISolenoid gearIndicator = testProvider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_GEAR_INDICATOR_CHANNEL);

        Driver driver = mock(Driver.class);

        IntakeMechanism intake = new IntakeMechanism(logger, testProvider);
        intake.setDriver(driver);

        intake.stop();

        verify(armExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(conveyorExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(mouthExtender).set(eq(DoubleSolenoidValue.kOff));
        verify(intakeMotor).set(eq(0.0));
        verifyNoMoreInteractions(intakeMotor, armExtender, conveyorExtender, mouthExtender, throughBeamSensor, gearIndicator);
    }
}
