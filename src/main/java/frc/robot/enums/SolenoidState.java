package frc.robot.enums;

public enum SolenoidState {
    
    ACTIVE(0), DEFAULT(1);

    private final int value;
    private SolenoidState(int value) {
       this.value = value;
    }
    
    public int getValue(){
        return value; 
    }

};