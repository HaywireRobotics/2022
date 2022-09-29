// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) // idk why this exists...
  private final DriveSubsystem m_subsystem;
  private final Joystick leftJoystick;
  private final Joystick rightJoystick;

  /**
   * Creates a new DriveCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveCommand(DriveSubsystem subsystem, Joystick _leftJoystick, Joystick _rightJoystick) {
    m_subsystem = subsystem;
    leftJoystick = _leftJoystick;
    rightJoystick = _rightJoystick;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPower = 0.0D;
    double rightPower = 0.0D;
    
    double leftJoystickVal = this.leftJoystick.getY();
    double rightJoystickVal = this.rightJoystick.getY();

    if (leftJoystickVal > 0.15D) {
      leftPower = (leftJoystickVal - 0.15) / 1.0;
    } else if (leftJoystickVal < -0.15D) {
      leftPower = (leftJoystickVal + 0.15) / 1.0;
    }

    if (rightJoystickVal > 0.15D) {
       rightPower = (rightJoystickVal - 0.15) / 1.0;
    } else if (rightJoystickVal < -0.15D) {
       rightPower = (rightJoystickVal + 0.15) / 1.0;
    }

    m_subsystem.tankDrive(leftPower, rightPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.m_subsystem.tankDrive(0.0D, 0.0D);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
