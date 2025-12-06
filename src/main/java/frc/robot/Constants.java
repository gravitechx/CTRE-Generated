package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public final class Constants {
    public static double armPOS = 0;
    public static int elevatorLevel = -3;
    public static double desiredHeight = 0;


    public static final double periodicSpeed = Robot.kDefaultPeriod; // how quickly periodic methods are called. (millis)

    public static final class LimelightConstants{
        /* x offset to branch can be found at https://firstfrc.blob.core.windows.net/frc2025/FieldAssets/2025FieldDrawings.pdf (pg. 185 )
            Source measurment: 6.38 (inches)
            Hand measurment: 7.5 inches
         */
        public static final Translation3d translationToRobot = new Translation3d(Units.inchesToMeters(10.48), Units.inchesToMeters(0.582), Units.inchesToMeters(4.691)); //TODO fill this out later
        public static final Rotation3d rotationOffset = new Rotation3d(180, 20, 0);
        public static final Translation3d tagToBranchOffset =  new Translation3d();

        public static final Translation3d translationToRobot2 = new Translation3d(Units.inchesToMeters(13.720), Units.inchesToMeters(9.755794), Units.inchesToMeters(8.505)); //TODO fill this out later
        public static final Rotation3d rotationOffset2 = new Rotation3d(0, 20, 0);
    }
   
    public static final class PivotConstants {
        public static final double pivotKP = 0.5; //proportional
        public static final double pivotKI = 0; //integral
        public static final double pivotKD = 0; //derivative
        public static final double PIDerrorTolerance = 0.05;//pid controller tolerance
        public static final double maxPivotSpeed = 0.2;
        public static final double maxPivotDownSpeed = maxPivotSpeed*.03;
        public static final double verticalIntakeAngle = 12;


        public static final double sourceAngle = 3.3;
        public static final double processorAngle = 6.2;
        public static final double L1Angle = 4.8;
        public static final double L2L3AlgaeAngle = 3.9;
        public static final double L2startAngle = 4;
        public static final double L3startAngle = 3.7;
        public static final double L4Angle = 3.35;
        public static final double idleAngle = 3.8;
        public static final double climbAngle = 3;

        public static final double groundIntakeAngle = 13.7;
        public static final double groundIntakeAlgaeAngle = 9.93;

        public static final double bargeStartAngle = -2.35;
        public static final double bargeEndAngle = 5;

    }

    public static final class ElevatorConstants {
        public static final SparkMax ElevatorMotorPort1 = new SparkMax(4, MotorType.kBrushless);
        public static final SparkMax ElevatorMotorPort2 = new SparkMax(5, MotorType.kBrushless); //TODO change this
        public static final double elevatorKP = 0.15; //proportional
        public static final double elevatorKI = 0; //integral
        public static final double elevatorKD = 0.8; //derivative
        public static final double PIDerrorTolerance = 0.2;//pid controller tolerance
        public static final double maxElevatorSpeed = 0.45;
        public static final double maxElevatorDownSpeed = maxElevatorSpeed*.4
        ;
        
        //Heights for the elevator
        //TODO: These need to be tuned to the correct heights. Also the order may be wrong
        public static final double L1Height = 0.5;
        public static final double kathunk = 1.5;
        public static final double sourceHeight = 7.79; 
        public static final double algaeLowHeight = 16.2;
        public static final double algaeLowHeightEnd = 12;
        public static final double L2AlgaeHeight = 5.85;
        public static final double L2Height = 7.838;
        public static final double L2End = L2Height-kathunk;
        public static final double algaeHighHeight = 23.8;
        public static final double algaeHighHeightEnd = 10.8;
        public static final double L3AlgaeHeight = 13.35;
        public static final double L3Height = 114.725;
        public static final double L4Height = 25.750;
        public static final double groundIntakeHeight = 0.5;    
    }

    public static final class CoralConstants {
        public static final double intakespeed = 0.3;
        public static final double outtakespeed = -0.35;
    }
}