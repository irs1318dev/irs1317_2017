package org.usfirst.frc.team1318.robot.driver;

public enum Operation
{
    // Other general operations:
    EnablePID,
    DisablePID,

    // Vision operations:
    EnableGearVision,
    EnableShooterVision,

    // DriveTrain operations:
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
    IntakeGearHolderExtend,
    IntakeGearHolderRetract,

    // Climber
    ClimberSpeed,

    // Shooter Operations
    ShooterSpeed,
    ShooterFeed,
    ShooterExtendHood,
}
