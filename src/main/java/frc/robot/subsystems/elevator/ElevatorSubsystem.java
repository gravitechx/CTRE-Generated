// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

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
import frc.robot.subsystems.claw.ClawState;

public class ElevatorSubsystem extends SubsystemBase {
  private SparkMax spark;
  private SparkMax follow;
  private SparkMaxConfig config = new SparkMaxConfig();
  private SparkMaxConfig config2 = new SparkMaxConfig();
  private SparkClosedLoopController sparkPID;
  
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

  public void setHeight() { 
    sparkPID.setReference(eState.getHeight(), ControlType.kPosition);
  }

  public void setState(String state){
        eState = ElevatorState.valueOf(state);
        setHeight();
    }

  public void periodic(){
    DogLog.log("velocity", spark.getEncoder().getVelocity());
    DogLog.log("pos", spark.getEncoder().getPosition());
    DogLog.log("applied output", spark.getAppliedOutput());
    DogLog.log("voltage?", spark.getBusVoltage());
    DogLog.log("elevator state", eState);

    
    
  }

  public boolean exampleCondition() {
    return false;
  }
}
