package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.driver.buttons.ButtonType;
import org.usfirst.frc.team1318.robot.driver.controltasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveDistanceTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeConveyorExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeArmExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.PIDBrakeTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.SequentialTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionAdvanceAndCenterTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionCenteringTask;
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
            // Operations for vision
            put(Operation.EnableGearVision,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
                    ButtonType.Toggle));

            put(Operation.EnableShooterVision,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_3,
                    ButtonType.Toggle));

            // Operations for the drive train
            put(Operation.DriveTrainDisablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
                    ButtonType.Click));

            put(Operation.DriveTrainEnablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
                    ButtonType.Click));

            put(Operation.DriveTrainMoveForward,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Y,
                    ElectronicsConstants.INVERT_Y_AXIS,
                    TuningConstants.DRIVETRAIN_Y_DEAD_ZONE));

            put(Operation.DriveTrainTurn,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DRIVETRAIN_X_DEAD_ZONE));

            put(Operation.DriveTrainSimpleMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));

            put(Operation.DriveTrainUsePositionalMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));

            put(Operation.DriveTrainLeftPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));

            put(Operation.DriveTrainRightPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));

            put(Operation.DriveTrainSwapFrontOrientation,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));

            // Operations for the intake
            put(Operation.IntakeArmExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    0,
                    ButtonType.Click));

            put(Operation.IntakeArmRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    180,
                    ButtonType.Click));

            put(Operation.IntakeIn,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE, // JOYSTICK_STICK_BOTTOM_LEFT_BUTTON
                    ButtonType.Simple));

            put(Operation.IntakeOut,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE, // JOYSTICK_STICK_TOP_LEFT_BUTTON
                    ButtonType.Simple));

            put(Operation.IntakeConveyorExtend,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE, // JOYSTICK_BASE_MIDDLE_LEFT_BUTTON
                    ButtonType.Click));

            put(Operation.IntakeGearHolderRetract,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE, // JOYSTICK_BASE_BOTTOM_LEFT_BUTTON
                    ButtonType.Click));

            // Operations for the climber
            put(Operation.ClimberSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Throttle,
                    true,
                    TuningConstants.CLIMBER_THROTTLE_DEAD_ZONE));

            // Operations for the shooter
            put(Operation.ShooterDisablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_6,
                    ButtonType.Click));

            put(Operation.ShooterEnablePID,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_7,
                    ButtonType.Click));

            put(Operation.ShooterDisableFeederWait,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_8,
                    ButtonType.Click));

            put(Operation.ShooterEnableFeederWait,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_9,
                    ButtonType.Click));

            put(Operation.ShooterExtendHood,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Simple));

            put(Operation.ShooterFeed,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Simple));

            put(Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None,
                    false,
                    0.0));

            put(Operation.ShooterTargetingLight,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
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
                    UserInputDevice.None,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Toggle,
                    () -> new VisionCenteringTask(false),
                    new Operation[]
                    {
                        Operation.EnableGearVision,
                        Operation.EnableShooterVision,
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
                    () -> new VisionCenteringTask(true),
                    new Operation[]
                    {
                        Operation.EnableGearVision,
                        Operation.EnableShooterVision,
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
                    //                    () -> SequentialTask.Sequence(
                    //                        ConcurrentTask.AnyTasks(
                    //                            new VisionAdvanceAndCenterTask(true),
                    //                            new IntakeSpinTask(true, 15.0)),
                    //                        ConcurrentTask.AllTasks(
                    //                            new IntakeSpinTask(true, 1.5),
                    //                            new IntakeExtendTask(false, 1.5),
                    //                            new DriveDistanceTimedTask(TuningConstants.MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE, 1.5))),
                    () -> SequentialTask.Sequence(
                        new VisionAdvanceAndCenterTask(true),
                        new DriveDistanceTimedTask(18.0, 1.5)),
                    new Operation[]
                    {
                        Operation.EnableGearVision,
                        Operation.EnableShooterVision,
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                        Operation.DriveTrainTurn,
                        Operation.DriveTrainMoveForward,
                        Operation.IntakeIn,
                        Operation.IntakeOut,
                        Operation.IntakeArmExtend,
                        Operation.IntakeArmRetract,
                    }));

            // Other operations
            put(
                MacroOperation.StartingPosition,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    270, // POV left
                    ButtonType.Toggle,
                    () -> ConcurrentTask.AllTasks(
                        new IntakeArmExtendTask(false, 0.25),
                        new IntakeConveyorExtendTask(false, 0.25)),
                    new Operation[]
                    {
                        Operation.IntakeConveyorExtend,
                        Operation.IntakeGearHolderRetract,
                        Operation.IntakeArmExtend,
                        Operation.IntakeArmRetract,
                        Operation.IntakeIn,
                        Operation.IntakeOut,
                    }));
            put(
                MacroOperation.GearSetupPosition,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    0, // POV up
                    ButtonType.Toggle,
                    () -> ConcurrentTask.AllTasks(
                        new IntakeArmExtendTask(true, 0.25),
                        new IntakeConveyorExtendTask(false, 0.25)),
                    new Operation[]
                    {
                        Operation.IntakeConveyorExtend,
                        Operation.IntakeGearHolderRetract,
                        Operation.IntakeArmExtend,
                        Operation.IntakeArmRetract,
                        Operation.IntakeIn,
                        Operation.IntakeOut,
                    }));
            put(
                MacroOperation.GearGrabPosition,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    180, // POV down
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new IntakeArmExtendTask(false, 0.25),
                            new IntakeSpinTask(false, 0.15)),
                        new IntakeConveyorExtendTask(true, 0.25)),
                    new Operation[]
                    {
                        Operation.IntakeConveyorExtend,
                        Operation.IntakeGearHolderRetract,
                        Operation.IntakeArmExtend,
                        Operation.IntakeArmRetract,
                        Operation.IntakeIn,
                        Operation.IntakeOut,
                    }));
            put(
                MacroOperation.BallIntakePosition,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    90, // POV right
                    ButtonType.Toggle,
                    () -> ConcurrentTask.AllTasks(
                        new IntakeArmExtendTask(true, 0.25),
                        new IntakeConveyorExtendTask(true, 0.25)),
                    new Operation[]
                    {
                        Operation.IntakeConveyorExtend,
                        Operation.IntakeGearHolderRetract,
                        Operation.IntakeArmExtend,
                        Operation.IntakeArmRetract,
                        Operation.IntakeIn,
                        Operation.IntakeOut,
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
                    /*() -> ConcurrentTask.AllTasks(
                        new DriveDistanceTimedTask(
                            TuningConstants.SHOOTER_CLOSE_SHOT_BACKWARDS_DISTANCE, 1.0),
                        new ShooterSpinTask(false,
                            TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY)),*/
                    () -> new ShooterSpinTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY),
                    new Operation[]
                    {
                        Operation.ShooterSpeed,
                        Operation.ShooterExtendHood,
                /*Operation.DriveTrainMoveForward,
                Operation.DriveTrainUsePositionalMode,
                Operation.DriveTrainLeftPosition,
                Operation.DriveTrainRightPosition*/
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
