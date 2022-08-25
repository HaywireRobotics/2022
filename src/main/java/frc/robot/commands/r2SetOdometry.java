// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveSubsystem;

public class r2SetOdometry extends CommandBase {
  Trajectory trajectory;
  frc.robot.subsystems.DriveSubsystem hyperdrive;
  /** Creates a new r2SetOdometry. */
  public r2SetOdometry(DriveSubsystem hyperdrive, Trajectory trajectory) {
    this.trajectory = trajectory;
    this.hyperdrive = hyperdrive;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hyperdrive.flipOdometry(trajectory.getInitialPose());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}