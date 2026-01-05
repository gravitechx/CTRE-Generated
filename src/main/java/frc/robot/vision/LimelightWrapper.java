package frc.robot.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.robot.Constants.LimelightConstants.*;

public class LimelightWrapper {
    private String limelightName;
    private final LimelightHelpers.PoseEstimate limelightPoseEstimate = new LimelightHelpers.PoseEstimate();
    public LimelightWrapper(String limelightName){
        this.limelightName = limelightName;
        LimelightHelpers.setCameraPose_RobotSpace(
            limelightName, 
            getTranslationToBot().getX(), 
            getTranslationToBot().getY(), 
            getTranslationToBot().getZ(), 
            getRotationOffset().getX(), 
            getRotationOffset().getY(), 
            getRotationOffset().getZ()
        );
        Pose3d limelightPositionOffset = LimelightHelpers.getCameraPose3d_RobotSpace(limelightName);
        SmartDashboard.putNumber("limelight pos X offset", limelightPositionOffset.getX());
        SmartDashboard.putNumber("limelight pos Y offset", limelightPositionOffset.getY());
        SmartDashboard.putNumber("limelight pos Z offset", limelightPositionOffset.getZ());
        SmartDashboard.putNumber("limelight pos angle offset", limelightPositionOffset.getRotation().getX());
    }

    public Translation3d getTranslationToBot(){
        if(limelightName.equals("limelight-one")){
            return translationToRobot;
        } else if(limelightName.equals("limelight-greg")){
            return translationToRobot2;
        } else if(limelightName.equals("limelight-object")){
            return translationToRobot3;
        } else{return translationToRobot;}
    }

    public Rotation3d getRotationOffset(){
        if(limelightName.equals("limelight-front")){
            return rotationOffset;
        } else if(limelightName.equals("limelight-back")){
            return rotationOffset2;
        } else if(limelightName.equals("limelight-object")){
            return rotationOffset3;
        } else{return rotationOffset;}
    }

    public Transform2d getNearestTagWith3DOffset(){
        double[] offsetData = LimelightHelpers.getTargetPose_RobotSpace(limelightName);
        if(offsetData.length > 0){

            double xOffset = offsetData[2];
            double yOffset = offsetData[0];
            double rotationOffset = offsetData[4];
            return new Transform2d(
                xOffset,
                yOffset,
                Rotation2d.fromDegrees(rotationOffset)
            );
        }
        return Transform2d.kZero;
    }

    public LimelightHelpers.PoseEstimate getBotPoseEstimate(){
        LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(limelightName);
        if(LimelightHelpers.getFiducialID(limelightName)>0){
            return mt1;
        }
        return limelightPoseEstimate;
    }

    public Transform2d getTransformToBranch(boolean isLeft){
        //sets 3d offset based on branch we're trying to align to then grabs nearest tag
        double xValue = tagToBranchOffset.getX();
        if(isLeft){
            xValue = -xValue;
        }
        LimelightHelpers.SetFidcuial3DOffset(limelightName, xValue, tagToBranchOffset.getY(), tagToBranchOffset.getZ());
        return getNearestTagWith3DOffset();
    }
    
    public void logTargetToRobot(){
        double[] values = LimelightHelpers.getTargetPose_RobotSpace(limelightName);
        if(values.length > 0){
            SmartDashboard.putNumber("TargetToRobotX", values[0]);
            SmartDashboard.putNumber("TargetToRobotY", values[2]);
            SmartDashboard.putNumber("TargetToRobotAngle", values[4]);
        }
    }

    public void resetGyro(double newAngle){
        LimelightHelpers.SetRobotOrientation(limelightName, newAngle, 0, 0, 0, 0, 0);
        LimelightHelpers.SetIMUMode(limelightName, 1);
        LimelightHelpers.SetIMUMode(limelightName, 0);
    }

    public void setPipeline(int newPipeline){
        LimelightHelpers.setPipelineIndex(limelightName, newPipeline);
    }

    public LimelightHelpers.RawDetection[] getDetections(){
        return LimelightHelpers.getRawDetections(limelightName);
    }

    public void setLights(){
        LimelightHelpers.setLEDMode_ForceOn(limelightName);
    }
}
