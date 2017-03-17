package org.usfirst.frc.team1318.robot.driver;

public enum Operation
{
    // Vision operations:
    EnableGearVision,
    EnableShooterVision,

    // DriveTrain operations:
    DriveTrainEnablePID,
    DriveTrainDisablePID,
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainSimpleMode,
    DriveTrainUsePositionalMode,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainSwapFrontOrientation,

    // intake
    IntakeArmExtend,
    IntakeArmRetract,
    IntakeIn,
    IntakeOut,
    IntakeConveyorExtend,
    IntakeGearHolderRetract,

    // Climber
    ClimberSpeed,

    // Shooter Operations
    ShooterEnablePID,
    ShooterDisablePID,
    ShooterEnableFeederWait,
    ShooterDisableFeederWait,
    ShooterSpeed,
    ShooterFeed,
    ShooterExtendHood,
    ShooterTargetingLight
}
