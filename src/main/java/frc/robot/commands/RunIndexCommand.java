package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;

public class RunIndexCommand extends CommandBase{
    private final IndexSubsystem m_subsystem;
    private final double speed;

    public RunIndexCommand(double _speed, IndexSubsystem subsystem) {
        this.m_subsystem = subsystem;
        this.speed = _speed;
    }

    @Override
    public void execute() {
        m_subsystem.driveIndex(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.driveIndex(0.0D);
    }
}
