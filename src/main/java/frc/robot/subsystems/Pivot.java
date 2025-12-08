package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
  // private SparkMax pivotMotor;
  // private RelativeEncoder pivotEncoder;
  // private DigitalInput sensor;
  private TalonFX pivotMotor;
  private TalonFXConfiguration config;
  private final PositionVoltage anglePOS = new PositionVoltage(0);

  private final DCMotorSim m_motorSimModel = new DCMotorSim(
    LinearSystemId.createDCMotorSystem(
       DCMotor.getKrakenX60Foc(1), 0.001, kGearRatio
    ),
    DCMotor.getKrakenX60Foc(1)
 );

  public Pivot() {
    config = new TalonFXConfiguration();
    config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 35;
    config.CurrentLimits.SupplyCurrentLowerLimit = 60;
    config.CurrentLimits.SupplyCurrentLowerTime =  .1;
    config.Slot0.kP = Constants.PivotConstants.pivotKP;
    config.Slot0.kI = Constants.PivotConstants.pivotKI;
    config.Slot0.kD = Constants.PivotConstants.pivotKD;
    
    pivotMotor = new TalonFX(9);
    pivotMotor.getConfigurator().apply(config);
    pivotMotor.setPosition(0);
    pivotMotor.setControl(anglePOS.withPosition(3.3));

    // pivotMotor = new SparkMax(18, MotorType.kBrushless);
    // sensor = new DigitalInput(0);

    // pivotEncoder = pivotMotor.getEncoder();
    // pivotMotor.configure(Constants.CoralConstants.coralMotorConfig,
    // Constants.CoralConstants.coralResetMode,
    // Constants.CoralConstants.coralPersistMode);

    // pivotEncoder.setPosition(0);

  }

  // public Boolean hasCoral() {
  // return sensor.get();
  // }

  public void setMotor(double POS) {
    // pivotMotor.set(speed);
    pivotMotor.setControl(anglePOS.withPosition(POS));
  }

  public void kathunk(double kathunkVal) {
    if (kathunkVal == -4) {
      setMotor(17.5);
    }
  }

  public double getEncoderPosition() {
    // return pivotEncoder.getPosition();
    return pivotMotor.getPosition().getValueAsDouble();
  }

  public void periodic() {
    SmartDashboard.putNumber("Pivot encoder", getEncoderPosition());
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