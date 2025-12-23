package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase{
    private final TalonFX motor = new TalonFX(17); //Change to actual wrist port
    private VoltageOut m_voltage;

    public Coral() {
        motor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive));
        m_voltage = new VoltageOut(0);
    }

    
    public void setMotor(double voltage) {
        motor.setControl(m_voltage.withOutput(voltage));
    }
    public double getEncoderRate(){
        return this.motor.getVelocity().getValueAsDouble();
    }
    public void periodic() {
        SmartDashboard.putNumber("Coral Speed", getEncoderRate());
        DogLog.log("coral voltage", motor.getMotorVoltage().getValueAsDouble());
    }

    }

