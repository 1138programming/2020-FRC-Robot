package frc.robot.enums;

public enum SolenoidState {
    DEFAULT(0), ACTIVE(1);

    private final int value;
    private SolenoidState(int value) {
       this.value = value;
    }
    
    public int getValue() {
        return value; 
    }
};
