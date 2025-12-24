package frc.robot.subsystems.claw;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase{
    private final TalonFX motor = new TalonFX(17); //Change to actual wrist port
    private VoltageOut m_voltage;
    
    ClawState cState = ClawState.NUETRAL;

    public ClawSubsystem() {
        motor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive));
        m_voltage = new VoltageOut(0);
    }

    
    public void setMotor() {
        motor.setControl(m_voltage.withOutput(cState.getVoltage()));
    }
    public double getEncoderRate(){
        return this.motor.getVelocity().getValueAsDouble();
    }

    public void setState(String state){
        cState = ClawState.valueOf(state);
        setMotor();
    }

    public String getState(){
        return cState.name();
    }


    public void periodic() {
        SmartDashboard.putNumber("Claw Speed", getEncoderRate());
        DogLog.log("Claw voltage", motor.getMotorVoltage().getValueAsDouble());

        DogLog.log("claw state", cState);
    }

    }

