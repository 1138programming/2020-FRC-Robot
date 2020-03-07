package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Util {
    public static double wrapInput(double input, double minInput, double maxInput) {
        double inputRange = maxInput - minInput;
        if (input > 0) {
            return (input - minInput) % inputRange + minInput;
        } else {
            return (input - minInput) % inputRange + minInput + inputRange;
        }
    }
}