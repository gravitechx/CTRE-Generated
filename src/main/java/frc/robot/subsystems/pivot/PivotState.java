package frc.robot.subsystems.pivot;

public enum PivotState {
    BACK(0),
    IDLE(3.3),
    CORAL_INTAKE(13.1),
    DOWN(15),
    ALGAE_INTAKE(12), //idk this
    PROCESSOR(10), //idk this
    L1(5),
    L2L3(2.4),
    L4(3.6),
    SCORING(3.9);

    private double defaultPosition;
    
    PivotState(double position){
        this.defaultPosition = position;
    }

    public double getPosition(){
        return this.defaultPosition;
    }

}
