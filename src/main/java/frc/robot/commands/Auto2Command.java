package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.Shooter;
import frc.robot.subsystems.*;

public class Auto2Command extends SequentialCommandGroup {
    private final DriveSubsystem m_driveSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final IndexSubsystem m_indexSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final BackspinSubsystem m_backspinSubsystem;
    private final Trajectory trajectory;

    public Auto2Command(DriveSubsystem subsystem, IntakeSubsystem intakeSubsystem, IndexSubsystem indexSubsystem, ShooterSubsystem shooterSubsystem, BackspinSubsystem backspinSubsystem, Trajectory _trajectory) {
        this.m_driveSubsystem = subsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_indexSubsystem = indexSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_backspinSubsystem = backspinSubsystem;
        this.trajectory = _trajectory;

        andThen(new InstantCommand(m_driveSubsystem::brakeOn, m_driveSubsystem));
        andThen(new MoveArmCommand(-0.2, m_intakeSubsystem).withTimeout(0.8));
        // andThen(CommandGroupBase.deadline(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(2), 
        //                                   new IntakeCommand(0.7D, m_intakeSubsystem)));
        andThen(new InstantCommand(m_driveSubsystem::resetOdo, m_driveSubsystem));
        andThen(CommandGroupBase.deadline(new DriveDistanceCommand(m_driveSubsystem, 54, 0.55), 
                                          new IntakeCommand(0.7D, m_intakeSubsystem)));
        andThen(new IntakeCommand(0.7D, m_intakeSubsystem).withTimeout(0.2));
        andThen(new FlipAroundCommand(m_driveSubsystem, -148, -0.44));
        // andThen(new DriveAutoCommand(m_driveSubsystem, 0.4, -0.4).withTimeout(1.85));
        andThen(new MoveArmCommand(0.2, m_intakeSubsystem).withTimeout(1.05));
        andThen(new InstantCommand(m_driveSubsystem::resetOdo, m_driveSubsystem));
        andThen(new DriveDistanceCommand(m_driveSubsystem, 59, 0.55));
        // andThen(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(2.1));
        andThen(new ShootCommand(3800, 4400, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem, m_backspinSubsystem));
        andThen(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    }
}
