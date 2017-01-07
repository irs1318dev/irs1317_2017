package org.usfirst.frc.team1318.robot.Vision;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Vision manager.
 * 
 * @author Will
 *
 */
public class VisionManager implements IController
{
    /**
     * Initializes a new VisionManager
     */
    public VisionManager()
    {
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void update()
    {
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
}
