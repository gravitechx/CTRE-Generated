// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import dev.doglog.DogLog;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
  private SparkMax spark;
  private SparkMax follow;
  private SparkMaxConfig config = new SparkMaxConfig();
  private SparkMaxConfig config2 = new SparkMaxConfig();
  private SparkClosedLoopController sparkPID;
  public enum ElevatorState{
    BOTTOM,
    INTAKE,
    L1,
    L2,
    L3,
    L4,
    L2ALGAE,
    L3ALGAE,
    BARGE,
    PROCESSOR
  }
  ElevatorState eState = ElevatorState.BOTTOM;

    public ElevatorSubsystem(){
      spark = new SparkMax(4, MotorType.kBrushless);
      follow = new SparkMax(5, MotorType.kBrushless);
      sparkPID = spark.getClosedLoopController();
      config.closedLoop
      .p(0.1)
      .i(0)
      .d(0)
      .velocityFF(0)
      .outputRange(-0.2, 3);
      config.softLimit
        .forwardSoftLimitEnabled(true)
        .forwardSoftLimit(25.2)
        .reverseSoftLimitEnabled(true)
        .reverseSoftLimit(1);
      config.closedLoopRampRate(0.2);
      config.openLoopRampRate(0.5);
      config.idleMode(IdleMode.kBrake);

      config.closedLoop.maxMotion
        .maxVelocity(2)
        .allowedClosedLoopError(0.2)
        .maxAcceleration(0.01);
      
      config2.follow(4);
      follow.configure(config2, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
      spark.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

  public void setHeight(double height) { 
    sparkPID.setReference(height, ControlType.kPosition);
  }

  public void setState(String state){
    if(state.equals("BOTTOM")){
      eState = ElevatorState.BOTTOM;
    }
    else if(state.equals("INTAKE")){
      eState = ElevatorState.INTAKE;
    }
    else if(state.equals("L1")){
      eState = ElevatorState.L1;
    }
    else if(state.equals("L2")){
      eState = ElevatorState.L2;
    }
    else if(state.equals("L3")){
      eState = ElevatorState.L3;
    }
    else if(state.equals("L4")){
      eState = ElevatorState.L4;
    }
    else if(state.equals("L2ALGAE")){
      eState = ElevatorState.L2ALGAE;
    }
    else if(state.equals("L3ALGAE")){
      eState = ElevatorState.L3ALGAE;
    }
    else if(state.equals("BARGE")){
      eState = ElevatorState.BARGE;
    }
    else if(state.equals("PROCESSOR")){
      eState = ElevatorState.PROCESSOR;
    }
  }

  public void periodic(){
    DogLog.log("velocity", spark.getEncoder().getVelocity());
    DogLog.log("pos", spark.getEncoder().getPosition());
    DogLog.log("applied output", spark.getAppliedOutput());
    DogLog.log("voltage?", spark.getBusVoltage());
    DogLog.log("elevator state", eState);

    switch(eState){
      case BOTTOM:
        setHeight(0);
        break;
      case INTAKE:
        setHeight(1);
        break;
      case L1:
        setHeight(1); //idk this one yet
        break;
      case L2:
        setHeight(6.9);
        break;
      case L3:
        setHeight(14.6);
        break;
      case L4:
        setHeight(25.2);
        break;
      case L2ALGAE:
        setHeight(8); //idk yet
        break;
      case L3ALGAE:
        setHeight(12); //idk yet
        break;
      case BARGE:
        setHeight(25.2);
        break;
      case PROCESSOR:
        setHeight(1); //idk yet
        break;
    }
  }

  public boolean exampleCondition() {
    return false;
  }
}
