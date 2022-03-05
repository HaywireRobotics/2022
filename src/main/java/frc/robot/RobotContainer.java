// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RunIndexCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
// import frc.robot.subsystems.ShootAngleSubsystem;
// import frc.robot.commands.AdjustShootAngle;
import frc.robot.commands.ShootCommand;
import edu.wpi.first.wpilibj2.command.Command;
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
  // private final ShootAngleSubsystem m_shootAngleSubsystem;
  private final ShooterSubsystem m_shooterSubsystem;
  private final IntakeSubsystem m_intakeSubsystem;
  private final IndexSubsystem m_indexSubsystem;

  public static final Joystick driverRightStick = new Joystick(1);
  public static final Joystick driverLeftStick = new Joystick(0);
  public static final Joystick manipulatorRightStick = new Joystick(3);
  public static final Joystick manipulatorLeftStick = new Joystick(2);

  public double testShooterSpeed;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings

    this.m_driveSubsystem = new DriveSubsystem();
    this.m_autoCommand = new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick);
    this.m_driveSubsystem.setDefaultCommand(new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick));
    
    this.m_shooterSubsystem = new ShooterSubsystem();
    this.m_intakeSubsystem = new IntakeSubsystem();
    this.m_indexSubsystem = new IndexSubsystem();

    this.testShooterSpeed = 0;
    SmartDashboard.putNumber("TestShooterSpeed", this.testShooterSpeed);

    configureButtonBindings();
  
    // this.m_shootAngleSubsystem = new ShootAngleSubsystem();
    // this.m_shootAngleSubsystem.setDefaultCommand(new AdjustShootAngle(this.m_shootAngleSubsystem, manipulatorLeftStick));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driverRightStick, 1).whenPressed(new InstantCommand(m_driveSubsystem::invertDrivetrain, m_driveSubsystem));
    
    new JoystickButton(manipulatorRightStick, 2).whileHeld(new ShootCommand(0.2D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 3).whileHeld(new ShootCommand(0.4D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 4).whileHeld(new ShootCommand(0.6D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 5).whileHeld(new ShootCommand(0.8D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));

    new JoystickButton(manipulatorLeftStick, 2).whileHeld(new ShootCommand(2000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 3).whileHeld(new ShootCommand(3000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 4).whileHeld(new ShootCommand(4000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 5).whileHeld(new ShootCommand(5000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 6).whileHeld(new ShootCommand(this.testShooterSpeed, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));


    new JoystickButton(manipulatorLeftStick, 8).whileHeld(new RunIndexCommand(m_indexSubsystem, 0.4D));
    new JoystickButton(manipulatorLeftStick, 9).whileHeld(new RunIndexCommand(m_indexSubsystem, -0.4D));

    new JoystickButton(manipulatorRightStick, 8).whileHeld(new IntakeCommand(m_intakeSubsystem, 0.5D));
    new JoystickButton(manipulatorRightStick, 9).whileHeld(new IntakeCommand(m_intakeSubsystem, -0.5D));
    // new JoystickButton(manipulatorLeftStick, 4).whileHeld((Command) new AdjustShootAngle(m_shootAngleSubsystem, manipulatorLeftStick));
    // new JoystickButton(manipulatorLeftStick, 5).whileHeld((Command) new AdjustShootAngle(m_shootAngleSubsystem, manipulatorLeftStick));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  // @Override
  public void teleopPeriodic() {
    testShooterSpeed = SmartDashboard.getNumber("TestShooterSpeed", 0);
  }
}
