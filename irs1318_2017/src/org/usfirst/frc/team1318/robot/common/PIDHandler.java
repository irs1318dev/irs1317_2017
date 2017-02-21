package org.usfirst.frc.team1318.robot.common;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;

/**
 * This class is a PID handler with a feed-forward handler and a complementary filter.
 * 
 * To use PID control:
 *      set the kp/ki/kd/kf tuning values
 *      calculate output based on the setpoint and measured value regularly
 * 
 * for reference:
 *      http://en.wikipedia.org/wiki/PID_controller
 *      http://en.wikipedia.org/wiki/Feed_forward_(control)
 * 
 * @author Will (adapted from old code)
 */
public class PIDHandler
{
    // constants
    private static final double MinTimeStep = 0.01;
    private final Double minOutput;
    private final Double maxOutput;

    private final ComplementaryFilter errorFilter;
    private final ComplementaryFilter outputFilter;

    // instance PIDFS constants
    private double kp;        // proportion for proportional
    private double ki;        // proportion for integral
    private double kd;        // proportion for derivative
    private double kf;        // proportion for feed-forward
    private double ks;        // multiplicand for adjusting scale of setpoint to match scale of measured value

    // instance variables
    private double setpoint = 0.0;          // the input, desired value for
    private double prevMeasuredValue = 0.0; // the previous measured value
    private double measuredValue = 0.0;     // the measured value for PID 
    private double integral = 0.0;          // integral of error data in memory
    private double derivative = 0.0;        // approximate slope of input.. units in / seconds
    private double dt = .001;               // amount of time we waited since our previous measurement
    private double prevTime = 0.0;          // the timestamp of our previous measurement 
    private double error = 0.0;             // the error (difference between setpoint and measured value)
    private double prevError = 0.0;         // the error during our previous measurement
    private double curTime = 0.0;           // the current timestamp
    private double output = 0.0;            // the output we wish to set after our calculation

    // other vars
    private final ITimer timer;
    private final IDashboardLogger logger;
    private final String logName;

    /**
     * This constructor initializes the object and sets constants to affect gain.
     * This defaults to not utilizing a complementary filter to slow ramp-up/ramp-down.
     * 
     * @param kp scalar for proportional component
     * @param ki scalar for integral component
     * @param kd scalar for derivative component
     * @param kf scalar for feed-forward control
     * @param ks scalar for adjusting scale difference between measured value and setpoint value
     * @param minOutput indicates the minimum output value acceptable, or null
     * @param maxOutput indicates the maximum output value acceptable, or null
     */
    public PIDHandler(
        double kp,
        double ki,
        double kd,
        double kf,
        double ks,
        Double minOutput,
        Double maxOutput,
        ITimer timer)
    {
        this(kp, ki, kd, kf, ks, 0.0, 1.0, 0.0, 1.0, minOutput, maxOutput, null, null, timer);
    }

    /**
     * This constructor initializes the object and sets constants to affect gain.
     * This defaults to not utilizing a complementary filter to slow ramp-up/ramp-down.
     * 
     * @param kp scalar for proportional component
     * @param ki scalar for integral component
     * @param kd scalar for derivative component
     * @param kf scalar for feed-forward control
     * @param ks scalar for adjusting scale difference between measured value and setpoint value
     * @param minOutput indicates the minimum output value acceptable, or null
     * @param maxOutput indicates the maximum output value acceptable, or null
     * @param logName to use for logging
     * @param logger to use for logging
     */
    public PIDHandler(
        double kp,
        double ki,
        double kd,
        double kf,
        double ks,
        Double minOutput,
        Double maxOutput,
        String logName,
        IDashboardLogger logger,
        ITimer timer)
    {
        this(kp, ki, kd, kf, ks, 0.0, 1.0, 0.0, 1.0, minOutput, maxOutput, logName, logger, timer);
    }

    /**
     * This constructor initializes the object and sets constants to affect gain.
     * This utilizes a complementary filter to slow ramp-up/ramp-down.
     * 
     * @param kp scalar for proportional component
     * @param ki scalar for integral component
     * @param kd scalar for derivative component
     * @param kf scalar for feed-forward control
     * @param ks scalar for adjusting scale difference between measured value and setpoint value
     * @param kO scalar for output complementary filter multiplier
     * @param kN scalar for output complementary filter multiplier
     * @param kEO scalar for error complementary filter multiplier
     * @param kEN scalar for error complementary filter multiplier
     * @param minOutput indicates the minimum output value acceptable, or null
     * @param maxOutput indicates the maximum output value acceptable, or null
     * @param logName to use for logging
     * @param logger to use for logging
     */
    public PIDHandler(
        double kp,
        double ki,
        double kd,
        double kf,
        double ks,
        double kO,
        double kN,
        double kEO,
        double kEN,
        Double minOutput,
        Double maxOutput,
        String logName,
        IDashboardLogger logger,
        ITimer timer)
    {
        this.ki = ki;
        this.kd = kd;
        this.kp = kp;
        this.kf = kf;
        this.ks = ks;

        this.errorFilter = new ComplementaryFilter(kEO, kEN);
        this.outputFilter = new ComplementaryFilter(kO, kN);

        this.minOutput = minOutput;
        this.maxOutput = maxOutput;

        this.timer = timer;
        this.prevTime = this.timer.get();

        this.logger = logger;
        this.logName = logName;
    }

    /**
     * Calculate the desired output value based on the history, setpoint, and measured value.
     * measuredValue should be in the same unit as the setpoint, basically a positive or negative percentage 
     * between -1 and 1.  This method should be called in a loop and fed feedback data and setpoint changes
     * 
     * @param setpoint describes the goal value
     * @param measuredValue describes the measured value
     * 
     * @return output value to be used
     */
    public double calculatePosition(double setpoint, double measuredValue)
    {
        this.setpoint = setpoint;
        this.measuredValue = measuredValue;

        // update dt
        this.curTime = this.timer.get();
        this.dt = this.curTime - this.prevTime;

        // To prevent division by zero and over-aggressive measurement, output updates at a max of 1kHz
        if (this.dt >= PIDHandler.MinTimeStep)
        {
            this.prevTime = this.curTime;

            // calculate error
            this.errorFilter.update(this.setpoint - this.measuredValue);
            this.error = this.errorFilter.getValue();

            // calculate integral, limiting it based on MaxOutput/MinOutput
            double potentialI = this.ki * (this.integral + this.error * this.dt);
            if (this.maxOutput != null && potentialI > this.maxOutput)
            {
                this.integral = this.maxOutput / this.ki;
            }
            else if (this.minOutput != null && potentialI < this.minOutput)
            {
                this.integral = this.minOutput / this.ki;
            }
            else
            {
                this.integral += this.error;// * this.dt;
            }

            // calculate derivative
            this.derivative = (this.error - this.prevError);// / this.dt;

            // store error
            this.prevError = this.error;

            double result = this.kp * this.error +      // proportional
                this.ki * this.integral +   // integral
                this.kd * this.derivative + // derivative
                this.kf * this.setpoint;    // feed-forward

            if (this.maxOutput != null && result > this.maxOutput)
            {
                result = this.maxOutput;
            }
            else if (this.minOutput != null && result < this.minOutput)
            {
                result = this.minOutput;
            }

            // apply complementary filter to slow ramp-up/ramp-down
            this.outputFilter.update(result);
            this.output = this.outputFilter.getValue();
            this.prevMeasuredValue = this.measuredValue;
        }

        return this.output;
    }

    /**
     * Calculate the desired output value based on the history, setpoint, and measured value.
     * measuredValue should be in the same unit as the setpoint, basically a positive or negative percentage 
     * between -1 and 1.  This method should be called in a loop and fed feedback data and setpoint changes
     * 
     * @param setpoint describes the goal velocity value
     * @param measuredValue describes the measured value, where the measured value is the ticks on the encoder
     * 
     * @return output value to be used
     */
    public double calculateVelocity(double setpoint, double measuredValue)
    {
        this.setpoint = setpoint;
        this.measuredValue = measuredValue;

        // update dt
        this.curTime = this.timer.get();
        this.dt = this.curTime - this.prevTime;

        // To prevent division by zero and over-aggressive measurement, output updates at a max of 100 Hz
        if (this.dt >= PIDHandler.MinTimeStep)
        {
            this.prevTime = this.curTime;

            // calculate change in ticks since our last measurement
            double deltaX = this.measuredValue - this.prevMeasuredValue;
            double timeRatio = 0.02 / this.dt;

            if (this.logger != null && this.logName != null)
            {
                this.logger.logNumber(this.logName, "scale factor", timeRatio * deltaX);
            }

            this.errorFilter.update(this.ks * this.setpoint - deltaX * timeRatio);
            this.error = this.errorFilter.getValue();

            // calculate integral, limiting it based on MaxOutput/MinOutput
            double potentialI = this.ki * (this.integral + this.error * this.dt);
            if (this.maxOutput != null && potentialI > this.maxOutput)
            {
                this.integral = this.maxOutput / this.ki;
            }
            else if (this.minOutput != null && potentialI < this.minOutput)
            {
                this.integral = this.minOutput / this.ki;
            }
            else
            {
                this.integral += this.error * this.dt;
            }

            // calculate derivative
            this.derivative = (this.error - this.prevError) / this.dt;

            // store error
            this.prevError = this.error;

            double result = this.kp * this.error +      // proportional
                this.ki * this.integral +   // integral
                this.kd * this.derivative + // derivative
                this.kf * this.setpoint;    // feed-forward

            if (this.maxOutput != null && result > this.maxOutput)
            {
                result = this.maxOutput;
            }
            else if (this.minOutput != null && result < this.minOutput)
            {
                result = this.minOutput;
            }

            // apply complementary filter to slow ramp-up/ramp-down
            this.outputFilter.update(result);
            this.output = this.outputFilter.getValue();
            this.prevMeasuredValue = this.measuredValue;
        }

        return this.output;
    }

    public double getCurrentOutput()
    {
        return this.output;
    }

    public void setKp(double kp)
    {
        this.kp = kp;
    }

    public void setKi(double ki)
    {
        this.ki = ki;
    }

    public void setKd(double kd)
    {
        this.kd = kd;
    }

    public void setKf(double kf)
    {
        this.kf = kf;
    }
}
