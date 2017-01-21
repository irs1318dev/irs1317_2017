package org.usfirst.frc.team1318.robot;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RobotModuleTest
{
    /**
     * Make sure the wiring is in place.
     */
    @Test
    public void testRobotModule()
    {
        Injector injector = Guice.createInjector(new RobotModule());
        //        ControllerManager controllerManager = injector.getInstance(ControllerManager.class);
    }
}
