package frc.robot.enums;

public enum ShiftState { 
    
    LOW(0), HIGH(1);

    private final int value;
    private ShiftState(int value) {
       this.value = value;
    }
    
    public int getValue() {
        return value; 
    }

};