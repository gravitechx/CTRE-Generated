package frc.robot.subsystems.claw;


public enum ClawState {

    NUETRAL(0),
    CORAL_INTAKE(-6),
    CORAL_OUTTAKE(4),
    ALGAE_INTAKE(-3),
    ALGAE_OUTTAKE(5),
    ALGAE_HOLDING(-0.6);

    private final double defaultVoltage;
    
    ClawState(double voltage){
        this.defaultVoltage = voltage;
    }

    public double getVoltage(){
        return this.defaultVoltage;
    }
}
