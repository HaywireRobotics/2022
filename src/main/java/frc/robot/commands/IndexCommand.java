package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;

public class IndexCommand extends CommandBase{
    private final IndexSubsystem m_subsystem;
    private final Joystick rightStick;

    public IndexCommand(IndexSubsystem subsystem, Joystick rightStick) {
        this.m_subsystem = subsystem;
        this.rightStick = rightStick;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double val = rightStick.getY();
        if (val > 0.5D) {
            m_subsystem.driveIndex(0.35D);
        } else if (val < -0.5D) {
            m_subsystem.driveIndex(-0.35D);
        } else {
            m_subsystem.driveIndex(0.0D);
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.driveIndex(0.0D);
    }
}
