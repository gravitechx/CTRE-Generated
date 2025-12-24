package frc.robot.subsystems.pivot;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.claw.ClawState;

public class PivotSubsystem extends SubsystemBase {
  private TalonFX pivotMotor;
  private TalonFXConfiguration config;
  private MotionMagicVoltage m_request;

  private final DCMotorSim m_motorSimModel = new DCMotorSim(
    LinearSystemId.createDCMotorSystem(
       DCMotor.getKrakenX60Foc(1), 0.001, kGearRatio
    ),
    DCMotor.getKrakenX60Foc(1)
  );

  PivotState pState = PivotState.BACK;
  

  public PivotSubsystem() {
    config = new TalonFXConfiguration();
    config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 35;
    config.CurrentLimits.SupplyCurrentLowerLimit = 60;
    config.CurrentLimits.SupplyCurrentLowerTime =  .1;
    config.Slot0.kP = 4.5;
    config.Slot0.kI = 0;
    config.Slot0.kD = 0.1;
    config.Slot0.kS = 0.2;
    config.Slot0.kV = 0.11;
    config.Slot0.kA = 0;
    config.Slot0.kG = 0;

    config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 15.2;
    config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 1;
    config.MotionMagic.MotionMagicCruiseVelocity = 60;
    config.MotionMagic.MotionMagicAcceleration = 50;
    config.MotionMagic.MotionMagicJerk = 200;

    
    pivotMotor = new TalonFX(9);
    pivotMotor.getConfigurator().apply(config);
    pivotMotor.setPosition(0);

    m_request = new MotionMagicVoltage(0);

  }

  public void setPos() {
    pivotMotor.setControl(m_request.withPosition(pState.getPosition()));
  }

  public double getEncoderPosition() {
    return pivotMotor.getPosition().getValueAsDouble();
  }

  public double getKathuk() {
    if(pivotMotor.getPosition().getValueAsDouble()>12) {
      return 15;
    } else {
      return 3.9;
    }
  }

  public void setState(String state){
        pState = PivotState.valueOf(state);
        setPos();
    }

  public String getState(){
    return pState.name();
}

  public void periodic() {
    SmartDashboard.putNumber("Pivot encoder", getEncoderPosition());
    DogLog.log("pivot velocity", pivotMotor.getVelocity().getValueAsDouble());
    DogLog.log("pivot voltage", pivotMotor.getMotorVoltage().getValueAsDouble());
    DogLog.log("pivot pos", pivotMotor.getPosition().getValueAsDouble());

    DogLog.log("pivot state", pState);

  }

  private static final double kGearRatio = 10.0;
  

public void simulationPeriodic() {
   var talonFXSim = pivotMotor.getSimState();

   // set the supply voltage of the TalonFX
   talonFXSim.setSupplyVoltage(RobotController.getBatteryVoltage());

   // get the motor voltage of the TalonFX
   var motorVoltage = talonFXSim.getMotorVoltageMeasure();

   // use the motor voltage to calculate new position and velocity
   // using WPILib's DCMotorSim class for physics simulation
   m_motorSimModel.setInputVoltage(motorVoltage.in(Volts));
   m_motorSimModel.update(0.020); // assume 20 ms loop time

   // apply the new rotor position and velocity to the TalonFX;
   // note that this is rotor position/velocity (before gear ratio), but
   // DCMotorSim returns mechanism position/velocity (after gear ratio)
   talonFXSim.setRawRotorPosition(m_motorSimModel.getAngularPosition().times(kGearRatio));
   talonFXSim.setRotorVelocity(m_motorSimModel.getAngularVelocity().times(kGearRatio));
}

}