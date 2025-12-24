package frc.robot;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    

}
