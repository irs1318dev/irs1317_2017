package org.usfirst.frc.team1318.robot.common;

import org.usfirst.frc.team1318.robot.driver.common.Driver;

/**
 * The controller defines the logic that controls a mechanism given inputs (component) and driver-requested actions, and 
 * translates those into the abstract functions that should be applied to the outputs (component).
 * 
 * @author Will
 *
 */
public interface IMechanism
{
    /**
     * read all of the sensors for the mechanism that we will use in macros/autonomous mode and record their values
     */
    public void readSensors();

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant component
     */
    public void update();

    /**
     * stop the relevant component
     */
    public void stop();

    /**
     * set the driver that the controller should use
     * @param driver to use
     */
    public void setDriver(Driver driver);
}
