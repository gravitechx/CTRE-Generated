package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase{
    private final SparkMax motor = Constants.ElevatorConstants.ElevatorMotorPort1;
    private final SparkMax motor2 = Constants.ElevatorConstants.ElevatorMotorPort2;
    private RelativeEncoder encoder = motor.getEncoder(); //used to be AbsoluteEncoder but these motors are relative. Should not affect performance.
    // private Boolean haveEncoder = !(encoder == null); //if there is not an encoder, it returns a null value
    //private Encoder newEncoder = new Encoder(0, 0);

    public Elevator(Pivot pivot) {
        encoder.setPosition(0);
        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.openLoopRampRate(0.2);
        motorConfig.closedLoopRampRate(0.2);
        motorConfig.follow(motor);
        motor2.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    
    public void setMotor(double speed) {
        motor.set(speed);
    }

    public void resetEncoder(double pos) {
        if (encoder.getPosition() < 5) {
        encoder.setPosition(pos);
        }
    }

    public double getEncoderPosition(){
        return encoder.getPosition();
    }

    public void setEncoder(double POS) {
        encoder.setPosition(POS);
    }

    public void periodic() {
        //See if this number will show up on SmartDashboard, and if it changes if you spin the motor.
        SmartDashboard.putNumber("Elevator Encoder?", encoder.getPosition()); //shows the encoder value on SmartDashboard
        // SmartDashboard.putBoolean("Encoder?", haveEncoder); //shows if the program recognizes the encoder or not
        //SmartDashboard.putNumber("Test", Math.random());

        // if(getEncoderPosition() >= 24){
        //     Constants.elevatorPOS = getEncoderPosition() - 2;
        // }
        // else if(getEncoderPosition() <= 2.5){
        //     Constants.elevatorPOS = 0;
        // }
         
        // SmartDashboard.putNumber("PLEASE POSE", kathunkVal());


    }

    public double kathunkVal() {
        if (Constants.elevatorLevel == 4) {
            return 0;
        } else if (Constants.elevatorLevel == 3) {
            return 0;
        } else if (Constants.elevatorLevel ==2) {
            return 0;
        } else if (Constants.elevatorLevel == 0) {
            // pivot.setMotor(15);
            return Constants.ElevatorConstants.groundIntakeHeight-2;
        } else{
            return 0;
        }
        
    }
        public double upthunkVal() {
            if (Constants.elevatorLevel == 4) {
                return Constants.ElevatorConstants.L4Height+2;
            } else if (Constants.elevatorLevel == 3) {
                return Constants.ElevatorConstants.L3Height+2;
            } else if (Constants.elevatorLevel ==2) {
                return Constants.ElevatorConstants.L2Height+2;
            } else if (Constants.elevatorLevel == 0) {
                // pivot.setMotor(15);
                return Constants.ElevatorConstants.groundIntakeHeight-2;
            } else{
                return 0;
            }
        }

    // public double upthunkVal() {
    //     if (Constants.elevatorLevel == 4) {
    //         return Constants.ElevatorConstants.L4Height+2;
    //     } else if (Constants.elevatorLevel == 3) {
    //         return Constants.ElevatorConstants.L3Height+2;
    //     } else if (Constants.elevatorLevel ==2) {
    //         return Constants.ElevatorConstants.L2Height+2;
    //     } else if (Constants.elevatorLevel == 0) {
    //         return Constants.ElevatorConstants.groundIntakeHeight-2;
    //     } else{
    //         return 10;
    //     }
        
    // }

    }

