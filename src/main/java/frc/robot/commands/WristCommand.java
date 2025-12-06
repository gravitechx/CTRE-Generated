package frc.robot.commands;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Wrist;


public class WristCommand extends Command{
    //makes the PID
    PIDController pid = new PIDController(Constants.WristConstants.wristKP, Constants.WristConstants.wristKI, 0);
    
    private final Wrist wrist;
    
    //The number of encoder rotations from 0 (Bottom of the wrist) to the top (24.8 for our wrist)
    private double level;


    public WristCommand(Wrist wrist, double level){
        this.wrist = wrist;
        //this.levelsup = levelsup;
        this.level = level;
        addRequirements(wrist);
             
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
        
            //level = levelsup.getAsDouble();
            motorSpeed = pid.calculate(wrist.getEncoderPosition(), level);
            // SmartDashboard.putNumber("WristPID speed", motorSpeed);
            // SmartDashboard.putNumber("Wrist Encoder", wrist.getEncoderPosition());
            if (wrist.getEncoderPosition() < level) {
            wrist.setMotor((Math.abs(motorSpeed) < Constants.WristConstants.maxWristSpeed) ? motorSpeed : Constants.WristConstants.maxWristSpeed);
            } else {
                wrist.setMotor((Math.abs(motorSpeed) < Constants.WristConstants.maxWristDownSpeed) ? motorSpeed : -Constants.WristConstants.maxWristDownSpeed);
            }
           
        //SmartDashboard.putNumber("Wrist PID Error", pid.getError());

        } 
    @Override
    public void end(boolean interrupted){
        
    }
    
    @Override
    public boolean isFinished(){
        //This does not need to be called. The command should not end until the next command is run, as we want the PID to continue.
        return false;
    }
}
