package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
 
public class MoveArmCommand extends CommandBase{
    private final IntakeSubsystem m_subsystem;
    private final double speed;

    public MoveArmCommand(double _speed, IntakeSubsystem subsystem) {
        this.m_subsystem = subsystem;
        this.speed = _speed;
    }

    @Override
    public void execute() {
        m_subsystem.moveArm(speed);
    }

    @Override
    public void end(boolean interupted) {
        m_subsystem.stopArm();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
