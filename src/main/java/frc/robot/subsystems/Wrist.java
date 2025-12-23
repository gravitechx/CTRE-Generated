package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Wrist extends SubsystemBase{
    private final SparkMax motor = new SparkMax(18, MotorType.kBrushless); //Change to actual wrist port
    private final SparkMaxConfig config;
    private final SparkClosedLoopController motorPID;

    public Wrist() {
        config  = new SparkMaxConfig();
        motorPID = motor.getClosedLoopController();
        config.idleMode(IdleMode.kBrake);

        SoftLimitConfig softLimit = new SoftLimitConfig();
        softLimit.forwardSoftLimit(0);
        softLimit.reverseSoftLimit(-4.5);
        softLimit.forwardSoftLimitEnabled(true);
        softLimit.reverseSoftLimitEnabled(true);
        config.apply(softLimit);

        config.closedLoop
            .p(0.4)
            .i(0)
            .d(0)
            .velocityFF(0)
            .outputRange(0.1, 0.1);

        config.closedLoopRampRate(0.3);

        config.closedLoop.maxMotion
            .maxVelocity(1)
            .allowedClosedLoopError(0.2)
            .maxAcceleration(0.01);

        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    
    public void setPosition(double position) {
        motorPID.setReference(position, ControlType.kPosition);
    }

    public void periodic() {

    }
    }

