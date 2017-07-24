package org.usfirst.frc.team1318.robot.vision;

import org.opencv.core.Point;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.ITimer;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.vision.pipelines.HSVGearCenterPipeline;
import org.usfirst.frc.team1318.robot.vision.pipelines.HSVShooterCenterPipeline;
import org.usfirst.frc.team1318.robot.vision.pipelines.ICentroidVisionPipeline;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * Vision manager.
 * 
 * @author Will
 *
 */
@Singleton
public class VisionManager implements IMechanism, VisionRunner.Listener<ICentroidVisionPipeline>
{
    private final static String LogName = "vision";

    private final IDashboardLogger logger;
    private final ITimer timer;
    private final ISolenoid shooterLight;
    private final ISolenoid gearLight;

    private final Object visionLock;
    
    private final UsbCamera shooterCamera;
    private final VisionThread shooterVisionThread;
    private final HSVShooterCenterPipeline shooterVisionPipeline;

    private final UsbCamera gearCamera;
    private final VisionThread gearVisionThread;
    private final HSVGearCenterPipeline gearVisionPipeline;

    private Driver driver;
    private VisionProcessingState currentState;

    private Point center;

    private Double desiredAngleX;
    private Double measuredAngleX;
    private Double distanceFromRobot;

    private double lastMeasuredFps;

    /**
     * Initializes a new VisionManager
     * @param logger to use
     * @param timer to use
     * @param provider for obtaining electronics objects
     */
    @Inject
    public VisionManager(
        IDashboardLogger logger,
        ITimer timer,
        IWpilibProvider provider)
    {
        this.logger = logger;
        this.timer = timer;
        this.shooterLight = provider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.VISION_SHOOTER_LIGHT_CHANNEL);
        this.gearLight = provider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.VISION_GEAR_LIGHT_CHANNEL);

        this.visionLock = new Object();

        this.shooterCamera = new UsbCamera("usb1", 1);
        this.shooterCamera.setResolution(VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);;
        this.shooterCamera.setExposureAuto();
        this.shooterCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_OPERATOR_BRIGHTNESS);
        this.shooterCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);

        this.shooterVisionPipeline = new HSVShooterCenterPipeline(this.timer, VisionConstants.SHOULD_UNDISTORT);
        this.shooterVisionThread = new VisionThread(shooterCamera, this.shooterVisionPipeline, this);
        this.shooterVisionThread.start();

        this.gearCamera = new UsbCamera("usb0", 0);
        this.gearCamera.setResolution(VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);
        this.gearCamera.setExposureAuto();
        this.gearCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_OPERATOR_BRIGHTNESS);
        this.gearCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);

        this.gearVisionPipeline = new HSVGearCenterPipeline(this.timer, VisionConstants.SHOULD_UNDISTORT);
        this.gearVisionThread = new VisionThread(gearCamera, this.gearVisionPipeline, this);
        this.gearVisionThread.start();

        this.driver = null;
    	this.currentState = VisionProcessingState.None;

        this.center = null;
        this.desiredAngleX = null;
        this.measuredAngleX = null;
        this.distanceFromRobot = null;

        this.lastMeasuredFps = 0.0;
    }

    public Point getCenter()
    {
        synchronized (this.visionLock)
        {
            return this.center;
        }
    }

    public Double getMeasuredAngle()
    {
        synchronized (this.visionLock)
        {
            return this.measuredAngleX;
        }
    }

    public Double getDesiredAngle()
    {
        synchronized (this.visionLock)
        {
            return this.desiredAngleX;
        }
    }

    public Double getMeasuredDistance()
    {
        synchronized (this.visionLock)
        {
            return this.distanceFromRobot;
        }
    }

    public double getLastMeasuredFps()
    {
        synchronized (this.visionLock)
        {
            return this.lastMeasuredFps;
        }
    }

    @Override
    public void update()
    {
        VisionProcessingState desiredState = VisionProcessingState.None;
        if (this.driver.getDigital(Operation.EnableGearVision))
        {
            desiredState = VisionProcessingState.Gear;
        }
        else if (this.driver.getDigital(Operation.EnableShooterVision))
        {
            desiredState = VisionProcessingState.Shooter;
        }

        if (this.currentState != desiredState)
        {
            if (desiredState == VisionProcessingState.Gear)
            {
                this.gearCamera.setExposureManual(VisionConstants.LIFECAM_CAMERA_VISION_EXPOSURE);
                this.gearCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_VISION_BRIGHTNESS);
                this.gearCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);
            }
            else 
            {
                this.gearCamera.setExposureAuto();
                this.gearCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_OPERATOR_BRIGHTNESS);
                this.gearCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);
            }

            if (desiredState == VisionProcessingState.Shooter)
            {
                this.shooterCamera.setExposureManual(VisionConstants.LIFECAM_CAMERA_VISION_EXPOSURE);
                this.shooterCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_VISION_BRIGHTNESS);
                this.shooterCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);
            }
            else 
            {
                this.shooterCamera.setExposureAuto();
                this.shooterCamera.setBrightness(VisionConstants.LIFECAM_CAMERA_OPERATOR_BRIGHTNESS);
                this.shooterCamera.setFPS(VisionConstants.LIFECAM_CAMERA_FPS);
            }

            this.shooterLight.set(desiredState == VisionProcessingState.Shooter);
            this.shooterVisionPipeline.setActivation(desiredState == VisionProcessingState.Shooter);

            this.gearLight.set(desiredState == VisionProcessingState.Gear);
            this.gearVisionPipeline.setActivation(desiredState == VisionProcessingState.Gear);

            this.currentState = desiredState;
        }

        Point center = this.getCenter();
        this.logger.logPoint(VisionManager.LogName, "center", center);

        Double fps = this.getLastMeasuredFps();
        this.logger.logNumber(VisionManager.LogName, "fps", fps);

        Double dist = this.getMeasuredDistance();
        this.logger.logNumber(VisionManager.LogName, "dist", dist);

        Double dAngle = this.getDesiredAngle();
        this.logger.logNumber(VisionManager.LogName, "dAngle", dAngle);

        Double mAngle = this.getMeasuredAngle();
        this.logger.logNumber(VisionManager.LogName, "mAngle", mAngle);
    }

    @Override
    public void stop()
    {
        this.shooterLight.set(false);
        this.shooterVisionPipeline.setActivation(false);

        this.gearLight.set(false);
        this.gearVisionPipeline.setActivation(false);

        this.center = null;

        this.desiredAngleX = null;
        this.measuredAngleX = null;
        this.distanceFromRobot = null;

        this.lastMeasuredFps = 0.0;
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    @Override
    public void copyPipelineOutputs(ICentroidVisionPipeline pipeline)
    {
        synchronized (this.visionLock)
        {
            if (pipeline.isActive())
            {
                this.center = pipeline.getCenter();

                this.desiredAngleX = pipeline.getDesiredAngleX();
                this.measuredAngleX = pipeline.getMeasuredAngleX();
                this.distanceFromRobot = pipeline.getRobotDistance();

                this.lastMeasuredFps = pipeline.getFps();
            }
        }
    }
}
