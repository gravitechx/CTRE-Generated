package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Coral;

public class IntakeSourceCMD extends Command{
    private Timer intakeEndDelay = new Timer();
    private final double endCommandDelay = 0.1;//how long we keep moving motors for after motor speed change
    private Coral coral;
    private final double threshold = 30;
    private double motorSpeed;
    private double startTime;
    private double initialDelay = 2;


    public IntakeSourceCMD(Coral coralSub, double speed){
        this.coral = coralSub;
        this.motorSpeed = speed;
        addRequirements(coral);
    }
    //set motor to specified speed
    public void initialize(){
        this.coral.setMotor(this.motorSpeed);
        intakeEndDelay.reset();
        this.startTime = Timer.getFPGATimestamp();
        SmartDashboard.putBoolean("source command", true);
    }

    public void execute(){
        if(Timer.getFPGATimestamp() > this.startTime + initialDelay && Math.abs(this.coral.getEncoderRate()) <= threshold){
            intakeEndDelay.start();
            SmartDashboard.putNumber("command coral speed", this.coral.getEncoderRate());
            SmartDashboard.putNumber("command coral threshold", this.threshold);
        }
        
    }

    public void end(boolean interrupted){
        this.coral.setMotor(0);
        intakeEndDelay.stop();
        SmartDashboard.putNumber("Source Finished", this.coral.getEncoderRate());
        SmartDashboard.putBoolean("source command", false);

    }

    public boolean isFinished(){
        return this.intakeEndDelay.hasElapsed(endCommandDelay);
    }

}
