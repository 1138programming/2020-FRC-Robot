package frc.robot.enums;

public enum ColorLabel { 
    RED(0), GREEN(1), BLUE(2), YELLOW(3);

    private final int value;
    private ColorLabel(int value) {
    this.value = value;
    }
    
    public int getValue(){
        return value;
    }
};