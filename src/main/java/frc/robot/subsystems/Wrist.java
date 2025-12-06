package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
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
    private RelativeEncoder encoder = motor.getEncoder(); //used to be AbsoluteEncoder but these motors are relative. Should not affect performance.
    // private Boolean haveEncoder = !(encoder == null); //if there is not an encoder, it returns a null value
    public static double wristPosition;
    //private Encoder newEncoder = new Encoder(0, 0);
    public Wrist() {
        encoder.setPosition(0);
        SparkMaxConfig motorConfig = new SparkMaxConfig();
        //motorConfig.follow(motor);
        motorConfig.idleMode(IdleMode.kBrake);
        SoftLimitConfig softLimit = new SoftLimitConfig();
        softLimit.forwardSoftLimit(0);
        softLimit.reverseSoftLimit(-4.5);
        softLimit.forwardSoftLimitEnabled(true);
        softLimit.reverseSoftLimitEnabled(true);
        motorConfig.apply(softLimit);
        motor.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    
    public void setMotor(double speed) {
        motor.set(speed);
    }

    public double getEncoderPosition(){
        return encoder.getPosition();
    }



    

    public double flipWristVertical() {
        if (Math.abs(encoder.getPosition()) > 1 ) {
            return -4.5;
        } else {
            return 0;
        }
    }

    public void periodic() {
        //See if this number will show up on SmartDashboard, and if it changes if you spin the motor.
        SmartDashboard.putNumber("Wrist Encoder?", encoder.getPosition()); //shows the encoder value on SmartDashboard
        // SmartDashboard.putBoolean("Encoder?", haveEncoder); //shows if the program recognizes the encoder or not
        //SmartDashboard.putNumber("Test", Math.random());
    }

    public void wristSwitch(double wristPosition){

        if(encoder.getPosition() >= 2){
            wristPosition = 2.34;
        } else if (encoder.getPosition() <= 1){
            wristPosition = 0;
        }
    }

    }

