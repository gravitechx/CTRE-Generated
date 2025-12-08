package frc.robot.subsystems;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.positional.Arm;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.hardware.TalonFX;

import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class PivotSubsystem extends SubsystemBase{

    private SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
    .withControlMode(ControlMode.CLOSED_LOOP)
    // Feedback Constants (PID Constants)
    .withClosedLoopController(50, 0, 0, DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
    .withSimClosedLoopController(50, 0, 0, DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
    // Feedforward Constants
    .withFeedforward(new ArmFeedforward(0.1, 0.1, 0.1))
    .withSimFeedforward(new ArmFeedforward(0.1, 0.1, 0.1))
    // Telemetry name and verbosity level
    .withTelemetry("ArmMotor", TelemetryVerbosity.HIGH)
    // Gearing from the motor rotor to final shaft.
    // In this example GearBox.fromReductionStages(3,4) is the same as GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your motor.
    // You could also use .withGearing(12) which does the same thing.
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(3, 4)))
    // Motor properties to prevent over currenting.
    .withMotorInverted(true)
    .withIdleMode(MotorMode.BRAKE)
    .withStatorCurrentLimit(Amps.of(40))
    .withClosedLoopRampRate(Seconds.of(0.25))
    .withOpenLoopRampRate(Seconds.of(0.25));

    private TalonFX pivotMotor = new TalonFX(9);

    private SmartMotorController sparkSmartMotorController = new TalonFXWrapper(pivotMotor, DCMotor.getKrakenX60(1), smcConfig);

    private ArmConfig pivotCfg = new ArmConfig(sparkSmartMotorController)
    // Soft limit is applied to the SmartMotorControllers PID
    .withSoftLimits(Degrees.of(-21.923), Degrees.of(90))
    // Hard limit is applied to the simulation.
    .withHardLimit(Degrees.of(-30), Degrees.of(90))
    // Starting position is where your arm starts
    .withStartingPosition(Degrees.of(-21.923))
    // Length and mass of your arm for sim.
    .withLength(Feet.of(1.31175))
    .withMass(Pounds.of(10))
    // Telemetry name and verbosity for the arm.
    .withTelemetry("Pivot", TelemetryVerbosity.HIGH);

    // Arm Mechanism
    private Arm pivot = new Arm(pivotCfg);

    /**
   * Set the angle of the arm.
   * @param angle Angle to go to.
   */
  public Command setAngle(Angle angle) { return pivot.setAngle(angle);}

  /**
   * Move the arm up and down.
   * @param dutycycle [-1, 1] speed to set the arm too.
   */
  public Command set(double dutycycle) { return pivot.set(dutycycle);}

  /**
   * Run sysId on the {@link Arm}
   */
  public Command sysId() { return pivot.sysId(Volts.of(7), Volts.of(2).per(Second), Seconds.of(4));}

    
    
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        pivot.updateTelemetry();
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        pivot.simIterate();
    }
}
