// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.PigeonTestSubsystem;
import frc.robot.subsystems.TalonTestSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  private final TalonTestSubsystem m_talonTestSubsystem = new TalonTestSubsystem();
  private final PigeonTestSubsystem m_pigeonTestSubsystem = new PigeonTestSubsystem();

  // Single driver controller (PS5 / DualSense).
  private final CommandPS5Controller m_driverController =
      new CommandPS5Controller(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings.
   *
   * <p>Bench-test bindings are gated behind {@link RobotModeTriggers#test()} so they can only run
   * while the Driver Station is in Test mode, matching how WPILib intends bench testing to be
   * done.
   */
  private void configureBindings() {
    // Hold Cross to spin the test Talon FX forward, Circle to spin it in reverse.
    m_driverController
        .cross()
        .and(RobotModeTriggers.test())
        .whileTrue(m_talonTestSubsystem.runForwardCommand());

    m_driverController
        .circle()
        .and(RobotModeTriggers.test())
        .whileTrue(m_talonTestSubsystem.runReverseCommand());

    // Press Square to zero the Pigeon 2's yaw. Yaw/pitch/roll are always published to
    // SmartDashboard by PigeonTestSubsystem.periodic(), so just rotate the sensor by hand
    // afterward and watch the values change.
    m_driverController
        .square()
        .and(RobotModeTriggers.test())
        .onTrue(m_pigeonTestSubsystem.zeroYawCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
