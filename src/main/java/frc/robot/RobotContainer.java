// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto1Command;
import frc.robot.commands.Auto2Command;
import frc.robot.commands.Auto3Command;
import frc.robot.commands.BabyHooksCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.ReadyToShootCommand;
import frc.robot.commands.IndexCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.r2SetOdometry;
import frc.robot.commands.r2goBrrrrr;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
//import frc.robot.Constants;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem;
  private final DriveCommand m_autoCommand;
  private SendableChooser<Integer> autoCommandChooser = new SendableChooser<Integer>();
  private List<Trajectory> paths = new ArrayList<Trajectory>();
  private final ShooterSubsystem m_shooterSubsystem;
  private final IntakeSubsystem m_intakeSubsystem;
  private final IndexSubsystem m_indexSubsystem;
  private final ClimbSubsystem m_climbSubsystem;

  public static final Joystick driverRightStick = new Joystick(1);
  public static final Joystick driverLeftStick = new Joystick(0);
  public static final Joystick manipulatorRightStick = new Joystick(3);
  public static final Joystick manipulatorLeftStick = new Joystick(2);
  public static final JoystickButton manipulatorButton = new JoystickButton(manipulatorRightStick, 10);

  public double testShooterSpeed;

  public <Sendable> RobotContainer() {
    System.out.println("Starting Robot Container Construction");

    this.m_driveSubsystem = new DriveSubsystem();
    this.m_autoCommand = new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick);
    this.m_driveSubsystem.setDefaultCommand(new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick));
    
    this.m_shooterSubsystem = new ShooterSubsystem();
    this.m_intakeSubsystem = new IntakeSubsystem();
    this.m_indexSubsystem = new IndexSubsystem();
    this.m_indexSubsystem.setDefaultCommand(new IndexCommand(this.m_indexSubsystem, manipulatorRightStick));
    this.m_climbSubsystem = new ClimbSubsystem();
    this.m_climbSubsystem.setDefaultCommand(new ClimbCommand(this.m_climbSubsystem, manipulatorLeftStick, manipulatorButton));

    this.testShooterSpeed = 0;
    SmartDashboard.putNumber("TestShooterSpeed", this.testShooterSpeed);

    configureButtonBindings();

    this.autoCommandChooser.setDefaultOption("Auto2", 0);
    this.autoCommandChooser.addOption("DO NOT USE   Auto3", 1);
    this.autoCommandChooser.addOption("Auto 1", 2);
    String[] files = {"auto1.wpilib.json", "Test.wpilib.json", "Test.wpilib.json"};
    for (int i = 0; i < files.length; i++) {
      String trajectoryJSON = "paths/output/" + files[i];
      Trajectory trajectory = new Trajectory();
      try {
        System.out.println("Getting " + files[i]);
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
        trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
      } catch (IOException ex) {
        System.out.println("Unable to load file" + files[i]);
      }  
      List<Trajectory.State> states = trajectory.getStates();
      Trajectory.State lastState = states.get(states.size()-1);
      lastState.accelerationMetersPerSecondSq = 0.0;
      lastState.curvatureRadPerMeter = 0.0;
      lastState.velocityMetersPerSecond = 0.0;
      this.paths.add(trajectory);
    }
    SmartDashboard.putData("Auto mode", this.autoCommandChooser);

    System.out.println("End of Robot Container Constructor");
  }

  public Command getAutonomousCommand() {
    int trajectoryIdx = autoCommandChooser.getSelected();

    Trajectory trajectory = paths.get(trajectoryIdx);
    
    if (trajectoryIdx == 0) {
      return new Auto2Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, trajectory);
    } else if (trajectoryIdx == 1) {
      return new Auto3Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, trajectory);
    } else if (trajectoryIdx == 2) {
      return new Auto1Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, trajectory);
    } else {
      return new r2goBrrrrr(m_driveSubsystem, trajectory);
    }
  }

  private void configureButtonBindings() {
    new JoystickButton(driverLeftStick, 1).whenPressed(new InstantCommand(m_driveSubsystem::invertDrivetrain, m_driveSubsystem));
    new JoystickButton(driverRightStick, 1).whileHeld(new IntakeCommand(0.6D, m_intakeSubsystem));
    new JoystickButton(manipulatorRightStick, 2).whileHeld(new IntakeCommand(0.6D, m_intakeSubsystem));
    new JoystickButton(driverRightStick, 3).whileHeld(new IntakeCommand(-0.6D, m_intakeSubsystem));
    new JoystickButton(manipulatorRightStick, 3).whileHeld(new IntakeCommand(-0.6D, m_intakeSubsystem));

    new JoystickButton(driverLeftStick, 6).whenPressed(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    new JoystickButton(driverLeftStick, 3).whileHeld(new MoveArmCommand(0.22D, m_intakeSubsystem));
    new JoystickButton(manipulatorLeftStick, 3).whileHeld(new MoveArmCommand(0.22D, m_intakeSubsystem));
    new JoystickButton(driverLeftStick, 2).whileHeld(new MoveArmCommand(-0.22D, m_intakeSubsystem));
    new JoystickButton(manipulatorLeftStick, 2).whileHeld(new MoveArmCommand(-0.22D, m_intakeSubsystem));

    new JoystickButton(manipulatorRightStick, 8).whileHeld(new BabyHooksCommand(-0.16, m_climbSubsystem));
    new JoystickButton(manipulatorRightStick, 9).whileHeld(new BabyHooksCommand(0.16, m_climbSubsystem));

    new JoystickButton(manipulatorLeftStick, 4).whileHeld(new ShootCommand(2350, false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 5).whileHeld(new ShootCommand(3500, false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick,4).whileHeld(new ShootCommand(4200, false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick,5).whileHeld(new ShootCommand(5000, false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 1).whileHeld(new ShootCommand(-0.1D, true, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 6).whileHeld(new ShootCommand(this.testShooterSpeed, false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 1).whileHeld(new ReadyToShootCommand(this.m_shooterSubsystem));
  
    new JoystickButton(manipulatorLeftStick,   7).whileHeld(new ShootCommand(2350, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick,  10).whileHeld(new ShootCommand(3500, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick,  7).whileHeld(new ShootCommand(4200, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 10).whileHeld(new ShootCommand(5000, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    
  }
}
