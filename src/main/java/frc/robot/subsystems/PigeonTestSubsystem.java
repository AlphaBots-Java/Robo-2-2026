// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.PigeonTestConstants;

/** Bench-test subsystem for a single Pigeon 2 IMU. */
public class PigeonTestSubsystem extends SubsystemBase {
  private final Pigeon2 m_pigeon =
      new Pigeon2(PigeonTestConstants.kPigeonId, new CANBus(PigeonTestConstants.kCanBus));

  /** Zeroes the yaw. Rotate/tilt the Pigeon by hand afterward and watch the values update. */
  public Command zeroYawCommand() {
    return runOnce(() -> m_pigeon.setYaw(0));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("PigeonTest/Yaw (deg)", m_pigeon.getYaw().getValueAsDouble());
    SmartDashboard.putNumber("PigeonTest/Pitch (deg)", m_pigeon.getPitch().getValueAsDouble());
    SmartDashboard.putNumber("PigeonTest/Roll (deg)", m_pigeon.getRoll().getValueAsDouble());
    SmartDashboard.putNumber(
        "PigeonTest/Angular Velocity Z (deg per s)",
        m_pigeon.getAngularVelocityZWorld().getValueAsDouble());
  }
}
