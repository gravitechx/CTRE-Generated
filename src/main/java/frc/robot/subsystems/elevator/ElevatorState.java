package frc.robot.subsystems.elevator;

public enum ElevatorState {
    BOTTOM(0),
    INTAKE(1),
    L1(1),
    L2(6.9),
    L3(14.6),
    L4(25.2),
    L2ALGAE(8), //idk
    L3ALGAE(12), //idk
    PROCESSOR(1);

    private final double defaultHeight;

    ElevatorState(double height){
        this.defaultHeight = height;
    }

    public double getHeight(){
        return this.defaultHeight;
    }
}
