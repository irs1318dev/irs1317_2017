package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Compressor.CompressorController;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainController;
import org.usfirst.frc.team1318.robot.Driver.Driver;

public class ControllerManager implements IController
{
    public final ComponentManager components;
    public final ArrayList<IController> controllerList;

    public ControllerManager(ComponentManager components)
    {
        this.components = components;
        this.controllerList = new ArrayList<IController>();
        this.controllerList.add(this.components.getPowerManager());
        this.controllerList.add(this.components.getPositionManager());
        this.controllerList.add(this.components.getVisionManager());
        this.controllerList.add(new CompressorController(this.components.getCompressor()));
        this.controllerList.add(new DriveTrainController(this.components.getDriveTrain(), TuningConstants.DRIVETRAIN_USE_PID_DEFAULT));
    }

    @Override
    public void update()
    {
        for (IController controller : this.controllerList)
        {
            try
            {
                controller.update();
            }
            catch (Exception ex)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void stop()
    {
        for (IController controller : this.controllerList)
        {
            try
            {
                controller.stop();
            }
            catch (Exception ex)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void setDriver(Driver driver)
    {
        for (IController controller : this.controllerList)
        {
            controller.setDriver(driver);
        }
    }
}
