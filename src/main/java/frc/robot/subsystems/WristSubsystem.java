package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class WristSubsystem extends SubsystemBase{
    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final SparkClosedLoopController motorPID;
    public enum WristState{
        HORIZANTAL,
        VERTICAL,
        FLIPPED
    }
    WristState wState = WristState.HORIZANTAL;

    public WristSubsystem() {
        motor = new SparkMax(18, MotorType.kBrushless);
        config  = new SparkMaxConfig();
        motorPID = motor.getClosedLoopController();
        config.idleMode(IdleMode.kBrake);
        SoftLimitConfig softLimit = new SoftLimitConfig();
        softLimit.forwardSoftLimit(0);
        softLimit.reverseSoftLimit(-4.6);
        softLimit.forwardSoftLimitEnabled(true);
        softLimit.reverseSoftLimitEnabled(true);
        config.apply(softLimit);

        config.closedLoop
            .p(0.1)
            .i(0)
            .d(0)
            .velocityFF(0)
            .outputRange(-0.13, 0.1);

        config.closedLoopRampRate(1);

        config.closedLoop.maxMotion
            .maxVelocity(1)
            .allowedClosedLoopError(0.1)
            .maxAcceleration(0.01);

        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    
    public void setPosition(double position) {
        motorPID.setReference(position, ControlType.kPosition);
    }

    public void setHorizantal(){
        motorPID.setReference(0, ControlType.kPosition);
    }

    public void setVertical(){
        motorPID.setReference(-2.7, ControlType.kPosition);
    }

    public void setFlip(){
        motorPID.setReference(-4.6, ControlType.kPosition);
    }

    public void setState(String state){
        if(state.equals("FLIPPED")){
            wState = WristState.FLIPPED;
        }
        else if(state.equals("HORIZANTAL")){
            wState = WristState.HORIZANTAL;
        }
        else if(state.equals("VERTICAL")){
            wState = WristState.VERTICAL;
        }
    }

    public void periodic() {
        switch(wState){
            case HORIZANTAL:
                setHorizantal();
                break;
            case VERTICAL:
                setVertical();
                break;
            case FLIPPED:
                setFlip();
                break;
        }
        DogLog.log("wrist state", wState);
    }
    }

