package frc.robot.subsystems.wrist;

public enum WristState {
    HORIZANTAL(0),
    VERTICAL(-2.7),
    FLIPPED(-4.6);

    private final double defaultPos;
    WristState(double pos){
        this.defaultPos = pos;
    }

    public double getPosition(){
        return this.defaultPos;
    }
}
