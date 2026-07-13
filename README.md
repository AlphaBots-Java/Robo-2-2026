# Robo-2-2026 — Swerve Practice Robot

A minimal FRC robot project (Java, WPILib 2026) used purely to practice and tune a **swerve drive** for the drive team. No other mechanisms — just chassis code.

## Hardware

- **Drive motors:** REV NEO (brushless), controlled by REV SPARK MAX / SPARK Flex
- **Steering (angle) motors:** CTRE Talon FX

## Getting Started

1. Open the project in VS Code with the WPILib extension.
2. Build: `./gradlew build`
3. Simulate: `./gradlew simulateJava`
4. Deploy to the RoboRIO: `./gradlew deploy`

## Status

Base project currently reflects the default WPILib command-based template. Swerve subsystem, module code, and drive commands are in progress.
