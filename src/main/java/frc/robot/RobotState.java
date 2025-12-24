package frc.robot;

import frc.robot.subsystems.claw.ClawState;
import frc.robot.subsystems.elevator.ElevatorState;
import frc.robot.subsystems.pivot.PivotState;
import frc.robot.subsystems.wrist.WristState;

public enum RobotState {
    STARTING(ElevatorState.BOTTOM, PivotState.BACK, WristState.HORIZANTAL, ClawState.NUETRAL),
    IDLE(ElevatorState.BOTTOM, PivotState.IDLE, WristState.HORIZANTAL, ClawState.NUETRAL),

    CORAL_INTAKE(ElevatorState.INTAKE, PivotState.CORAL_INTAKE, WristState.VERTICAL, ClawState.CORAL_INTAKE),
    INTAKE_DOWN(ElevatorState.INTAKE, PivotState.DOWN, WristState.VERTICAL, ClawState.CORAL_INTAKE),
    CORAL_INTAKE_DONE(ElevatorState.BOTTOM, PivotState.IDLE, WristState.VERTICAL, ClawState.NUETRAL),

    ALGAE_INTAKE(ElevatorState.INTAKE, PivotState.ALGAE_INTAKE, WristState.VERTICAL, ClawState.ALGAE_INTAKE),
    ALGAE_INTAKE_DONE(ElevatorState.INTAKE, PivotState.IDLE, WristState.HORIZANTAL, ClawState.ALGAE_HOLDING),

    PROCESSOR(ElevatorState.BOTTOM, PivotState.PROCESSOR, WristState.VERTICAL, ClawState.ALGAE_HOLDING),
    PROCESSOR_SCORING(ElevatorState.BOTTOM, PivotState.PROCESSOR, WristState.VERTICAL, ClawState.ALGAE_OUTTAKE),
    PROCESSOR_DONE(ElevatorState.BOTTOM, PivotState.PROCESSOR, WristState.VERTICAL, ClawState.NUETRAL),
    
    L1(ElevatorState.L1, PivotState.L1, WristState.VERTICAL, ClawState.NUETRAL),
    L1_SCORING(ElevatorState.L1, PivotState.L1, WristState.VERTICAL, ClawState.CORAL_OUTTAKE),
    L1_DONE(ElevatorState.L1, PivotState.L1, WristState.VERTICAL, ClawState.NUETRAL),

    L2(ElevatorState.L2, PivotState.L2L3, WristState.HORIZANTAL, ClawState.NUETRAL),
    L2_SCORING(ElevatorState.L2, PivotState.SCORING, WristState.HORIZANTAL, ClawState.NUETRAL),

    L3(ElevatorState.L3, PivotState.L2L3, WristState.HORIZANTAL, ClawState.NUETRAL),
    L3_SCORING(ElevatorState.L3, PivotState.SCORING, WristState.HORIZANTAL, ClawState.NUETRAL),

    L2L3_DONE(ElevatorState.BOTTOM, PivotState.SCORING, WristState.HORIZANTAL, ClawState.NUETRAL),


    L4(ElevatorState.L4, PivotState.L4, WristState.HORIZANTAL, ClawState.NUETRAL),
    L4_SCORING(ElevatorState.BOTTOM, PivotState.L4, WristState.HORIZANTAL, ClawState.NUETRAL),

    ALGAE_L2(ElevatorState.L2ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.NUETRAL),
    ALGAE_L2_INTAKING(ElevatorState.L2ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.ALGAE_INTAKE),
    ALGAE_L2_DONE(ElevatorState.L2ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.ALGAE_HOLDING),

    ALGAE_L3(ElevatorState.L3ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.NUETRAL),
    ALGAE_L3_INTAKING(ElevatorState.L3ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.ALGAE_INTAKE),
    ALGAE_L3_DONE(ElevatorState.L3ALGAE, PivotState.L2L3, WristState.HORIZANTAL, ClawState.ALGAE_HOLDING),

    ALGAE_HOLDING(ElevatorState.BOTTOM, PivotState.BACK, WristState.HORIZANTAL, ClawState.NUETRAL),
    CORAL_OUTTAKE(ElevatorState.BOTTOM, PivotState.BACK, WristState.HORIZANTAL, ClawState.NUETRAL),
    ALGAE_OUTTAKE(ElevatorState.BOTTOM, PivotState.BACK, WristState.HORIZANTAL, ClawState.NUETRAL),
    WRIST_FLIPPED(ElevatorState.BOTTOM, PivotState.BACK, WristState.HORIZANTAL, ClawState.NUETRAL);

    private final ElevatorState eDefault;
    private final PivotState pDefault;
    private final WristState wDefault;
    private final ClawState cDefault;


    RobotState(ElevatorState eState, PivotState pState, WristState wState, ClawState cState){
       this.eDefault = eState;
       this.pDefault = pState;
       this.wDefault = wState;
       this.cDefault = cState;
    }

    public String getEState(){
        return this.eDefault.name();
    }
    public String getPState(){
        return this.pDefault.name();
    }
    public String getWState(){
        return this.wDefault.name();
    }
    public String getCState(){
        return this.cDefault.name();
    }

}
