# Robo-2-2026 — Swerve Practice Robot

A minimal FRC robot project (Java, WPILib 2026) used purely to practice and tune a **swerve drive** for the drive team. No other mechanisms — just chassis code.

## Hardware

- **Swerve drive & steering motors:** REV NEO (brushless), controlled by REV SPARK MAX (all 8 corner motors)
- **Bench-test motor:** CTRE Talon FX (standalone test rig, not part of the swerve modules)
- **IMU:** CTRE Pigeon 2

## Controls

One PS5 (DualSense) controller on port 0 (`CommandPS5Controller`).

## Getting Started

1. Open the project in VS Code with the WPILib extension.
2. Build: `./gradlew build`
3. Simulate: `./gradlew simulateJava`
4. Deploy to the RoboRIO: `./gradlew deploy`

## Driving the Swerve

Set the 8 motor CAN IDs and the gyro CAN ID in `SwerveConstants` ([Constants.java](src/main/java/frc/robot/Constants.java)), deploy, enable in **Teleop**:

- **Left stick** — translation (forward/back, strafe)
- **Right stick X** — rotation
- **Options** — zeroes the gyro heading (point the robot downfield and press this first — driving is field-relative)

There is no absolute encoder on the modules yet, so each steering SPARK MAX's relative position is zeroed on boot — **manually point every wheel straight forward before enabling the robot**, or the modules will drive at the wrong angle. Module angles/speeds and heading are published to SmartDashboard under `Swerve/`. Tune `kSteerP/I/D`, the gear ratios, and the track width/wheelbase in `SwerveConstants` to match your actual module hardware.

## Bench-Testing the Talon FX

This is a standalone test rig for a single Talon FX — not one of the swerve module motors (those are all SPARK MAX, see above).

Set the CAN ID in `TalonTestConstants.kTalonId` ([Constants.java](src/main/java/frc/robot/Constants.java)), deploy, then put the Driver Station into **Test mode**:

- Hold **Cross** — spins the Talon FX forward
- Hold **Circle** — spins the Talon FX in reverse

These bindings only trigger in Test mode (`RobotModeTriggers.test()`), so the motor cannot be driven accidentally during teleop/autonomous. Live velocity, position, temperature, and stator current are published to SmartDashboard under `TalonTest/`.

## Bench-Testing the Pigeon 2

Set the CAN ID in `PigeonTestConstants.kPigeonId` ([Constants.java](src/main/java/frc/robot/Constants.java)), deploy, then put the Driver Station into **Test mode**:

- Yaw, pitch, roll, and yaw angular velocity are always published to SmartDashboard under `PigeonTest/` — no button needed, just watch the values while you rotate/tilt the sensor by hand.
- Press **Square** — zeroes the yaw (only works in Test mode), so you can re-center it before checking that rotation reads correctly.

## Status

Swerve drivetrain (4 modules, field-relative teleop drive) plus individual Talon FX / Pigeon 2 bench tests are wired up. No absolute encoders yet — steering zeroes to whatever position the wheel is in at boot.
