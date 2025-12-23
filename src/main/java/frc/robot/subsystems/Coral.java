package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase{
    private final TalonFX motor = new TalonFX(17); //Change to actual wrist port

    public Coral() {
        motor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive));
    }

    
    public void setMotor(double speed) {
        motor.set(-speed);
    }
    public double getEncoderRate(){
        return this.motor.getVelocity().getValueAsDouble();
    }
    public void periodic() {
        SmartDashboard.putNumber("Coral Speed", getEncoderRate());
    }

    }

