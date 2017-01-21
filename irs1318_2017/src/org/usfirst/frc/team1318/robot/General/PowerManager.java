package org.usfirst.frc.team1318.robot.General;

import org.usfirst.frc.team1318.robot.Common.ComplementaryFilter;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Power manager.
 * 
 * @author Will
 *
 */
@Singleton
public class PowerManager implements IController
{
    private final PowerDistributionPanel pdp;
    private ComplementaryFilter filter;

    /**
     * Initializes a new PowerComponent
     */
    @Inject
    public PowerManager(@Named("POWERMANAGER_PDP") PowerDistributionPanel pdp)
    {
        this.pdp = new PowerDistributionPanel();
        this.filter = new ComplementaryFilter(0.4, 0.6, this.pdp.getVoltage());
    }

    public double getVoltage()
    {
        return this.filter.getValue();
    }

    @Override
    public void update()
    {
        this.filter.update(this.pdp.getVoltage());
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
