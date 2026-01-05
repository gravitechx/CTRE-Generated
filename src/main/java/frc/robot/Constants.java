package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public final class Constants {
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

        public static final Translation3d translationToRobot3 = new Translation3d(Units.inchesToMeters(13.720), Units.inchesToMeters(9.755794), Units.inchesToMeters(8.505)); //TODO fill this out later
        public static final Rotation3d rotationOffset3 = new Rotation3d(0, 20, 0);
    }
}