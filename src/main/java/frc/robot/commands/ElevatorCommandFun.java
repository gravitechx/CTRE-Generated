// package frc.robot.commands;

// import java.util.function.DoubleSupplier;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants;
// import frc.robot.subsystems.Elevator;


// public class ElevatorCommandFun extends Command{
//     //makes the PID
//     //THIS SHOULD NOT BE USED DURING AN ACTUAL COMP. For stress testing/fun purposes only.
//     PIDController pid = new PIDController(Constants.ElevatorConstants.elevatorKp, Constants.ElevatorConstants.elevatorKi, 0);
   
//     private final Elevator elevator;
//     //The double level is replaced with levelsup, a double supplier method which is called every scheduler run to get a continuous execute method for the level.
//     private DoubleSupplier levelsup;


//     public ElevatorCommandFun(Elevator elevator, DoubleSupplier levelsup){
        
//         this.elevator = elevator;
//         this.levelsup = levelsup;
//         addRequirements(elevator);
//         Constants.desiredHeight = levelsup.getAsDouble();
        
//     }

//     @Override
//     public void initialize(){
       
//     }

//     // uses PID to set motor speed until it reaches level
//     @Override
//     public void execute(){ //execute is what happens after the robot is initialized and before it ends
//         //the PID calculates how much the motor needs to run to go to a certain position
//         //(the current position is determined using the encoder) and runs it
//         double motorSpeed;
            
//         double level = levelsup.getAsDouble();
//         if (level > 24.8) {
//             level = 24.8;
//         }
//         Constants.desiredHeight = level;

        
//         motorSpeed = pid.calculate(elevator.getEncoderPosition(), level);
//         SmartDashboard.putNumber("Positive speed", motorSpeed);
//         SmartDashboard.putNumber("Encoder", elevator.getEncoderPosition());
//         if (elevator.getEncoderPosition() < level) {
//             elevator.setMotor((Math.abs(motorSpeed) < Constants.ElevatorConstants.maxElevatorSpeed) ? motorSpeed : Constants.ElevatorConstants.maxElevatorSpeed);
//         } else {
//             elevator.setMotor((Math.abs(motorSpeed) < Constants.ElevatorConstants.maxElevatorDownSpeed) ? motorSpeed : -Constants.ElevatorConstants.maxElevatorDownSpeed);
//         }
           
//         SmartDashboard.putNumber("Error", pid.getError());

//     }
//     @Override
//     public void end(boolean interrupted){
        
//     }
    
//     @Override
//     public boolean isFinished(){
        
//         return false;
//     }
// }
