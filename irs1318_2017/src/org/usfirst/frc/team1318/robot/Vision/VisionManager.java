package org.usfirst.frc.team1318.robot.Vision;

import org.opencv.core.Point;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Vision.Analyzer.HSVCenterPipeline;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * Vision manager.
 * 
 * @author Will
 *
 */
public class VisionManager implements IController, VisionRunner.Listener<HSVCenterPipeline>
{
    private final Object visionLock;
    private final VisionThread visionThread;

    private Point center;

    /**
     * Initializes a new VisionManager
     */
    public VisionManager()
    {
        this.visionLock = new Object();
        AxisCamera camera = CameraServer.getInstance().addAxisCamera(VisionConstants.CAMERA_IP_ADDRESS);
        this.visionThread = new VisionThread(camera, new HSVCenterPipeline(), this);
        this.visionThread.start();

        this.center = null;
    }

    public Point getCenter()
    {
        synchronized (this.visionLock)
        {
            return this.center;
        }
    }

    @Override
    public void update()
    {
        String centerString = "n/a";
        Point center = this.getCenter();
        if (center != null)
        {
            centerString = String.format("%f,%f", center.x, center.y);
        }

        DashboardLogger.putString("center", centerString);
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void setDriver(Driver driver)
    {
        // no-op
    }

    @Override
    public void copyPipelineOutputs(HSVCenterPipeline pipeline)
    {
        synchronized (this.visionLock)
        {
            this.center = pipeline.getCenter();
        }
    }
}
