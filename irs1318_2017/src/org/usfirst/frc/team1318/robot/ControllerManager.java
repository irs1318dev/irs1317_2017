package org.usfirst.frc.team1318.robot;

import java.util.List;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;

public class ControllerManager implements IController
{
    public final List<IController> controllerList;

    public ControllerManager(List<IController> controllerList)
    {
        this.controllerList = controllerList;
        //        this.controllerList.add(this.components.getPowerManager());
        //        this.controllerList.add(this.components.getPositionManager());
        //        this.controllerList.add(this.components.getVisionManager());
        //        this.controllerList.add(new CompressorController(this.components.getCompressor()));
        //        this.controllerList.add(new DriveTrainController(this.components.getDriveTrain(), TuningConstants.DRIVETRAIN_USE_PID_DEFAULT));
        //        this.controllerList.add(this.injector.getInstance(IntakeController.class));
        //        this.controllerList.add(new ShooterController(this.components.getShooter(), this.components.getPowerManager()));
        //        this.controllerList.add(new StingerController(this.components.getStingerComponent()));
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
