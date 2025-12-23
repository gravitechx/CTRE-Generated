// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import frc.robot.subsystems.Elevator;
// import frc.robot.subsystems.Pivot;
// import frc.robot.subsystems.Wrist;

// public class PositionArmCMD extends ParallelCommandGroup{
//     public PositionArmCMD(
//         Elevator elevator, double elevatorSetpoint,
//         Pivot pivot, double pivotSetpoint,
//         Wrist wrist, double wristSetpoint
//     ){
//         int idekBro = -2; // what is this :(
//         addCommands(
//             new ElevatorCommand(elevator, elevatorSetpoint, idekBro, pivot),
//             new InstantCommand( () -> pivot.setMotor(pivotSetpoint)),
//             new WristCommand(wrist, wristSetpoint)
//         );
//     }
// }
