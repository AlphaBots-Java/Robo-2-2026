// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kJoystickDeadband = 0.1;
  }

  public static class TalonTestConstants {
    // Set this to the CAN ID of the Talon FX you want to test.
    public static final int kTalonId = 0;
    // CAN bus name the Talon FX is on ("rio" for the roboRIO bus, or your CANivore name).
    public static final String kCanBus = "rio";
  }

  public static class PigeonTestConstants {
    // Set this to the CAN ID of the Pigeon 2 you want to test.
    public static final int kPigeonId = 0;
    // CAN bus name the Pigeon 2 is on ("rio" for the roboRIO bus, or your CANivore name).
    public static final String kCanBus = "rio";
  }

  /** CAN IDs, physical dimensions, and tuning gains for the swerve drivetrain. */
  public static class SwerveConstants {
    // CAN bus the Pigeon 2 is on ("rio" for the roboRIO bus, or your CANivore name). The drive
    // and steer motors are SPARK MAX, which talk over the roboRIO's CAN bus directly.
    public static final String kCanBus = "rio";

    // --- CAN IDs: set these to match your robot's wiring ---
    public static final int kFrontLeftDriveId = 50;
    public static final int kFrontLeftSteerId = 51;

    public static final int kFrontRightDriveId = 52;
    public static final int kFrontRightSteerId = 53;

    public static final int kBackLeftDriveId = 54;
    public static final int kBackLeftSteerId = 55;

    public static final int kBackRightDriveId = 56;
    public static final int kBackRightSteerId = 57;

    public static final int kGyroId = 5;

    // --- Physical dimensions: measure these on your chassis ---
    // Distance between the left and right wheels.
    public static final double kTrackWidthMeters = 0.580;
    // Distance between the front and back wheels.
    public static final double kWheelBaseMeters = 0.530;
    public static final double kWheelDiameterMeters = Units.inchesToMeters(4);

    // --- Gear ratios: motor rotations per one output rotation. ---
    // SDS MK4i drive ratio depends on which level (L1/L2/L3) your modules are built with.
    // 6.75:1 below is the L2 ratio (the most common one) — change it if yours is L1 or L3.
    public static final double kDriveGearRatio = 6.75;
    // SDS MK4i steering ratio is 150/7:1 regardless of drive level.
    public static final double kSteerGearRatio = 150.0 / 7.0;

    public static final double kMaxSpeedMetersPerSecond = 3.5;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;

    // Steering closed-loop gains (software PIDController, output is a duty cycle in [-1, 1]).
    // Tune these on your robot.
    public static final double kSteerP = 4.0;
    public static final double kSteerI = 0.0;
    public static final double kSteerD = 0.0;

    public static final SwerveDriveKinematics kKinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBaseMeters / 2, kTrackWidthMeters / 2), // Front left
            new Translation2d(kWheelBaseMeters / 2, -kTrackWidthMeters / 2), // Front right
            new Translation2d(-kWheelBaseMeters / 2, kTrackWidthMeters / 2), // Back left
            new Translation2d(-kWheelBaseMeters / 2, -kTrackWidthMeters / 2)); // Back right
  }
}
