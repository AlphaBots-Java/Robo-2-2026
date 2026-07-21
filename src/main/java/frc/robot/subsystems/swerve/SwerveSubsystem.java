// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.swerve;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.SwerveConstants;

/** Four-module swerve drivetrain: NEO/SPARK MAX drive, Talon FX steering, Pigeon 2 gyro. */
public class SwerveSubsystem extends SubsystemBase {
  private final SwerveModule m_frontLeft =
      new SwerveModule(SwerveConstants.kFrontLeftDriveId, SwerveConstants.kFrontLeftSteerId);
  private final SwerveModule m_frontRight =
      new SwerveModule(SwerveConstants.kFrontRightDriveId, SwerveConstants.kFrontRightSteerId);
  private final SwerveModule m_backLeft =
      new SwerveModule(SwerveConstants.kBackLeftDriveId, SwerveConstants.kBackLeftSteerId);
  private final SwerveModule m_backRight =
      new SwerveModule(SwerveConstants.kBackRightDriveId, SwerveConstants.kBackRightSteerId);

  private final Pigeon2 m_gyro = new Pigeon2(SwerveConstants.kGyroId, new CANBus(SwerveConstants.kCanBus));

  /**
   * Drives the chassis at the given speeds.
   *
   * @param xSpeed forward speed, m/s (+ forward)
   * @param ySpeed strafe speed, m/s (+ left)
   * @param rotSpeed rotation speed, rad/s (+ counterclockwise)
   * @param fieldRelative if true, speeds are relative to the field instead of the robot chassis
   */
  public void drive(double xSpeed, double ySpeed, double rotSpeed, boolean fieldRelative) {
    ChassisSpeeds speeds =
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotSpeed, getHeading())
            : new ChassisSpeeds(xSpeed, ySpeed, rotSpeed);

    SwerveModuleState[] states = SwerveConstants.kKinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveConstants.kMaxSpeedMetersPerSecond);

    m_frontLeft.setDesiredState(states[0]);
    m_frontRight.setDesiredState(states[1]);
    m_backLeft.setDesiredState(states[2]);
    m_backRight.setDesiredState(states[3]);
  }

  public void stop() {
    m_frontLeft.stop();
    m_frontRight.stop();
    m_backLeft.stop();
    m_backRight.stop();
  }

  public Rotation2d getHeading() {
    return m_gyro.getRotation2d();
  }

  /** Zeroes the gyro heading; call this with the robot pointed downfield before driving. */
  public Command zeroHeadingCommand() {
    return runOnce(m_gyro::reset);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Swerve/Heading (deg)", getHeading().getDegrees());

    SmartDashboard.putNumber("Swerve/Front Left Angle (deg)", m_frontLeft.getState().angle.getDegrees());
    SmartDashboard.putNumber("Swerve/Front Right Angle (deg)", m_frontRight.getState().angle.getDegrees());
    SmartDashboard.putNumber("Swerve/Back Left Angle (deg)", m_backLeft.getState().angle.getDegrees());
    SmartDashboard.putNumber("Swerve/Back Right Angle (deg)", m_backRight.getState().angle.getDegrees());

    SmartDashboard.putNumber(
        "Swerve/Front Left Speed (mps)", m_frontLeft.getState().speedMetersPerSecond);
    SmartDashboard.putNumber(
        "Swerve/Front Right Speed (mps)", m_frontRight.getState().speedMetersPerSecond);
    SmartDashboard.putNumber("Swerve/Back Left Speed (mps)", m_backLeft.getState().speedMetersPerSecond);
    SmartDashboard.putNumber(
        "Swerve/Back Right Speed (mps)", m_backRight.getState().speedMetersPerSecond);
  }
}
