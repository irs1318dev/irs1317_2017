package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.Compressor.CompressorComponent;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.General.PositionManager;
import org.usfirst.frc.team1318.robot.General.PowerManager;
import org.usfirst.frc.team1318.robot.Intake.IntakeComponent;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;
import org.usfirst.frc.team1318.robot.Stinger.StingerComponent;
import org.usfirst.frc.team1318.robot.Vision.VisionManager;

public class ComponentManager
{
    private CompressorComponent compressorComponent;
    private DriveTrainComponent driveTrainComponent;
    private ShooterComponent shooterComponent;
    private IntakeComponent intakeComponent;
    private StingerComponent stingerComponent;

    private PowerManager powerManager;
    private PositionManager positionManager;
    private VisionManager visionManager;

    public ComponentManager()
    {
        this.compressorComponent = new CompressorComponent();
        this.driveTrainComponent = new DriveTrainComponent();
        this.shooterComponent = new ShooterComponent();
        this.intakeComponent = new IntakeComponent();
        this.stingerComponent = new StingerComponent();

        this.powerManager = new PowerManager();
        this.positionManager = new PositionManager(this.driveTrainComponent);
        this.visionManager = new VisionManager();
    }

    public CompressorComponent getCompressor()
    {
        return this.compressorComponent;
    }

    public DriveTrainComponent getDriveTrain()
    {
        return this.driveTrainComponent;
    }

    public ShooterComponent getShooter()
    {
        return this.shooterComponent;
    }

    public IntakeComponent getIntake()
    {
        return this.intakeComponent;
    }

    public StingerComponent getStingerComponent()
    {
        return this.stingerComponent;
    }

    public PowerManager getPowerManager()
    {
        return this.powerManager;
    }

    public PositionManager getPositionManager()
    {
        return this.positionManager;
    }

    public VisionManager getVisionManager()
    {
        return this.visionManager;
    }
}
