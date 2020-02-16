package frc.robot.controller;

import edu.wpi.first.wpiutil.math.MathUtil;

public class PIDController {
    private double m_error = 0;
    private double m_Kp = 1;
    private double m_Ki = 0;
    private double m_Kd = 0;
    private double m_Kf = 0;
    private double m_setpoint = 0;
    private double m_setpoint_tolerance = 0.05;
    private double m_velocity_tolerance = 0.05;
    private double m_maxOutput = 1;
    private double m_minOutput = -1;
    private double m_maxInput = Double.POSITIVE_INFINITY;
    private double m_minInput = Double.NEGATIVE_INFINITY;
    private double m_output_deadband_upper = 0;
    private double m_output_deadband_lower = 0;
    private double m_output_deadband_tolerance = 0;
    private double m_measurement = 0;
    private double m_last_measurement = 0;
    private double m_velocity = 0;
    private double m_integral = 0;
    private double m_output = 0;
    private double m_period = 0.02; // Amount of time between each loop, in seconds

    /**
     * Creates a PIDController.
     */
    public PIDController(double Kp, double Ki, double Kd, double Kf, double period) {
        m_Kp = Kp;
        m_Ki = Ki;
        m_Kd = Ki;
        m_Kf = Kf;
        m_period = period;
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
        return Math.abs(m_error) < m_setpoint_tolerance && Math.abs(m_velocity) < m_velocity_tolerance;
    }

    /**
     * Sets the velocity tolerance for being considered at the setpoint
     * 
     * @param setpoint_tolerance    The tolerance on the velocity to consider having reached the setpoint
     * @param velocity_tolerance       The tolerance on the acceleration to consider having reached the setpoint
     */
    public void setTolerance(double setpoint_tolerance, double velocity_tolerance) {
        m_setpoint_tolerance = setpoint_tolerance;
        m_velocity_tolerance = velocity_tolerance;
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
        return m_velocity;
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

    public void setOutputDeadband(double range, double tolerance) {
        m_output_deadband_upper = range / 2;
        m_output_deadband_lower = -range / 2;
        m_output_deadband_tolerance = tolerance;
    }

    public void setOutputDeadband(double lower, double upper, double tolerance) {
        if (lower > upper) {
            m_output_deadband_lower = upper;
            m_output_deadband_upper = lower;
        } else {
            m_output_deadband_lower = lower;
            m_output_deadband_upper = upper;
        }
        m_output_deadband_tolerance = tolerance;
    }

    /**
     * Calculates and returns the output of the controller.
     * 
     * @param measurement       The sensor measurement to use as the controller's input
     */
    public double calculate(double measurement) {
        m_measurement = measurement;
        m_error = m_setpoint - m_measurement;
        m_integral += m_error * m_period;
        m_velocity = (m_measurement - m_last_measurement) / m_period;

        // Calculates output
        m_output = (m_Kp * m_error) + (m_Ki * m_integral) + (m_Kd * m_velocity) + (m_Kf * m_setpoint);

        // Enforces the deadband on the output
        if (m_output < m_output_deadband_upper && m_output > m_output_deadband_lower) {
            double upperDist = m_output_deadband_upper - m_output;
            double lowerDist = m_output - m_output_deadband_lower;

            // Makes sure the output is not within the tolerance on the middle of the deadband before changing it
            if (upperDist - lowerDist > m_output_deadband_tolerance) {
                if (upperDist < lowerDist) {
                    m_output = m_output_deadband_upper;
                } else {
                    m_output = m_output_deadband_lower;
                }
            } else {
                m_output = (m_output_deadband_upper - m_output_deadband_lower) / 2;
            }
        }

        m_output = MathUtil.clamp(m_output, m_minOutput, m_maxOutput);

        m_last_measurement = m_measurement;
        return m_output;
    }

    public void reset() {
        m_last_measurement = 0;
        m_integral = 0;
    }
}