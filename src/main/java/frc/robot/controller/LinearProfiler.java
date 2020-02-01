package frc.robot.controller;

import edu.wpi.first.wpilibj.controller.PIDController;

class LinearProfiler {
    private PIDController posPID; // Internal position PID

    private double lastPos = 0; // Last position measurement
    private double lastVel = 0; // Last velocity measurement

    private double vel = 0; // Current velocity measurement
    private double accel = 0; // Current acceleration measurement

    private double period = 0.02; // Period of the LinearProfiler (in seconds). Delta t used to calculate velocity and acceleration
    //private int dt = 0;

    private double measurement; // Current position measurement

    private double maxAccel = Double.POSITIVE_INFINITY; // Maximum acceleration of the profile
    private double maxVel = Double.NEGATIVE_INFINITY; // Maximum velocity of the profile

    private double t_accel = 0; // Current target acceleration
    private double t_vel = 0; // Current target velocity
    private double t_pos = 0; // Current target position
    private double pidSetpoint = 0; // Current setpoint for the position PID

    private double initialPos = 0; // Initial position measurement
    private double flatPoint = 0; // Position at which the velocity profile becomes flat (i.e. acceleration stops)
    private double midPoint = 0; // The middle position of the profile
    private double deccelPoint = 0; // Position at which the velocity profile stops being flat (i.e. accleration starts again)
    private double targetPos = 0; // The profile's final target position
    private double distance = 0; // The difference between the target position and the initial position

    private int dir = 0; // Indicates whether the profile is backwards or forwards, i.e. whether the target position is smaller or greater than the initial position

    private double kP = 1; // P gain for the position PID
    private double kI = 0; // I gain for the position PID
    private double kD = 0; // D gain for the position PID

    /**
     * Creates a LinearProfiler with the given max velocity, max acceleration, PID constants, and period
     * 
     * @param maxVel    Maximum velocity
     * @param maxAccel  Maximum acceleration
     * @param kP        P gain
     * @param kI        I gain
     * @param kD        D gain
     * @param period    Controller period
     */
    public LinearProfiler(double maxVel, double maxAccel, float kP, float kI, float kD, double period) {
        this.maxVel = maxVel;
        this.maxAccel = maxAccel;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.period = period;
      
        posPID = new PIDController(kP, kI, kD, period);
    }

    /**
     * Creates a LinearProfiler with the given max velocity, max acceleration, PID constants. 
     * The default period is 0.02 seconds
     * 
     * @param maxVel    Maximum velocity
     * @param maxAccel  Maximum acceleration
     * @param kP        P gain
     * @param kI        I gain
     * @param kD        D gain
     */
    public LinearProfiler(double maxVel, double maxAccel, float kP, float kI, float kD) {
        this(maxVel, maxAccel, kP, kI, kD, 0.02);
    }

    /**
     * Creates a LinearProfiler with the given max velocity and max acceleration. 
     * The default PID constants are 1, 0, and 0 respectively.
     * The default period is 0.02 seconds
     * 
     * @param maxVel
     * @param maxAccel
     */
    public LinearProfiler(double maxVel, double maxAccel) {
        this(maxVel, maxAccel, 1, 0, 0);
    }

    /**
     * Creates a LinearProfiler.
     * The default max velocity and max acceleration are both positive infinity (this implies a profile which constantly accelerates until its reached half its target, then constantly deccelerates).
     * The default PID constants are 1, 0, and 0 respectively.
     * The default period is 0.02 seconds
     */
    public LinearProfiler() {
        this(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    // Functions to set constants

    /**
     * Sets the maximum velocity
     * 
     * @param maxVel    The maximum velocity
     */
    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
    }

    /**
     * Sets the maximum acceleration
     * 
     * @param maxAccel  The maximum acceleration
     */
    public void setMaxAccel(double maxAccel) {
        this.maxAccel = maxAccel;
    }

    /**
     * Sets the position PID's position tolerance
     * 
     * @param positionTolerance The position tolerance
     */
    public void setTolerance(double positionTolerance) {
        posPID.setTolerance(positionTolerance);
    }

    /**
     * Sets the position PID's position and velocity tolerances
     * 
     * @param positionTolerance The position tolerance
     * @param velocityTolerance The velocity tolerance
     */
    public void setTolerance(double positionTolerance, double velocityTolerance) {
        posPID.setTolerance(positionTolerance, velocityTolerance);
    }

    // Target functions

    /**
     * Sets the profile's target position
     * 
     * @param targetPos The target position
     */
    public void setTarget(double targetPos) {
        this.targetPos = targetPos;
    }

    /**
     * Sets the profile's target position relative to its current position.
     * 
     * @param targetPos The relative target position
     */
    public void setTargetRelative(double targetPos) {
        this.targetPos = measurement + targetPos;
    }

    /**
     * Gets the profile's target position
     */
    public double getTarget() {
        return targetPos;
    }

    // PID Functions

    /**
     * Returns the position PID object
     */
    public PIDController getPID() {
        return posPID;
    }

    // Functions that move the motor

    /**
     * Calculates the midpoint, flat point, and deccel point for the profile
     * Gives initial values to the target position, target velocity, target acceleration, and position PID setpoint
     * Resets position PID
     * 
     * @param measurement   Initial position
     */
    public void init(double measurement) {
        this.measurement = measurement;
        initialPos = measurement;
        distance = Math.abs(targetPos - initialPos);
        midPoint = distance / 2;
        flatPoint = (0.5 * maxVel * maxVel / maxAccel); // Derived using the 3 amigos
      
        // Gets the direction of the profile and sets the initial target acceleration
        if (initialPos < targetPos) {
          dir = 1;
          t_accel = maxAccel;
        } else {
          dir = -1;
          t_accel = -maxAccel;
        }
      
        // Figures out whether the velocity profile will be a trapezoid or a triangle
        if (midPoint > flatPoint) {
            // A trapezoid means the profile reaches its maximum velocity. It accelerates to the maximum velocity, then starts deccelerating at the deccel point until it reaches the target position
            deccelPoint = distance - flatPoint;
        } else {
            // A triangle means that the profile does not reach its maximum velocity. It accelerates until it has gotten to the profile's midpoint, then deccelerates to the target position
            flatPoint = midPoint;
            deccelPoint = midPoint;
        }
      
        t_pos = 0;
        pidSetpoint = initialPos;
        posPID.setSetpoint(pidSetpoint);
        posPID.reset();
      
        vel = 0;
        accel = 0;
        lastPos = initialPos;
        lastVel = 0;
    }

    /**
     * Calculates the PWM output given the current position measurement
     * 
     * @param measurement   The current position measurement
     * @return              The position PID's PWM output
     */
    public double calculate(double measurement) {
        this.measurement = measurement;
        //std::uint32_t time = pros::millis();
      
        //dt = int(time - lastTime);
        //vel = (double)(getSensorValue() - lastPos) / dt;
        vel = (measurement - lastPos) / period; // Calcultes the actual velocity
        //accel = (vel - lastVel) / dt;
        accel = (vel - lastVel) / period; // Calculates the actual acceleration
      
        //lastTime = time;

        // Updates previous values
        lastPos = measurement;
        lastVel = vel;
      
        // Sets the target acceleration depending on where in the profile we are
        if (t_pos < flatPoint) {
          t_accel = maxAccel;
        } else if (t_pos < deccelPoint) {
          t_accel = 0;
        } else {
          t_accel = -maxAccel;
        }
        //t_vel += t_accel * dt;
        t_vel += t_accel * period; // Updates the target velocity

        // Clamps the target velocity to between its maximum and minimum values, and makes sure the target acceleration doesn't overshoot
        if (t_vel > maxVel) {
          t_vel = maxVel;
          t_accel = 0;
        } else if (t_vel < 0) {
          t_vel = 0;
          t_accel = 0;
        }
        //t_pos += t_vel * dt;
        t_pos += t_vel * period; // Updates the target position
      
        pidSetpoint = dir * t_pos + initialPos; // Corrects the target position for the profile's direction and starting position to get the position PID setpoint
        posPID.setSetpoint(pidSetpoint); // Sets the position PID's setpoint
        return posPID.calculate(measurement); // Returns the calculated PWM value
    }

    /**
     * Returns true if the profile has completed and the position PID has reached its setpoint
     * 
     * @return  Whether the profile has reached its target
     */
    public boolean atTarget() {
        if (Math.abs(measurement) > deccelPoint) {
          return (t_vel == 0 && posPID.atSetpoint());
        }
        return false;
    }

    // Getters for internal variables

    /**
     * Gets the current target position
     */
    public double getTargetPos() {
        return pidSetpoint;
    }

    /**
     * Gets the current target velocity
     */
    public double getTargetVel() {
        return t_vel;
    }

    /**
     * Gets the current target acceleration
     */
    public double getTargetAccel() {
        return t_accel;
    }

    /**
     * Gets the measured position
     */
    public double getPos() {
        return measurement;
    }

    /**
     * Gets the measured velocity
     */
    public double getVel() {
        return vel;
    }

    /**
     * Gets the measured acceleration
     */
    public double getAccel() {
        return accel;
    }

    /**
     * Gets the controller's period
     */
    public double getPeriod() {
        return posPID.getPeriod();
    }

    /*public int getDeltaTime() {
        return dt;
    }*/
};