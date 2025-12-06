package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase{
    private final TalonFX motor = new TalonFX(17); //Change to actual wrist port
    // private RelativeEncoder encoder = motor.getEncoder(); //used to be AbsoluteEncoder but these motors are relative. Should not affect performance.
    // private Boolean haveEncoder = !(encoder == null); //if there is not an encoder, it returns a null value
    //private Encoder newEncoder = new Encoder(0, 0);

    public Coral() {
        motor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive));

        // encoder.setPosition(0);
        // SparkMaxConfig motorConfig = new SparkMaxConfig();
        // motorConfig.smartCurrentLimit(120);
        //motorConfig.follow(motor);
        // motorConfig.idleMode(IdleMode.kBrake);
        // motor.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    
    public void setMotor(double speed) {
        motor.set(-speed);
    }

    // public double getEncoderPosition(){
    //     return encoder.getPosition();
    // }

    // public double getEncoderSpeed() {
    //     return encoder.getVelocity();
    // }
    public double getEncoderRate(){
        return this.motor.getVelocity().getValueAsDouble();
    }
    public void periodic() {
        SmartDashboard.putNumber("Coral Speed", getEncoderRate());
        //See if this number will show up on SmartDashboard, and if it changes if you spin the motor.
        // SmartDashboard.putNumber("Coral Encoder?", encoder.getPosition()); //shows the encoder value on SmartDashboard
        //SmartDashboard.putBoolean("Encoder?", haveEncoder); //shows if the program recognizes the encoder or not
        //SmartDashboard.putNumber("Test", Math.random());
        // SmartDashboard.putNumber("ASDFDS", motor.get());
        // SmartDashboard.putNumber("motor ss", motor.getAppliedOutput());
    }

    }

