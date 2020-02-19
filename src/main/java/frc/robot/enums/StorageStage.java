package frc.robot.enums;

public enum StorageStage { 
    STAGE1(0), STAGE2(1), BOTH(2);

    private final int value;
    private StorageStage(int value) {
       this.value = value;
    }
    
    public int getValue() {
        return value; 
    }
};