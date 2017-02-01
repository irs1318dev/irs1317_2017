package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.driver.buttons.ButtonType;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.PIDBrakeTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.SequentialTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterKickerTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinDownTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionCenteringTask;
import org.usfirst.frc.team1318.robot.driver.descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.UserInputDevice;

public class ButtonMap
{
    @SuppressWarnings("serial")
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
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

            // Operations for the shooter
            put(
                Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));
            put(
                Operation.ShooterSpin,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.ShooterLowerKicker,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.ShooterExtendHood,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Simple));

            // Operations for the intake
            put(
                Operation.IntakeRotatingIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeRotatingOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    0,
                    ButtonType.Click));
            put(
                Operation.IntakeRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    180,
                    ButtonType.Click));

            // Stinger operations
            put(
                Operation.StingerIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.StingerOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Simple));

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
                    UserInputDeviceButton.NONE,
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
                MacroOperation.Center,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    ButtonType.Simple,
                    () -> new VisionCenteringTask(),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                    }));

            // Macros for shooting distance.
            put(
                MacroOperation.SpinClose,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY,
                            TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                    }));
            put(
                MacroOperation.SpinMiddle,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY,
                            TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                    }));
            put(
                MacroOperation.SpinFar,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                        Operation.IntakeExtend,
                        Operation.IntakeRetract,
                    }));
            put(
                MacroOperation.Shoot,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                        new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                        Operation.IntakeExtend,
                        Operation.IntakeRetract,
                    }));

        }
    };
}
