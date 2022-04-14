package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.Shooter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Auto1Command extends SequentialCommandGroup {
    private final DriveSubsystem m_driveSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final IndexSubsystem m_indexSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final Trajectory trajectory;

    public Auto1Command(DriveSubsystem subsystem, IntakeSubsystem intakeSubsystem, IndexSubsystem indexSubsystem, ShooterSubsystem shooterSubsystem, Trajectory _trajectory) {
        this.m_driveSubsystem = subsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_indexSubsystem = indexSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.trajectory = _trajectory;

        andThen(new InstantCommand(m_driveSubsystem::brakeOn, m_driveSubsystem));
        andThen(new ShootCommand(3900, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem).withTimeout(4.5));
        andThen(new DriveDistanceCommand(m_driveSubsystem, -54, 0.5));
    }
}
