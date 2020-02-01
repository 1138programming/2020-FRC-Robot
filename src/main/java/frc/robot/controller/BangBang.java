package frc.robot.controller;

import edu.wpi.first.wpiutil.math.MathUtil;

public class BangBang {
    private double m_positionError = 0;
    private double m_setpoint = 0;
    private double m_positionTolerance = 0.05;
    private double m_maxOutput = Double.POSITIVE_INFINITY;
    private double m_minOutput = Double.NEGATIVE_INFINITY;
    private double m_maxInput = Double.POSITIVE_INFINITY;
    private double m_minInput = Double.NEGATIVE_INFINITY;
    private double m_measurement = 0;

    /**
     * Creates a BangBang controller.
     * Outputs the maximum output if the error is positive
     * Outputs the minimum output if the error is negative
     * Outputs 0 if the error is within the acceptable tolerance
     */
    public BangBang() {
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
        return Math.abs(m_positionError) < m_positionTolerance;
    }

    /**
     * Sets the tolerance for being considered at the setpoint
     * 
     * @param positionTolerance
     */
    public void setTolerance(double positionTolerance) {
        m_positionTolerance = positionTolerance;
    }

    /**
     * Returns the error, the difference between the setpoint and the measured value
     * 
     * @return  The error
     */
    public double getPositionError() {
        return m_positionError;
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
        m_positionError = m_setpoint - m_measurement;

        if (atSetpoint()) {
            return 0;
        } else {
            if (m_positionError > 0) {
                return m_maxOutput;
            } else {
                return m_minOutput;
            }
        }
    }
}