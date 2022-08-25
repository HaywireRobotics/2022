package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase{
    private final IntakeSubsystem m_subsystem;
    private final double speed;

    public IntakeCommand(double _speed, IntakeSubsystem subsystem) {
        this.m_subsystem = subsystem;
        this.speed = _speed;
    }

    @Override
    public void execute() {
        m_subsystem.driveIntake(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.driveIntake(0.0D);
    }
}
