package frc.robot;

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
    PivotSubsystem pivot = new PivotSubsystem();
    WristSubsystem wrist = new WristSubsystem();
    ClawSubsystem claw = new ClawSubsystem();

    RobotState botState = RobotState.STARTING;

    public void setState(String state){
        botState = RobotState.valueOf(state);
        setEState(botState.getEState());
        setPState(botState.getPState());
        setWState(botState.getWState());
        setCState(botState.getCState());
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
        if(botState.name().equals("L1")){
            setState("L1_SCORING");
        }
        else if(botState.name().equals("L2")){
                setState("L2_SCORING");
                new WaitCommand(0.1);
                setState("L2L3_DONE");
        } 
        else if(botState.name().equals("L2_FLIPPED")){
                setState("L2_SCORING_FLIPPED");
                new WaitCommand(0.1);
                setState("L2L3_DONE_FLIPPED");
        } 
        else if(botState.name().equals("L3")){
                setState("L2_SCORING");
                new WaitCommand(0.1);
                setState("L2L3_DONE");
        } 
        else if(botState.name().equals("L4")){
             setState("L4_SCORING");
        } 
        else if(botState.name().equals("PROCESSOR")){
             setState("PROCESSOR_SCORING");
        }
        else if(botState.name().equals("CORAL_INTAKE")){
             setState("INTAKE_DOWN");
        }
        else{ setState(botState.name());}
    }

    public void rightTriggerFalse(){
        if(botState.name().equals("INTAKE_DOWN")){
             setState("CORAL_INTAKE_DONE");
        }
        else if(botState.name().equals("PROCESSOR_SCORING")){
             setState("PROCESSOR_DONE");
        } 
        else if(botState.name().equals("L1_SCORING")){
             setState("L1_DONE");
        }
        else{ setState(botState.name());}
    }
    

}
