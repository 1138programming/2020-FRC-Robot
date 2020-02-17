package frc.robot.controller;

import java.util.LinkedList;

import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.enums.IntegralType;

public class PIDController {
    /**
     * @brief Setpoint value
     */
    private double m_setpoint = 0;

    /**
     * @brief Tolerance on the setpoint value used when considering whether the controller has reached the target
     */
    private double m_setpointTolerance = 0.05;

    /**
     * @brief Proportional gain value
     */
    private double m_Kp = 1;
    
    /**
     * @brief Integral gain value
     */
    private double m_Ki = 0;

    /**
     * @brief Derivative gain value
     */
    private double m_Kd = 0;

    /**
     * @brief Feedforward gain value
     */
    private double m_Kf = 0;

    /**
     * @brief Error between the setpoint and the measurement
     */
    private double m_error = 0;

    /**
     * @brief The integral of the error
     */
    private double m_integral = 0;

    /**
     * @brief Tolerance on the setpoint velocity used when considering whether the controller has reached the target
     */
    private double m_velocityTolerance = 0.05;

    /**
     * @brief The velocity of the error. Derivative of the error over time
     */
    private double m_velocityError = 0;

    /**
     * @brief Maximum output that the controller can return
     */
    private double m_maxOutput = 1;

    /**
     * @brief Minimum output that the controller can return
     */
    private double m_minOutput = -1;

    /**
     * @brief Maximum input or setpoint that can be given to the controller
     */
    private double m_maxInput = Double.POSITIVE_INFINITY;

    /**
     * @brief Minimum input or setpoint that can be given to the controller
     */
    private double m_minInput = Double.NEGATIVE_INFINITY;

    /**
     * @brief Upper bound for the output deadband
     * 
     * If the controller's output is greater than 0, the deadband is added to it. If the controller's output is less than 0, the deadband is subtracted from it
     */
    private double m_outputDeadband = 0;

    /**
     * @brief Tolerance on the middle of the deadband
     * 
     * If the controller is within the given tolerance of zero, the output is set to zero
     */
    private double m_outputDeadbandTolerance = 0;

    /**
     * @brief The measurement, comes from a sensor
     */
    private double m_measurement = 0;

    /**
     * @brief The controller's output
     */
    private double m_output = 0;

    /**
     * @brief The amount of time between each loop, in seconds
     */
    private double m_period = 0.02;

    /**
     * @brief The number of past errors to cache for the integral
     */
    private int m_integralWindow = 1;

    /**
     * @brief LinkedList to cache past errors in
     */
    private LinkedList<Double> m_errorCache;

    /**
     * @brief The type of integral to use
     */
    private IntegralType m_integralType = IntegralType.DEFAULT;

    /**
     * @brief Whether to use an integral zone or not
     * 
     * An integral zone is a range around setpoint on which to accumulate error on the integral. Everywhere else, error is not accumulated
     */
    private boolean m_integralZone = false;

    /**
     * @brief The zone around the setpoint in which to accumulate the integral
     * 
     * If, for example, the range is 100 units, then from 50 units below and 50 units above, the integral will accumulate
     */
    private double m_integralZoneRange = 0;

    /**
     * @brief The maximum value for the integal
     */
    private double m_maxIntegral = Double.POSITIVE_INFINITY;

    /**
     * @brief The minimum value for the integral
     */
    private double m_minIntegral = Double.NEGATIVE_INFINITY;

    /**
     * Creates a PIDController.
     */
    public PIDController(double Kp, double Ki, double Kd, double Kf, double period) {
        m_Kp = Kp;
        m_Ki = Ki;
        m_Kd = Ki;
        m_Kf = Kf;
        m_period = period;

        m_errorCache = new LinkedList<Double>();
        m_errorCache.addFirst(0.0);
    }

    public PIDController(double Kp, double Ki, double Kd, double Kf) {
        this(Kp, Ki, Kd, Kf, 0.02);
    }

    public PIDController(double Kp, double Ki, double Kd) {
        this(Kp, Ki, Kd, 0);
    }

    /**
     * Set the proportional term for the controller
     * 
     * @param Kp    The proportional term
     */
    public void setP(double Kp) {
        m_Kp = Kp;
    }

    /**
     * Set the integral term for the controller
     * 
     * @param Ki    The integral term
     */
    public void setI(double Ki) {
        m_Ki = Ki;
    }

    /**
     * Set the derivative term for the controller
     * 
     * @param Kp    The derivative term
     */
    public void setD(double Kd) {
        m_Kd = Kd;
    }

    /**
     * Set the feedforward term for the controller
     * 
     * @param Kf    The feedforward term
     */
    public void setF(double Kf) {
        m_Kf = Kf;
    }

    /**
     * Get the proportional term of the controller
     * 
     * @return  The proportional term of the controller
     */
    public double getP() {
        return m_Kp;
    }

    /**
     * Get the integral term of the controller
     * 
     * @return  The integral term of the controller
     */
    public double getI() {
        return m_Ki;
    }

    /**
     * Get the derivative term of the controller
     * 
     * @return  The derivative term of the controller
     */
    public double getD() {
        return m_Kd;
    }

    /**
     * Get the feedforward term of the controller
     * 
     * @return  The feedforward term of the controller
     */
    public double getF() {
        return m_Kf;
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
     * Returns whether the measured value is within a certain setpoint and velocity tolerance of the setpoint
     * 
     * @return  True if the measured value is within the velocity_tolerance, false otherwise
     */
    public boolean atSetpoint() {
        return Math.abs(m_error) < m_setpointTolerance && Math.abs(m_velocityError) < m_velocityTolerance;
    }

    /**
     * Sets the velocity tolerance for being considered at the setpoint
     * 
     * @param setpoint_tolerance    The tolerance on the velocity to consider having reached the setpoint
     * @param velocity_tolerance       The tolerance on the acceleration to consider having reached the setpoint
     */
    public void setTolerance(double setpoint_tolerance, double velocity_tolerance) {
        m_setpointTolerance = setpoint_tolerance;
        m_velocityTolerance = velocity_tolerance;
    }

    /**
     * Returns the error, the difference between the setpoint and the measured value
     * 
     * @return  The error
     */
    public double getError() {
        return m_error;
    }

    public double getVelocityError() {
        return m_velocityError;
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

    public void setIntegralRange(double minIntegral, double maxIntegral) {
        if (minIntegral > maxIntegral) {
            m_minIntegral = maxIntegral;
            m_maxIntegral = minIntegral;
        } else {
            m_minIntegral = minIntegral;
            m_maxIntegral = maxIntegral;
        }
    }

    public void setOutputDeadband(double deadband, double tolerance) {
        m_outputDeadband = deadband;
        m_outputDeadbandTolerance = tolerance;
    }

    public void configIntegral(IntegralType integralType, boolean integralZone) {
        m_integralType = integralType;
        m_integralZone = integralZone;

        if (m_integralType != IntegralType.WINDOW) {
            m_integralWindow = 1;
        }
    }

    public void setIntegralZoneRange(double integralZoneRange) {
        m_integralZoneRange = Math.abs(integralZoneRange);
    }

    public void setIntegralWindowLength(int integralWindow) {
        if (integralWindow < 1 || m_integralType != IntegralType.WINDOW) {
            m_integralWindow = 1;
        } else {
            m_integralWindow = integralWindow;
        }
    }

    /**
     * Calculates and returns the output of the controller.
     * 
     * @param measurement       The sensor measurement to use as the controller's input
     */
    public double calculate(double measurement) {
        // Sets sensor measurement
        m_measurement = measurement;

        // Gets the current error
        m_error = m_setpoint - m_measurement;

        // Computes the velocity error
        m_velocityError = (m_error - m_errorCache.getFirst()) / m_period;

        // Handles growing and maintaining the past error cache and integral
        if (m_integralType != IntegralType.NONE) {
            if (!m_integralZone || Math.abs(m_error) <= m_integralZoneRange) { // Only adds the error to the integral if there is no integral zone or if the error is within the integral zone
                // Adds current error to integral
                m_integral += m_error;

                // Adds current error to past error cache
                m_errorCache.addFirst(m_error);

                // Checks if the error cache has exceeded the specified length
                if (m_errorCache.size() > m_integralWindow) {
                    // Removes the oldest error from the error cache
                    double oldestError = m_errorCache.removeLast();

                    // If the integral type is WINDOW, remove the oldest error from the integral
                    if (m_integralType == IntegralType.WINDOW) {
                        m_integral -= oldestError;
                    }
                }
            }

            // Clamp integral between the max and min integral
            m_integral = MathUtil.clamp(m_integral, m_minIntegral, m_maxIntegral);
        } else {
            m_integral = 0;
        }

        // Calculates output
        m_output = (m_Kp * m_error) + (m_Ki * m_integral * m_period) + (m_Kd * m_velocityError) + (m_Kf * m_setpoint);

        // Add the deadband to the controller output, or if it is within the tolerance, set the output to 0
        if (Math.abs(m_output) > m_outputDeadbandTolerance) {
            if (m_output > 0) {
                m_output += m_outputDeadband;
            } else {
                m_output -= m_outputDeadband;
            }
        } else {
            m_output = 0;
        }

        // Clamps output between the maximum and minimum output the controller can return
        m_output = MathUtil.clamp(m_output, m_minOutput, m_maxOutput);

        return m_output;
    }

    public void reset() {
        m_integral = 0;
        m_errorCache.clear();
        m_errorCache.addFirst(0.0);
    }
}