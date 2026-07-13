# Robo-2-2026 — Swerve Practice Robot

A minimal FRC robot project (Java, WPILib 2026) used purely to practice and tune a **swerve drive** for the drive team. No other mechanisms — just chassis code.

## Hardware

- **Drive motors:** REV NEO (brushless), controlled by REV SPARK MAX / SPARK Flex
- **Steering (angle) motors:** CTRE Talon FX
- **IMU:** CTRE Pigeon 2

## Controls

One PS5 (DualSense) controller on port 0 (`CommandPS5Controller`).

## Getting Started

1. Open the project in VS Code with the WPILib extension.
2. Build: `./gradlew build`
3. Simulate: `./gradlew simulateJava`
4. Deploy to the RoboRIO: `./gradlew deploy`

## Bench-Testing the Talon FX

Set the CAN ID in `TalonTestConstants.kTalonId` ([Constants.java](src/main/java/frc/robot/Constants.java)), deploy, then put the Driver Station into **Test mode**:

- Hold **Cross** — spins the Talon FX forward
- Hold **Circle** — spins the Talon FX in reverse

These bindings only trigger in Test mode (`RobotModeTriggers.test()`), so the motor cannot be driven accidentally during teleop/autonomous. Live velocity, position, temperature, and stator current are published to SmartDashboard under `TalonTest/`.

## Bench-Testing the Pigeon 2

Set the CAN ID in `PigeonTestConstants.kPigeonId` ([Constants.java](src/main/java/frc/robot/Constants.java)), deploy, then put the Driver Station into **Test mode**:

- Yaw, pitch, roll, and yaw angular velocity are always published to SmartDashboard under `PigeonTest/` — no button needed, just watch the values while you rotate/tilt the sensor by hand.
- Press **Square** — zeroes the yaw (only works in Test mode), so you can re-center it before checking that rotation reads correctly.

## Status

Talon FX bench test is wired up. Swerve subsystem, module code, and drive commands are in progress.
