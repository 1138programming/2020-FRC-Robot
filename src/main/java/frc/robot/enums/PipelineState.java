package frc.robot.enums;

public enum PipelineState { 
    
    DEFAULT(1), TARGETING(0);

    private final int value;
    private PipelineState(int value) {
       this.value = value;
    }
    
    public int getValue() {
        return value; 
    }

};