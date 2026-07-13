// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.TalonTestConstants;

/** Bench-test subsystem for a single Talon FX (steering motor). */
public class TalonTestSubsystem extends SubsystemBase {
  private static final double kTestOutput = 0.2;

  private final TalonFX m_talon =
      new TalonFX(TalonTestConstants.kTalonId, new CANBus(TalonTestConstants.kCanBus));
  private final DutyCycleOut m_dutyCycleRequest = new DutyCycleOut(0);

  public TalonTestSubsystem() {
    m_talon.setPosition(0);
  }

  private void set(double percentOutput) {
    m_talon.setControl(m_dutyCycleRequest.withOutput(percentOutput));
  }

  /** Spins the Talon FX forward while scheduled; stops when the command ends. */
  public Command runForwardCommand() {
    return startEnd(() -> set(kTestOutput), () -> set(0));
  }

  /** Spins the Talon FX in reverse while scheduled; stops when the command ends. */
  public Command runReverseCommand() {
    return startEnd(() -> set(-kTestOutput), () -> set(0));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("TalonTest/Velocity (rps)", m_talon.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("TalonTest/Position (rot)", m_talon.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("TalonTest/Temperature (C)", m_talon.getDeviceTemp().getValueAsDouble());
    SmartDashboard.putNumber(
        "TalonTest/Stator Current (A)", m_talon.getStatorCurrent().getValueAsDouble());
  }
}
