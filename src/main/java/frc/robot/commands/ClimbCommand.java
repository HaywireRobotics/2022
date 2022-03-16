package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase{
    public final ClimbSubsystem m_subsystem;
    public final double speed;

    public ClimbCommand(double _speed, ClimbSubsystem subsystem) {
        this.m_subsystem = subsystem;
        this.speed = _speed;
    }

    @Override
    public void execute() {
        m_subsystem.driveWinch(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.stopWinch();
    }
}
