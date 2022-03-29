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

public class Auto3Command extends SequentialCommandGroup{
    private final DriveSubsystem m_driveSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final IndexSubsystem m_indexSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final Trajectory trajectory;

    public Auto3Command(DriveSubsystem subsystem, IntakeSubsystem intakeSubsystem, IndexSubsystem indexSubsystem, ShooterSubsystem shooterSubsystem, Trajectory _trajectory) {
        this.m_driveSubsystem = subsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_indexSubsystem = indexSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.trajectory = _trajectory;

        andThen(new InstantCommand(m_driveSubsystem::brakeOn, m_driveSubsystem));
        andThen(new ShootCommand(3900, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem).withTimeout(3));
        andThen(new DriveAutoCommand(m_driveSubsystem, 0.4, -0.4).withTimeout(1.9));
        andThen(new MoveArmCommand(-0.2, m_intakeSubsystem).withTimeout(0.8));
        andThen(new InstantCommand(m_driveSubsystem::zeroHeading, m_driveSubsystem));
        andThen(CommandGroupBase.deadline(new r2goBrrrrr(m_driveSubsystem, trajectory),
                new IntakeCommand(0.7D, m_intakeSubsystem)));
    //     andThen(CommandGroupBase.deadline(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(2.2), 
    //                                     new IntakeCommand(0.8D, m_intakeSubsystem)));
    //     // andThen(new FlipAroundCommand(m_driveSubsystem));
    //     andThen(new DriveAutoCommand(m_driveSubsystem, -0.4, 0.4).withTimeout(1.15));
    //    // andThen(new MoveArmCommand(0.2, m_intakeSubsystem).withTimeout(0.9));
    //    // andThen(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(2.1));
    //    andThen(CommandGroupBase.deadline(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(3), 
                                        // new IntakeCommand(0.8D, m_intakeSubsystem)));
        // andThen(new DriveAutoCommand(m_driveSubsystem, -0.4, 0.4).withTimeout(1.2));
        andThen(new MoveArmCommand(0.2, m_intakeSubsystem).withTimeout(0.9));
        // andThen(new DriveAutoCommand(m_driveSubsystem, -0.5, -0.5).withTimeout(1.76));
        andThen(new ShootCommand(3900, false, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem).withTimeout(5));
        andThen(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    }
}
