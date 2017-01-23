package org.usfirst.frc.team1318.robot.DriveTrain;

import org.usfirst.frc.team1318.robot.Common.DashboardLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

/**
 * The drivetrain component class describes the electronics of the drivetrain and defines the abstract way to control it.
 * The drivetrain electronics include two motors (left and right), and two encoders (left and right). 
 * 
 */
@Singleton
public class DriveTrainComponent
{
    private final static String LogName = "dt";

    private final Victor leftMotor;
    private final Victor rightMotor;

    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    /**
     * Initializes a new DriveTrainComponent
     */
    @Inject
    public DriveTrainComponent(
        @Named("DRIVETRAIN_LEFTMOTOR") Victor leftMotor,
        @Named("DRIVETRAIN_RIGHTMOTOR") Victor rightMotor,
        @Named("DRIVETRAIN_LEFTENCODER") Encoder leftEncoder,
        @Named("DRIVETRAIN_RIGHTENCODER") Encoder rightEncoder)
    {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
    }

    /**
     * set the power levels to the drive train
     * @param leftPower level to apply to the left motor
     * @param rightPower level to apply to the right motor
     */
    public void setDriveTrainPower(double leftPower, double rightPower)
    {
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "leftPower", leftPower);
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "rightPower", rightPower);

        double outLeftPower = leftPower;
        double outRightPower = -rightPower;// note: right motors are oriented facing "backwards"

        this.leftMotor.set(outLeftPower);
        this.rightMotor.set(outRightPower);
    }

    /**
     * get the velocity from the left encoder
     * @return a value indicating the velocity
     */
    public double getLeftEncoderVelocity()
    {
        double leftVelocity = -this.leftEncoder.getRate();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "leftVelocity", leftVelocity);
        return leftVelocity;
    }

    /**
     * get the velocity from the right encoder
     * @return a value indicating the velocity
     */
    public double getRightEncoderVelocity()
    {
        double rightVelocity = this.rightEncoder.getRate();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "rightVelocity", rightVelocity);
        return rightVelocity;
    }

    /**
     * get the distance from the left encoder
     * @return a value indicating the distance
     */
    public double getLeftEncoderDistance()
    {
        double leftDistance = -this.leftEncoder.getDistance();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "leftDistance", leftDistance);
        return leftDistance;
    }

    /**
     * get the distance from the right encoder
     * @return a value indicating the distance
     */
    public double getRightEncoderDistance()
    {
        double rightDistance = this.rightEncoder.getDistance();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "rightDistance", rightDistance);
        return rightDistance;
    }

    /**
     * get the ticks from the left encoder
     * @return a value indicating the number of ticks we are at
     */
    public int getLeftEncoderTicks()
    {
        int leftTicks = -this.leftEncoder.get();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "leftTicks", leftTicks);
        return leftTicks;
    }

    /**
     * get the ticks from the right encoder
     * @return a value indicating the number of ticks we are at
     */
    public int getRightEncoderTicks()
    {
        int rightTicks = this.rightEncoder.get();
        DashboardLogger.logNumber(DriveTrainComponent.LogName, "rightTicks", rightTicks);
        return rightTicks;
    }

    /**
     * reset the encoder so it considers the current location to be "0"
     */
    public void reset()
    {
        this.leftEncoder.reset();
        this.rightEncoder.reset();
    }
}
