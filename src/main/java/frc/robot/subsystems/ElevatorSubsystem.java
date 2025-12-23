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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {

  // Vendor motor controller object
  private SparkMax spark = new SparkMax(4, MotorType.kBrushless);
  private SparkMax follow = new SparkMax(5, MotorType.kBrushless);
  private SparkMaxConfig config = new SparkMaxConfig();
  private SparkMaxConfig config2 = new SparkMaxConfig();
  private SparkClosedLoopController sparkPID = spark.getClosedLoopController();

    public ElevatorSubsystem(){
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
  /**
   * Set the height of the elevator.
   * @param angle Distance to go to.
   */
  public void setHeight(double height) { 
    sparkPID.setReference(height, ControlType.kPosition);
  }

  /**
   * Move the elevator up and down.
   * @param dutycycle [-1, 1] speed to set the elevator too.
   */
  public void set(double dutycycle) { spark.setVoltage(dutycycle);}

  /**
   * Run sysId on the {@link Elevator}
   */

  /** Creates a new ExampleSubsystem. */

  /**
   * Example command factory method.
   *
   * @return a command
   */

  public void periodic(){
    DogLog.log("velocity", spark.getEncoder().getVelocity());
    DogLog.log("pos", spark.getEncoder().getPosition());
    DogLog.log("applied output", spark.getAppliedOutput());
    DogLog.log("voltage?", spark.getBusVoltage());


  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }
}
