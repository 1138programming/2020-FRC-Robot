package frc.robot.controller;

import edu.wpi.first.wpiutil.math.MathUtil;

public class TakeBackHalf {
    private double m_error = 0;
    private double m_H0 = 0;
    private double m_gain = 1;
    private double m_setpoint = 0;
    private double m_tolerance = 0.05;
    private double m_maxOutput = Double.POSITIVE_INFINITY;
    private double m_minOutput = Double.NEGATIVE_INFINITY;
    private double m_maxInput = Double.POSITIVE_INFINITY;
    private double m_minInput = Double.NEGATIVE_INFINITY;
    private double m_measurement = 0;
    private int m_sign = 1;
    private int m_lastSign = 1;
    private double m_output = 0;
    private double m_lastOutput = 0;

    /**
     * Creates a TakeBackHalf controller.
     */
    public TakeBackHalf(double gain) {
        m_gain = gain;
    }

    public TakeBackHalf() {
        this(1);
    }

    /**
     * Set the gain of the controller
     * 
     * @param gain          The gain
     */
    public void setGain(double gain) {
        m_gain = gain;
    }

    /**
     * Get the gain of the controller
     * 
     * @return  The gain of the controller
     */
    public double getGain() {
        return m_gain;
    }

    /**
     * Sets the setpoint of the controller
     * 
     * @param setpoint      The new setpoint
     */
    public void setSetpoint(double setpoint) {
        m_setpoint = MathUtil.clamp(setpoint, m_minInput, m_maxInput);
    }

    /**
     * Sets the setpoint of the controller relative to the current setpoint
     * 
     * @param setpoint      The relative setpoint
     */
    public void setSetpointRelative(double setpoint) {
        setSetpoint(m_setpoint + setpoint);
    }

    /**
     * Returns the setpoint of the controller
     * 
     * @return  The setpoint of the controller
     */
    public double getSetpoint() {
        return m_setpoint;
    }

    /**
     * Returns whether the measured value is within a certain tolerance of the setpoint
     * 
     * @return  True if the measured value is within the tolerance, false otherwise
     */
    public boolean atSetpoint() {
        return Math.abs(m_error) < m_tolerance;
    }

    /**
     * Sets the tolerance for being considered at the setpoint
     * 
     * @param positionTolerance
     */
    public void setTolerance(double tolerance) {
        m_tolerance = tolerance;
    }

    /**
     * Returns the error, the difference between the setpoint and the measured value
     * 
     * @return  The error
     */
    public double getError() {
        return m_error;
    }

    public void setInputRange(double minInput, double maxInput) {
        if (minInput > maxInput) {
            m_minInput = maxInput;
            m_maxInput = minInput;
        } else {
            m_minInput = minInput;
            m_maxInput = maxInput;
        }
    }

    public void setOutputRange(double minOutput, double maxOutput) {
        if (minOutput > maxOutput) {
            m_minOutput = maxOutput;
            m_maxOutput = minOutput;
        } else {
            m_minOutput = minOutput;
            m_maxOutput = maxOutput;
        }
    }

    /**
     * Calculates and returns the output of the controller.
     * 
     * @param measurement       The sensor measurement to use as the controller's input
     */
    public double calculate(double measurement) {
        m_measurement = MathUtil.clamp(measurement, m_minInput, m_maxInput);
        m_error = m_setpoint - m_measurement;
        m_sign = m_error >= 0 ? 1 : -1;

        double output_h = MathUtil.clamp(m_gain * m_error + m_lastOutput, m_minOutput, m_maxOutput);
        if (m_lastSign != m_sign) {
            m_H0 = (output_h + m_H0) / 2;
            m_output = m_H0;
        } else {
            m_output = output_h;
        }

        m_lastSign = m_sign;
        m_lastOutput = m_output;

        return m_output;
    }

    public void reset() {
        m_lastSign = 1;
        m_lastOutput = 0;
    }
}