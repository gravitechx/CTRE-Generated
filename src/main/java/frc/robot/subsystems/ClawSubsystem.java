package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase{
    private final TalonFX motor = new TalonFX(17); //Change to actual wrist port
    private VoltageOut m_voltage;
    public enum ClawState{
        NUETRAL,
        CORAL_INTAKE,
        CORAL_OUTTAKE,
        ALGAE_INTAKE,
        ALGAE_OUTTAKE,
        ALGAE_HOLDING
    }
    ClawState cState = ClawState.NUETRAL;

    public ClawSubsystem() {
        motor.getConfigurator().apply(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive));
        m_voltage = new VoltageOut(0);
    }

    
    public void setMotor(double voltage) {
        motor.setControl(m_voltage.withOutput(voltage));
    }
    public double getEncoderRate(){
        return this.motor.getVelocity().getValueAsDouble();
    }

    public void setState(String state){
        if(state.equals("NUETRAL")){
            cState = ClawState.NUETRAL;
        }
        else if(state.equals("CORAL_INTAKE")){
            cState = ClawState.CORAL_INTAKE;
        }
        else if(state.equals("CORAL_OUTTAKE")){
            cState = ClawState.CORAL_OUTTAKE;
        }
        else if(state.equals("ALGAE_INTAKE")){
            cState = ClawState.ALGAE_INTAKE;
        }
        else if(state.equals("ALGAE_OUTTAKE")){
            cState = ClawState.ALGAE_OUTTAKE;
        }
        else if(state.equals("ALGAE_HOLDING")){
            cState = ClawState.ALGAE_HOLDING;
        }
    }


    public void periodic() {
        SmartDashboard.putNumber("Claw Speed", getEncoderRate());
        DogLog.log("Claw voltage", motor.getMotorVoltage().getValueAsDouble());

        switch(cState){
            case NUETRAL:
                setMotor(0);
                break;
            case CORAL_INTAKE:
                setMotor(-6);
                break;
            case CORAL_OUTTAKE:
                setMotor(4);
                break;
            case ALGAE_INTAKE:
                setMotor(-5);
                break;
            case ALGAE_OUTTAKE:
                setMotor(6);
                break;
            case ALGAE_HOLDING:
                setMotor(-1);
                break;
        }
        DogLog.log("claw state", cState);
    }

    }

