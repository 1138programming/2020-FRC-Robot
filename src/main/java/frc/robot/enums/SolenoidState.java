package frc.robot.enums;

public enum SolenoidState {
    DEFAULT(1), ACTIVE(0);

    private final int value;
    private SolenoidState(int value) {
       this.value = value;
    }
    
    public int getValue() {
        return value; 
    }
};
