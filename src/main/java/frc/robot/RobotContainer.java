// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.BabyHooksCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.RunIndexCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
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
  private SendableChooser<Integer> autoCommandChooser = new SendableChooser<Integer>();
  private final ShooterSubsystem m_shooterSubsystem;
  private final IntakeSubsystem m_intakeSubsystem;
  private final IndexSubsystem m_indexSubsystem;
  private final ClimbSubsystem m_climbSubsystem;

  public static final Joystick driverRightStick = new Joystick(1);
  public static final Joystick driverLeftStick = new Joystick(0);
  public static final Joystick manipulatorRightStick = new Joystick(3);
  public static final Joystick manipulatorLeftStick = new Joystick(2);

  public double testShooterSpeed;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    System.out.println("Starting Robot Container Construction");

    this.m_driveSubsystem = new DriveSubsystem();
    this.m_autoCommand = new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick);
    this.m_driveSubsystem.setDefaultCommand(new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick));
    
    this.m_shooterSubsystem = new ShooterSubsystem();
    this.m_intakeSubsystem = new IntakeSubsystem();
    this.m_indexSubsystem = new IndexSubsystem();
    this.m_climbSubsystem = new ClimbSubsystem();

    this.testShooterSpeed = 0;
    SmartDashboard.putNumber("TestShooterSpeed", this.testShooterSpeed);

    configureButtonBindings(); 

    System.out.println("End of Robot Container Constructor");
  }

  private void configureButtonBindings() {
    new JoystickButton(driverRightStick, 1).whenPressed(new InstantCommand(m_driveSubsystem::invertDrivetrain, m_driveSubsystem));
    new JoystickButton(driverRightStick, 2).whileHeld(new IntakeCommand(0.5D, m_intakeSubsystem));
    new JoystickButton(driverRightStick, 3).whileHeld(new IntakeCommand(-0.5D, m_intakeSubsystem));

    new JoystickButton(driverLeftStick, 1).whenPressed(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    new JoystickButton(driverLeftStick, 2).whileHeld(new MoveArmCommand(-.07D, m_intakeSubsystem));
    new JoystickButton(driverLeftStick, 3).whileHeld(new MoveArmCommand(0.07D, m_intakeSubsystem));

    new JoystickButton(manipulatorRightStick, 2).whileHeld(new ShootCommand(0.2D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 3).whileHeld(new ShootCommand(0.4D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 4).whileHeld(new ShootCommand(0.6D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 5).whileHeld(new ShootCommand(0.8D, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorRightStick, 6).whileHeld(new ClimbCommand(-0.5D, m_climbSubsystem));
    new JoystickButton(manipulatorRightStick, 7).whileHeld(new ClimbCommand(0.75D, m_climbSubsystem));
    new JoystickButton(manipulatorRightStick, 10).whileHeld(new BabyHooksCommand(-0.06, m_climbSubsystem));
    new JoystickButton(manipulatorRightStick, 11).whileHeld(new BabyHooksCommand(0.06, m_climbSubsystem));

    new JoystickButton(manipulatorLeftStick, 2).whileHeld(new ShootCommand(2000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 3).whileHeld(new ShootCommand(3000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 4).whileHeld(new ShootCommand(4000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 5).whileHeld(new ShootCommand(5000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 6).whileHeld(new ShootCommand(this.testShooterSpeed, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 8).whileHeld(new RunIndexCommand(0.4D, m_indexSubsystem));
    new JoystickButton(manipulatorLeftStick, 9).whileHeld(new RunIndexCommand(-0.4D, m_indexSubsystem));
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  // @Override
  public void teleopPeriodic() {
    testShooterSpeed = SmartDashboard.getNumber("TestShooterSpeed", 0);
  }
}
