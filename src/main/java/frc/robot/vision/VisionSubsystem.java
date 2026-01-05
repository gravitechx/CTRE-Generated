package frc.robot.vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class VisionSubsystem extends SubsystemBase{
    private String llName1;
    private String llName2;
    // private String llName3;

    private LimelightWrapper ll1;
    private LimelightWrapper ll2;
    // private LimelightWrapper ll3;
    private LimelightHelpers.PoseEstimate poseSnapshot;
    private LimelightHelpers.PoseEstimate poseSnapshot2;

    private SwerveDrivePoseEstimator limelightPoseEstimate = null;

    boolean doRejectUpdate = false;
    boolean doRejectUpdate2 = false;


    private Field2d llfield = new Field2d();


    public VisionSubsystem(String llName1, String llName2){ //, String llName3
        this.llName1 = llName1;
        this.llName2 = llName2;
        // this.llName3 = llName3;

        ll1 = new LimelightWrapper(llName1);
        ll2 = new LimelightWrapper(llName2);
        // ll3 = new LimelightWrapper(llName3);

        ll1.setPipeline(0);
        ll2.setPipeline(0);
        // ll1.setLights();
        // ll3.setPipeline(1);
    }

    public void periodic(){
        doRejectUpdate = false;
        doRejectUpdate2 = false;
        poseSnapshot = ll1.getBotPoseEstimate();
        LimelightHelpers.PoseEstimate goodPose = null;
        poseSnapshot2 = ll1.getBotPoseEstimate();
        LimelightHelpers.PoseEstimate goodPose2 = null;
        if(poseSnapshot.rawFiducials.length > 0)
        {
            if(poseSnapshot.tagCount == 1 && poseSnapshot.rawFiducials.length == 1)
            {
                if(poseSnapshot.rawFiducials[0].ambiguity > .7)
                {
                doRejectUpdate = true;
                }
                if(poseSnapshot.rawFiducials[0].distToCamera > 3)
                {
                doRejectUpdate = true;
                }
            }
            if(poseSnapshot.tagCount == 0)
            {
                doRejectUpdate = true;
            }

            if(!doRejectUpdate)
            {
                goodPose = poseSnapshot;
                limelightPoseEstimate.addVisionMeasurement(goodPose.pose, goodPose.timestampSeconds);
            }
        }
        if(poseSnapshot2.rawFiducials.length > 0)
        {
            if(poseSnapshot2.tagCount == 1 && poseSnapshot2.rawFiducials.length == 1)
            {
                if(poseSnapshot2.rawFiducials[0].ambiguity > .7)
                {
                doRejectUpdate2 = true;
                }
                if(poseSnapshot2.rawFiducials[0].distToCamera > 3)
                {
                doRejectUpdate2 = true;
                }
            }
            if(poseSnapshot2.tagCount == 0)
            {
                doRejectUpdate2 = true;
            }

            if(!doRejectUpdate2)
            {
                goodPose2 = poseSnapshot2;
                limelightPoseEstimate.addVisionMeasurement(goodPose2.pose, goodPose2.timestampSeconds);
            }
        }

        if(goodPose==null && goodPose==null){
            limelightPoseEstimate = null;
        }

        if(limelightPoseEstimate!=null){
            llfield.setRobotPose(limelightPoseEstimate.getEstimatedPosition());
            SmartDashboard.putData("ll pos", llfield);
        }
    }

    public SwerveDrivePoseEstimator getLLPose(){
        return limelightPoseEstimate;
    }

}
