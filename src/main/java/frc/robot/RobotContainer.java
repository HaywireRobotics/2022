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
import frc.robot.commands.DriveCommandsHotterSister;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.ReadyToShootCommand;
import frc.robot.commands.ShootBackspinCommand;
import frc.robot.commands.IndexCommand;
import frc.robot.subsystems.*;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TestShootCommand;
import frc.robot.commands.r2SetOdometry;
import frc.robot.commands.r2goBrrrrr;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
//import frc.robot.Constants;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem;
  // private final DriveCommand m_autoCommand;
  private SendableChooser<Integer> autoCommandChooser = new SendableChooser<Integer>();
  private List<Trajectory> paths = new ArrayList<Trajectory>();
  private final ShooterSubsystem m_shooterSubsystem;
  private final IntakeSubsystem m_intakeSubsystem;
  private final IndexSubsystem m_indexSubsystem;
  private final ClimbSubsystem m_climbSubsystem;
  private final BackspinSubsystem m_backspinSubsystem;

  public static final Joystick driverRightStick = new Joystick(2);
  public static final Joystick driverLeftStick = new Joystick(1);
  public static final Joystick manipulatorRightStick = new Joystick(0);
  public static final Joystick manipulatorLeftStick = new Joystick(3);
  public static final JoystickButton manipulatorButton = new JoystickButton(manipulatorRightStick, 10);

  public final XboxController xboxController = new XboxController(4);

  public double testShooterSpeed;
  public double testBackspinSpeed;

  public <Sendable> RobotContainer() {
    System.out.println("Starting Robot Container Construction");

    this.m_driveSubsystem = new DriveSubsystem();
    // this.m_autoCommand = new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick);
    this.m_driveSubsystem.setDefaultCommand(new DriveCommand(this.m_driveSubsystem, driverLeftStick, driverRightStick));
    // this.m_driveSubsystem.setDefaultCommand(new DriveCommandsHotterSister(this.m_driveSubsystem, xboxController));
    
    this.m_backspinSubsystem = new BackspinSubsystem();
    this.m_shooterSubsystem = new ShooterSubsystem();
    this.m_intakeSubsystem = new IntakeSubsystem();
    this.m_indexSubsystem = new IndexSubsystem();
    this.m_indexSubsystem.setDefaultCommand(new IndexCommand(this.m_indexSubsystem, manipulatorRightStick));
    this.m_climbSubsystem = new ClimbSubsystem();
    this.m_climbSubsystem.setDefaultCommand(new ClimbCommand(this.m_climbSubsystem, manipulatorLeftStick));

    this.testShooterSpeed = createSmartDashboardNumber("TestShooterSpeed", 0);
    this.testBackspinSpeed = createSmartDashboardNumber("TestBackspinSpeed", 0);

    configureButtonBindings();

    this.autoCommandChooser.setDefaultOption("Auto2", 0);
    this.autoCommandChooser.addOption("Auto3", 1);
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

  public static double createSmartDashboardNumber(String key, double defValue) {

    // See if already on dashboard, and if so, fetch current value
    double value = SmartDashboard.getNumber(key, defValue);
  
    // Make sure value is on dashboard, puts back current value if already set
    // otherwise puts back default value
    SmartDashboard.putNumber(key, value);
  
    return value;
  }

  public void getTestSpeeds() {
    this.testShooterSpeed = SmartDashboard.getNumber("TestShooterSpeed", 0);
    this.testBackspinSpeed = SmartDashboard.getNumber("TestBackspinSpeed", 0);
  }

  public Command getAutonomousCommand() {
    int trajectoryIdx = autoCommandChooser.getSelected();

    Trajectory trajectory = paths.get(trajectoryIdx);
    
    if (trajectoryIdx == 0) {
      return new Auto2Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, m_backspinSubsystem, trajectory);
    } else if (trajectoryIdx == 1) {
      return new Auto3Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, m_backspinSubsystem, trajectory);
    } else if (trajectoryIdx == 2) {
      return new Auto1Command(m_driveSubsystem, m_intakeSubsystem, m_indexSubsystem, m_shooterSubsystem, m_backspinSubsystem, trajectory);
    } else {
      return new r2goBrrrrr(m_driveSubsystem, trajectory);
    }
  }

  private void configureButtonBindings() {
    // drivetrain inversion and idle mode control
    new JoystickButton(driverLeftStick, 6).whenPressed(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    new JoystickButton(driverLeftStick, 1).whenPressed(new InstantCommand(m_driveSubsystem::invertDrivetrain, m_driveSubsystem));

    // Intake controls
    new JoystickButton(driverRightStick, 1).whileHeld(new IntakeCommand(0.6D, m_intakeSubsystem));
    new JoystickButton(manipulatorRightStick, 2).whileHeld(new IntakeCommand(0.6D, m_intakeSubsystem));
    new JoystickButton(driverRightStick, 3).whileHeld(new IntakeCommand(-0.6D, m_intakeSubsystem));
    new JoystickButton(manipulatorRightStick, 3).whileHeld(new IntakeCommand(-0.6D, m_intakeSubsystem));

    // Intake Arm controls
    new JoystickButton(driverLeftStick, 3).whileHeld(new MoveArmCommand(0.22D, m_intakeSubsystem));
    new JoystickButton(manipulatorLeftStick, 3).whileHeld(new MoveArmCommand(0.22D, m_intakeSubsystem));
    new JoystickButton(driverLeftStick, 2).whileHeld(new MoveArmCommand(-0.22D, m_intakeSubsystem));
    new JoystickButton(manipulatorLeftStick, 2).whileHeld(new MoveArmCommand(-0.22D, m_intakeSubsystem));

    // Babyhook Controls
    new JoystickButton(manipulatorRightStick, 8).whileHeld(new BabyHooksCommand(-0.16, m_climbSubsystem));
    new JoystickButton(manipulatorRightStick, 9).whileHeld(new BabyHooksCommand(0.16, m_climbSubsystem));

    // shooters that need ready trigger
    // THE TESTED BACKSPIN RATIO IS 47:88
    // 4400
    new JoystickButton(manipulatorLeftStick, 4).whileHeld(new ShootBackspinCommand(2350, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorLeftStick, 5).whileHeld(new ShootBackspinCommand(3000, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem ));
    new JoystickButton(manipulatorRightStick,4).whileHeld(new ShootBackspinCommand(4200, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorRightStick,5).whileHeld(new ShootBackspinCommand(5000, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorLeftStick, 1).whileHeld(new ShootCommand(-0.1D, 0.5D, true, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorLeftStick, 6).whileHeld(new TestShootCommand(false, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    
    // ready trigger
    new JoystickButton(manipulatorRightStick, 1).whileHeld(new ReadyToShootCommand(this.m_shooterSubsystem));
  
    // no ready trigger needed
    new JoystickButton(manipulatorLeftStick,   7).whileHeld(new ShootBackspinCommand(2350, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorLeftStick,  10).whileHeld(new ShootBackspinCommand(3500, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorRightStick,  7).whileHeld(new ShootBackspinCommand(4200, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
    new JoystickButton(manipulatorRightStick, 10).whileHeld(new ShootBackspinCommand(5000, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
  }
}
