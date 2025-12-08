// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

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
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorCommandFun;
import frc.robot.commands.IntakeSourceCMD;
import frc.robot.commands.PositionArmCMD;
import frc.robot.commands.WristCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.Wrist;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(1.2).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

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
    private PivotSubsystem pivotSub = new PivotSubsystem();
    private ElevatorSubsystem elevSub = new ElevatorSubsystem();
    private Wrist wrist = new Wrist();
    private Elevator elevator = new Elevator(pivot);
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

        joystick.a().whileTrue(pivotSub.setAngle(Degrees.of(-20)));
        joystick.b().whileTrue(pivotSub.setAngle(Degrees.of(60)));
        // Schedule `set` when the Xbox controller's B button is pressed,
        // cancelling on release.
        joystick.x().whileTrue(pivotSub.set(0.3));
        joystick.y().whileTrue(pivotSub.set(-0.3));

        // Schedule `setHeight` when the Xbox controller's B button is pressed,
        // cancelling on release.
        joystick.povDown().whileTrue(elevSub.setHeight(Meters.of(0)));
        joystick.povRight().whileTrue(elevSub.setHeight(Meters.of(1.5)));
        // Schedule `set` when the Xbox controller's B button is pressed,
        // cancelling on release.
        joystick.povLeft().whileTrue(elevSub.set(0.3));
        joystick.povUp().whileTrue(elevSub.set(-0.3));

        // joystick.leftTrigger().and(isCoralMode).onTrue(new ParallelCommandGroup(
        //     new WristCommand(wrist, Constants.WristConstants.horizontalAngle),
        //     new ElevatorCommand(elevator, 1.5, -4, pivot),
        //     new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.groundIntakeAngle-0.8)),
        //     new InstantCommand(() -> Constants.armPOS = Constants.PivotConstants.groundIntakeAngle),
        //     new InstantCommand(() -> coral.setMotor(.6))
        // ));
        // joystick.leftTrigger().and(isCoralMode).onFalse(
        //     new ParallelCommandGroup(
        //         new ElevatorCommandFun(elevator, () -> elevator.kathunkVal()),
        //         new InstantCommand(() -> pivot.kathunk(Constants.elevatorLevel))
        //     )

        // );
        // joystick.leftTrigger().and(isCoralMode).onFalse(new SequentialCommandGroup(
        //     new WaitCommand(0.45), //go down, wait .5 seconds, go up
        //     new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.idleAngle)),
        //     // new ElevatorCommand(elevator, 0, -5, pivot),
        //     // new InstantCommand(() -> elevator.resetEncoder(0.5)),
        //     new WaitCommand(0.5)
        // )); 
        // joystick.leftTrigger().and(isCoralMode).onFalse(
        //     new SequentialCommandGroup(
        //     new WaitCommand(0.3),
        //     new InstantCommand(() -> coral.setMotor(0.3)),
        //     new WaitCommand(0.15),
        //     new InstantCommand(() -> coral.setMotor(0)) //stop spinning motor after .5 seconds
        // ));

        // joystick.leftTrigger().and(isAlgaeMode).onTrue(
        //     new ParallelCommandGroup(
        //             new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.groundIntakeAlgaeAngle-0.5)),
        //             new WristCommand(wrist, Constants.WristConstants.horizontalAngle),
        //             new ElevatorCommand(elevator, 0, 0, pivot),
        //             new InstantCommand(() -> coral.setMotor(0.5))
        //         )
        // );

        // joystick.leftTrigger().and(isAlgaeMode).onFalse(
        //     new ParallelCommandGroup(
        //             new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.idleAngle)),
        //             new WristCommand(wrist, Constants.WristConstants.horizontalAngle),
        //             new InstantCommand(() -> coral.setMotor(0.1))
        //         )
        // );

        // joystick.a().and(isCoralMode).onTrue(
        //     new PositionArmCMD(
        //         elevator, Constants.ElevatorConstants.L1Height, pivot, Constants.PivotConstants.L1Angle, wrist, Constants.WristConstants.horizontalAngle
        //     )
        // );
        // joystick.a().and(isAlgaeMode).onTrue(
        //     new PositionArmCMD(
        //         elevator, Constants.ElevatorConstants.L1Height, pivot, Constants.PivotConstants.processorAngle, wrist, Constants.WristConstants.horizontalAngle
        //     )
        // );
            

        // //L2 Coral Command
        // joystick.b().and(isCoralMode).onTrue(new ParallelCommandGroup(
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L2Height + 0.7, 2, pivot),
        //     new InstantCommand( () -> pivot.setMotor(Constants.PivotConstants.L2startAngle-.3)),
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle)
        // ));

        // //L2 Algae Command
        // joystick.b().and(isAlgaeMode).onTrue(new ParallelCommandGroup(
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L2AlgaeHeight, 2, pivot),
        //     new InstantCommand( () -> pivot.setMotor(Constants.PivotConstants.L2L3AlgaeAngle)),
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle),
        //     new InstantCommand(() -> coral.setMotor(0.5))
        // ));

        // joystick.b().and(isAlgaeMode).onFalse(
        //     new InstantCommand(() -> coral.setMotor(0.1))
        // );
  
        // //L3 Coral Command
        // joystick.x().and(isCoralMode).onTrue(new ParallelCommandGroup(
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L3Height +0.7, 3, pivot),
        //     new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.L3startAngle-.3)),
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle)
        // ));

        // //L3 Algae Command
        // joystick.x().and(isAlgaeMode).onTrue(new ParallelCommandGroup(
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L3AlgaeHeight-.25, 3, pivot),
        //     new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.L2L3AlgaeAngle)),
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle),
        //     new InstantCommand(() -> coral.setMotor(0.5))
        // ));

        // joystick.x().and(isAlgaeMode).onFalse(
        //     new InstantCommand(() -> coral.setMotor(0.1))
        // );


        // //L4 Command
        // joystick.y().and(isCoralMode).onTrue(
        //     new ParallelCommandGroup(
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle),
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L4Height + 0.4, 4, pivot),
        //     new InstantCommand(() -> pivot.setMotor(2.6+0.7)),
        //     new SequentialCommandGroup(
        //         new WaitCommand(1),
        //         new InstantCommand(() -> Constants.armPOS = 1.7)
        //     )));

        // joystick.y().and(isAlgaeMode).onTrue(
        //     new ParallelCommandGroup(
        //     new ElevatorCommand(elevator, Constants.ElevatorConstants.L4Height + 1.4, 0, pivot),
        //     new WristCommand(wrist, Constants.WristConstants.verticalAngle),
        //     new SequentialCommandGroup(
        //         new WaitCommand(2),
        //         new InstantCommand(() -> pivot.setMotor(Constants.PivotConstants.bargeStartAngle))
        //     )
        // ));

        // joystick.rightTrigger().onTrue(new ParallelCommandGroup(
        //     new ElevatorCommandFun(elevator, () -> elevator.kathunkVal()),
        //     new InstantCommand(() -> pivot.kathunk(Constants.elevatorLevel))
        // ));

        // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        // joystick.b().whileTrue(drivetrain.applyRequest(() ->
        //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        // ));
        // // Run SysId routines when holding back/start and X/Y.
        // // Note that each routine should be run exactly once in a single log.
        // joystick.leftTrigger().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        // joystick.leftTrigger().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        // joystick.rightTrigger().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        // joystick.rightTrigger().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // // reset the field-centric heading on left bumper press
        // joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        // drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
