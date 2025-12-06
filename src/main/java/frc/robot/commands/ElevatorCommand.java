package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;


public class ElevatorCommand extends Command{
    //makes the PID
    PIDController pid = new PIDController(Constants.ElevatorConstants.elevatorKP, Constants.ElevatorConstants.elevatorKI, 0);
    
    private final Elevator elevator;
    private final Pivot pivot;
    
    //The number of encoder rotations from 0 (Bottom of the elevator) to the top (24.8 for our elevator)
    private double level;
    public int elevatorLevel;


    public ElevatorCommand(Elevator elevator, double level, int elLevel, Pivot pivot){
        this.elevator = elevator;
        if (level > 25.5) {
            level = 25.5;
        }
        this.pivot = pivot;
        this.level = level;
        addRequirements(elevator);
        Constants.elevatorLevel = elLevel;
        this.elevatorLevel = elLevel;
        Constants.desiredHeight = level;
        // SmartDashboard.putNumber("Elevator desired value", level);
        
    }
    @Override
    public void initialize(){
        
    }

    // uses PID to set motor speed until it reaches level. Keeps it at level after that
    @Override
    public void execute(){ //execute is what happens after the robot is initialized and before it ends
        //the PID calculates how much the motor needs to run to go to a certain position
        //(the current position is determined using the encoder) and runs it
        double motorSpeed;
        Constants.elevatorLevel = this.elevatorLevel;
        Constants.desiredHeight = level;
        SmartDashboard.putNumber("Elevator desired value", Constants.desiredHeight);

        SmartDashboard.putNumber("Elevator Level", Constants.elevatorLevel);

        motorSpeed = pid.calculate(elevator.getEncoderPosition(), level);
        // SmartDashboard.putNumber("ElevatorPID speed", motorSpeed);
        // SmartDashboard.putNumber("Elevator Encoder", elevator.getEncoderPosition());
        if (!(elevator.getEncoderPosition() > 4.5 && elevator.getEncoderPosition() < 8 && pivot.getEncoderPosition() < 2)) {
            if (elevator.getEncoderPosition() < level) {
                elevator.setMotor((Math.abs(motorSpeed) < Constants.ElevatorConstants.maxElevatorSpeed) ? motorSpeed : Constants.ElevatorConstants.maxElevatorSpeed);
            } else {
                elevator.setMotor((Math.abs(motorSpeed) < Constants.ElevatorConstants.maxElevatorDownSpeed) ? motorSpeed : -Constants.ElevatorConstants.maxElevatorDownSpeed);
            } 
        } else if (elevator.getEncoderPosition() < 8 ) {
            elevator.setMotor(0);
        } else {
            elevator.setMotor(0.3);
        }  
        //SmartDashboard.putNumber("Elevator PID Error", pid.getError());
    }
    @Override
    public void end(boolean interrupted){
        
    }

    public boolean atSetpoint(){
        return false;
    }
    
    @Override
    public boolean isFinished(){
        //This does not need to be called. The command should not end until the next command is run, as we want the PID to continue.
        return false;
    }
}
