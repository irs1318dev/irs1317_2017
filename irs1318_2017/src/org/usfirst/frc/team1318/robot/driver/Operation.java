package org.usfirst.frc.team1318.robot.driver;

public enum Operation
{
    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainSimpleMode,
    DriveTrainUsePositionalMode,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainSwapFrontOrientation,

    // Shooter operations:
    ShooterSpeed,
    ShooterSpin,
    ShooterLowerKicker,
    ShooterExtendHood,

    // Intake operations:
    IntakeRotatingIn,
    IntakeRotatingOut,
    IntakeExtend,
    IntakeRetract,

    // Stinger Operations
    StingerOut,
    StingerIn,

    // Other general operations:
    EnablePID,
    DisablePID,
}
