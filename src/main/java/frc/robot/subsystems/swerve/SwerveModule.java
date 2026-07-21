// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.swerve;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.robot.Constants.SwerveConstants;

/**
 * One swerve module: a NEO on a SPARK MAX for drive, a second NEO on a SPARK MAX for steering.
 *
 * <p>There is no absolute encoder wired up yet, so the steering SPARK MAX's internal relative
 * sensor is zeroed on startup. That only reads correctly if every wheel is manually pointed
 * straight forward before the robot is enabled.
 */
public class SwerveModule {
  private final SparkMax m_driveMotor;
  private final RelativeEncoder m_driveEncoder;

  private final SparkMax m_steerMotor;
  private final RelativeEncoder m_steerEncoder;
  
  private final PIDController m_steerPid =
      new PIDController(SwerveConstants.kSteerP, SwerveConstants.kSteerI, SwerveConstants.kSteerD);

  private static final double kMetersPerMotorRotation =
      (Math.PI * SwerveConstants.kWheelDiameterMeters) / SwerveConstants.kDriveGearRatio;

  public SwerveModule(int driveMotorId, int steerMotorId) {
    m_driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
    SparkMaxConfig driveConfig = new SparkMaxConfig();
    driveConfig.idleMode(IdleMode.kBrake);
    m_driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_driveEncoder = m_driveMotor.getEncoder();

    m_steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);
    SparkMaxConfig steerConfig = new SparkMaxConfig();
    steerConfig.idleMode(IdleMode.kBrake);
    m_steerMotor.configure(steerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_steerEncoder = m_steerMotor.getEncoder();
    m_steerEncoder.setPosition(0);

    m_steerPid.enableContinuousInput(-0.5, 0.5);
  }

  /** Current wheel speed (m/s) and angle, for kinematics/telemetry. */
  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocityMetersPerSecond(), getSteerAngle());
  }

  /** Current wheel distance traveled (m) and angle, for odometry. */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(getDriveDistanceMeters(), getSteerAngle());
  }

  /** Drives the module toward the desired speed and angle (steering optimized to the closer side). */
  public void setDesiredState(SwerveModuleState desiredState) {
    Rotation2d currentAngle = getSteerAngle();
    desiredState.optimize(currentAngle);

    m_driveMotor.set(desiredState.speedMetersPerSecond / SwerveConstants.kMaxSpeedMetersPerSecond);

    double steerOutput =
        m_steerPid.calculate(currentAngle.getRotations(), desiredState.angle.getRotations());
    m_steerMotor.set(MathUtil.clamp(steerOutput, -1, 1));
  }

  public void stop() {
    m_driveMotor.set(0);
    m_steerMotor.set(0);
  }

  private double getDriveDistanceMeters() {
    return m_driveEncoder.getPosition() * kMetersPerMotorRotation;
  }

  private double getDriveVelocityMetersPerSecond() {
    return (m_driveEncoder.getVelocity() / 60.0) * kMetersPerMotorRotation;
  }

  private Rotation2d getSteerAngle() {
    return Rotation2d.fromRotations(m_steerEncoder.getPosition() / SwerveConstants.kSteerGearRatio);
  }
}
