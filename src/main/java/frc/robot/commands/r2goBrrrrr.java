// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.*;
 


public class r2goBrrrrr extends SequentialCommandGroup {

    DriveSubsystem m_subsystem;
    Trajectory trajectory;

    // Use addRequirements() here to declare subsystem dependencies.
    DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(Constants.Drive.ksVolts, Constants.Drive.kvVoltsSecondsPerMeter, Constants.Drive.kaVoltSecondsSquarePerMeter),
      Constants.Drive.kDriveKinematics, 10);
    TrajectoryConfig config =
    new TrajectoryConfig(Constants.Drive.kMaxSpeedMetersPerSecond, Constants.Drive.kMaxAccelerationMetersPerSecondSquared)
    .setKinematics(Constants.Drive.kDriveKinematics).addConstraint(autoVoltageConstraint);

    RamseteCommand ramseteCommand;
  public boolean isFinished() {
    return ramseteCommand.isFinished();
  }

  public r2goBrrrrr(DriveSubsystem m_subsystem, Trajectory trajectory) {
    this.m_subsystem = m_subsystem;
    // addRequirements(m_subsystem);

    this.ramseteCommand = new RamseteCommand(
      trajectory, 
      m_subsystem::getPose, 
      new RamseteController(Constants.Drive.kRamseteB, Constants.Drive.kRamseteZeta) ,
      Constants.Drive.kDriveKinematics,
      m_subsystem::setWheelVelocity, 
      m_subsystem
    );
    this.m_subsystem.resetOdometry(trajectory.getInitialPose());
    this.m_subsystem.brakeOn();
    this.trajectory = trajectory;

    addCommands(
      ramseteCommand.andThen(() -> m_subsystem.tankDriveVolts(0.0, 0.0))
    );
    System.out.println("Starting Automomous Path!");
  }
}