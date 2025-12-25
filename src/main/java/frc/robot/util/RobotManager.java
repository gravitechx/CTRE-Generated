package frc.robot.util;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.claw.ClawSubsystem;
import frc.robot.subsystems.elevator.ElevatorState;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.wrist.WristSubsystem;

public class RobotManager extends SubsystemBase{

    ElevatorSubsystem elevator = new ElevatorSubsystem();
    public PivotSubsystem pivot = new PivotSubsystem();
    WristSubsystem wrist = new WristSubsystem();
    ClawSubsystem claw = new ClawSubsystem();

    RobotState botState = RobotState.IDLE;

    public void setState(String state, double ElevDelay){
        botState = RobotState.valueOf(state);
        new SequentialCommandGroup(
            new InstantCommand(() -> setWState(botState.getWState())),
            new InstantCommand(() -> setPState(botState.getPState())),
            new WaitCommand(ElevDelay),
            new InstantCommand(() -> setEState(botState.getEState())),
            new InstantCommand(() -> setCState(botState.getCState()))
        ).schedule();
    }

    public RobotState getState(){
        return botState;
    }

    public void setEState(String state){
        elevator.setState(state);
    }

    public void setPState(String state){
        pivot.setState(state);
    }

    public void setWState(String state){
        wrist.setState(state);
    }

    public void setCState(String state){
        claw.setState(state);
    }

    public void periodic(){
        DogLog.log("bot state", botState);
    }

    public void rightTrigger(){
        switch(botState){
            case L1:
                setState("L1_SCORING", 0);
                break;
            case L2:
                setState("L2_SCORING", 0);
                setState("L2L3_DONE", .2);
                break;
            case L2_FLIPPED:
                setState("L2_SCORING_FLIPPED", 0);
                setState("L2L3_DONE_FLIPPED", .2);
                break;
            case L3:
                setState("L2_SCORING", 0);
                setState("L2L3_DONE", .2);
                break;
            case L3_FLIPPED:
                setState("L3_SCORING_FLIPPED", 0);
                setState("L2L3_DONE_FLIPPED", .2);
                break;
            case L4:
                setState("L4_SCORING", 0);
                break;
            case L4_FLIPPED:
                setState("L4_SCORING_FLIPPED", 0);
                break;
            case PROCESSOR:
                setState("PROCESSOR_SCORING", 0);
                break;
            case CORAL_INTAKE:
                setState("INTAKE_DOWN", 0);
                break;
            default: setState(botState.name(), 0);

        }
    }

    public void rightTriggerFalse(){
        switch(botState){
            case INTAKE_DOWN:
                setState("CORAL_INTAKE_DONE", 0);
                break;
            case PROCESSOR_SCORING:
                setState("PROCESSOR_DONE", 0);
                break;
            case L1_SCORING:
                setState("L1_DONE", 0);
                break;
            default: setState(botState.name(), 0);
        }
    }

    public void leftTrigger(){
        switch(botState){
            case ALGAE_L2:
                setState("ALGAE_L2_INTAKING", 0);
                break;
            case ALGAE_L3:
                setState("ALGAE_L3_INTAKING", 0);
                break;
            default: setState("CORAL_INTAKE", 0);
        }
    }

    public void flip(){
        switch(botState){
            case L2:
                setState("L2_FLIPPED", 0);
                break;
            case L3:
                setState("L3_FLIPPED", 0);
                break;
            case L4:
                setState("L4_FLIPPED", 0);
                break;
            default: setState("IDLE_FLIPPED", 0);
        }
    }

    public void a(){
        switch(botState){
            case ALGAE_L2_DONE:
                setState("ALGAE_HOLDING", 0);
                break;
            case ALGAE_HOLDING:
                setState("PROCESSOR", 0);
            default: setState(botState.name(), 0);
        }
    }

    public void b(){
        switch(botState){
            case L3_FLIPPED:
                setState("L2_FLIPPED", 0);
                break;
            case L4_FLIPPED:
                setState("L2_FLIPPED", 0);
                break;
            case IDLE_FLIPPED:
                setState("L2_FLIPPED", 0);
                break;
            default: setState("L2", 0);
        }
    }

    public void x(){
        switch(botState){
            case L2_FLIPPED:
                setState("L3_FLIPPED", 0);
                break;
            case L4_FLIPPED:
                setState("L3_FLIPPED", 0);
                break;
            case IDLE_FLIPPED:
                setState("L3_FLIPPED", 0);
                break;
            default: setState("L3", 0);
        }
    }

    public void y(){
        switch(botState){
            case L2_FLIPPED:
                setState("L4_FLIPPED", 0);
                break;
            case L3_FLIPPED:
                setState("L4_FLIPPED", 0);
                break;
            case IDLE_FLIPPED:
                setState("L4_FLIPPED", 0);
                break;
            default: setState("L4", 0);
        }
    }
    

}
