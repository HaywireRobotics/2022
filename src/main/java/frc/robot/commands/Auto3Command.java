// watch as I pull a pro gamer move and fix this on the bus ride there and then it works flawlessly first try :)

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
        andThen(new MoveArmCommand(-0.2, m_intakeSubsystem).withTimeout(0.8));
        andThen(CommandGroupBase.deadline(new DriveDistanceCommand(m_driveSubsystem, 30, 0.6),
                                          new IntakeCommand(0.8, m_intakeSubsystem)));
        andThen(new IntakeCommand(0.8, m_intakeSubsystem).withTimeout(0.4));
        andThen(new FlipAroundCommand(m_driveSubsystem, -160, -0.45));
        andThen(new DriveDistanceCommand(m_driveSubsystem, 40, 0.65));
        andThen(new ShootCommand(4200, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem).withTimeout(2.5));
        andThen(new FlipAroundCommand(m_driveSubsystem, -100, 0.45));
        andThen(CommandGroupBase.deadline(new DriveDistanceCommand(m_driveSubsystem, 120, 0.6),
                                          new IntakeCommand(0.8, m_intakeSubsystem)));
        andThen(new IntakeCommand(0.8, m_intakeSubsystem).withTimeout(0.2));
        andThen(new FlipAroundCommand(m_driveSubsystem, 140, -0.5));
        andThen(new DriveDistanceCommand(m_driveSubsystem, 70, 0.55));
        andThen(new ShootCommand(4200, false, true, m_shooterSubsystem, m_intakeSubsystem, m_indexSubsystem).withTimeout(5));
        andThen(new InstantCommand(m_driveSubsystem::toggleIdle, m_driveSubsystem));
    }
}
