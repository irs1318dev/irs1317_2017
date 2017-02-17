package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.driver.buttons.ButtonType;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveDistanceTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.PIDBrakeTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.SequentialTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionCenteringTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionForwardAndCenterTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.WaitTask;
import org.usfirst.frc.team1318.robot.driver.descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.UserInputDevice;

public class ButtonMap implements IButtonMap
{
    @SuppressWarnings("serial")
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            // Operations for general stuff
            put(
                Operation.DisablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
                    ButtonType.Click));
            put(
                Operation.EnablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
                    ButtonType.Click));

            // Operations for the drive train
            put(
                Operation.DriveTrainMoveForward,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Y,
                    ElectronicsConstants.INVERT_Y_AXIS,
                    TuningConstants.DRIVETRAIN_Y_DEAD_ZONE));
            put(
                Operation.DriveTrainTurn,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DRIVETRAIN_X_DEAD_ZONE));
            put(
                Operation.DriveTrainSimpleMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainUsePositionalMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainLeftPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));
            put(
                Operation.DriveTrainRightPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));
            put(
                Operation.DriveTrainSwapFrontOrientation,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));

            // Operations for the intake
            put(
                Operation.IntakeArmExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    0,
                    ButtonType.Click));
            put(
                Operation.IntakeArmRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    180,
                    ButtonType.Click));
            put(
                Operation.IntakeIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeGearHolderExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.IntakeGearHolderRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));

            // Operations for the climber
            put(
                Operation.ClimberSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Throttle,
                    true,
                    TuningConstants.CLIMBER_THROTTLE_DEAD_ZONE));

            // Operations for the shooter
            put(
                Operation.ShooterExtendHood,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ShooterFeed,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));
        }
    };

    @SuppressWarnings("serial")
    public static Map<MacroOperation, MacroOperationDescription> MacroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {
            // Brake mode macro
            put(
                MacroOperation.PIDBrake,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    ButtonType.Simple,
                    () -> new PIDBrakeTask(),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                    }));

            // Centering macro
            put(
                MacroOperation.ShooterCenter,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Toggle,
                    () -> new WaitTask(0.0),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                        Operation.DriveTrainTurn,
                        Operation.DriveTrainMoveForward,
                    }));
            put(
                MacroOperation.GearCenter,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> new VisionCenteringTask(),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                        Operation.DriveTrainTurn,
                        Operation.DriveTrainMoveForward,
                    }));
            put(
                MacroOperation.GearCenterAndAdvance,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new VisionForwardAndCenterTask(),
                        new DriveDistanceTimedTask(TuningConstants.MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE, 1.5)),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                        Operation.DriveTrainTurn,
                        Operation.DriveTrainMoveForward,
                    }));

            put(
                MacroOperation.SpinFar,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> new ShooterSpinTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY),
                    new Operation[]
                    {
                        Operation.ShooterSpeed,
                        Operation.ShooterExtendHood
                    }));

            put(
                MacroOperation.SpinClose,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> new ShooterSpinTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY),
                    new Operation[]
                    {
                        Operation.ShooterSpeed,
                        Operation.ShooterExtendHood
                    }));

            put(
                MacroOperation.SpinCloseLowGoal,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> new ShooterSpinTask(true, TuningConstants.SHOOTER_CLOSE_SHOT_LOW_GOAL_VELOCITY),
                    new Operation[]
                    {
                        Operation.ShooterSpeed,
                        Operation.ShooterExtendHood
                    }));
        }
    };

    @Override
    public Map<Operation, OperationDescription> getOperationSchema()
    {
        return ButtonMap.OperationSchema;
    }

    @Override
    public Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
