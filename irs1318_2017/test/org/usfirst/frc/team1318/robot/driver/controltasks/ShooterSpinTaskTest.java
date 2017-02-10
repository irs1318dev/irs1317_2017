package org.usfirst.frc.team1318.robot.driver.controltasks;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ShooterSpinTaskTest
{

    @Test
    public void beginTest()
    {
        boolean extendHood = false;
        double shooterVelocity = 0.0;

        ShooterSpinTask spinTask = new ShooterSpinTask(extendHood, shooterVelocity);

    }

    @Test
    public void hasCompletedTest()
    {
        boolean extendHood = false;
        double shooterVelocity = 0.0;

        ShooterSpinTask spinTask = new ShooterSpinTask(extendHood, shooterVelocity);

        assertFalse(spinTask.hasCompleted());
    }

    @Test
    public void shouldCancelTest()
    {
        boolean extendHood = false;
        double shooterVelocity = 0.0;

        ShooterSpinTask spinTask = new ShooterSpinTask(extendHood, shooterVelocity);

        assertFalse(spinTask.shouldCancel());
    }

    @Test
    public void endTest()
    {
        boolean extendHood = false;
        double shooterVelocity = 0.0;

        ShooterSpinTask spinTask = new ShooterSpinTask(extendHood, shooterVelocity);

        spinTask.end();

    }
}
