// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.time.Instant;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.IntakeSourceCMD;
// import frc.robot.commands.PositionArmCMD;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Wrist;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)*0.5; // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.5).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.2).withRotationalDeadband(MaxAngularRate * 0.2) // Add a 20% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest .PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private boolean isCoralMode = true;
    private Pivot pivot = new Pivot();
    private ElevatorSubsystem elevSub = new ElevatorSubsystem();
    private Wrist wrist = new Wrist();
    // private Elevator elevator = new Elevator(pivot);
    private Coral coral = new Coral();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate*.5) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        Trigger isCoralMode = new Trigger(() -> this.isCoralMode);
        Trigger isAlgaeMode = isCoralMode.negate();

        // joystick.a().whileTrue(pivotSub.setAngle(Degrees.of(-20)));
        // joystick.b().whileTrue(pivotSub.setAngle(Degrees.of(60)));
        // Schedule `set` when the Xbox controller's B button is pressed,
        // cancelling on release.
        // joystick.x().whileTrue(pivotSub.set(0.3));
        // joystick.y().whileTrue(pivotSub.set(-0.3));

        // Schedule `setHeight` when the Xbox controller's B button is pressed,
        // cancelling on release.
        // joystick.a().whileTrue(new InstantCommand(() -> elevSub.setHeight(1)));
        // joystick.b().whileTrue(new InstantCommand(() -> elevSub.setHeight(5)));
        // joystick.x().onTrue(new InstantCommand(() -> elevSub.setHeight(12)));
        // joystick.y().onTrue(new InstantCommand(() -> elevSub.setHeight(17)));
        // joystick.rightBumper().onTrue(new InstantCommand(() -> elevSub.setHeight(24.5)));

        // joystick.a().onTrue(new InstantCommand(() -> coral.setMotor(-5)));
        // joystick.a().onFalse(new InstantCommand(() -> coral.setMotor(0)));
        // joystick.b().onTrue(new InstantCommand(() -> coral.setMotor(5)));
        // joystick.b().onFalse(new InstantCommand(() -> coral.setMotor(0)));
        // joystick.x().onTrue(new InstantCommand(() -> coral.setMotor(0)));
        // joystick.y().onTrue(new InstantCommand(() -> pivot.setMotor(3.3)));

        joystick.leftTrigger().onTrue(new ParallelCommandGroup(
            new InstantCommand(() -> wrist.setVertical()),
            new InstantCommand(() -> elevSub.setHeight(1)),
            new InstantCommand(() -> pivot.setMotor(13.1)),
            new InstantCommand(() -> coral.setMotor(-6))
        ))
        .onFalse(new ParallelCommandGroup(
            new InstantCommand(() -> pivot.setMotor(3.3)),
            new InstantCommand(() -> coral.setMotor(0))
        ));

        joystick.rightTrigger().onTrue(
            new SequentialCommandGroup(
            new InstantCommand(() -> pivot.setMotor(pivot.getKathuk())),
            new WaitCommand(0.2),
            new InstantCommand(() -> elevSub.setHeight(1))
            )
        );

        joystick.rightStick().onTrue(new InstantCommand(() -> coral.setMotor(4)));
        joystick.rightStick().onFalse(new InstantCommand(() -> coral.setMotor(0)));


        joystick.leftStick().onTrue(new InstantCommand(() -> wrist.setFlip()));

        joystick.b().onTrue(new ParallelCommandGroup(
            new InstantCommand(() -> pivot.setMotor(2.3)),
            new InstantCommand(() -> wrist.setHorizantal()),
            new InstantCommand(() -> elevSub.setHeight(6.9))
        )
        );

        joystick.x().onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> pivot.setMotor(2.5)),
                new InstantCommand(() -> wrist.setHorizantal()),
                new InstantCommand(() -> elevSub.setHeight(14.6))
            )
        );

        joystick.y().onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> wrist.setHorizantal()),
                new InstantCommand(() -> elevSub.setHeight(25.2)),
                new InstantCommand(() -> pivot.setMotor(3.6))
            )
        );

        joystick.povLeft().onTrue(new InstantCommand(() -> pivot.setMotor(3.3)));
        
        // drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
