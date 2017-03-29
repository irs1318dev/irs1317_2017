package org.usfirst.frc.team1318.robot.driver;

public enum MacroOperation
{
    // DriveTrain operations:
    PIDBrake,
    GearCenter,
    GearCenterAndAdvance,
    ShooterCenter,

    // Gear-holder operations
    StartingPosition,
    GearSetupPosition,
    GearGrabPosition,
    BallIntakePosition,
    
    SwallowGear,
    SwallowGearSetup,
    
    AutomaticGearPickUp,

    // Shooter operations:
    SpinFar,
    SpinClose,
    SpinCloseLowGoal,
    CloseShotDriveBackwards
}
