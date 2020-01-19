package frc.robot.enums;

public enum RotationDirection { 

    CLOCKWISE(0), COUNTERCLOCKWISE(1), NONE(2);

    private final int value;
    private RotationDirection(int value) {
       this.value = value;
    }
    
    public int getValue(){
        return value; 
    }
};
