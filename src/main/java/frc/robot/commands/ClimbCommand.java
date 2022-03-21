package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase{
    public final ClimbSubsystem m_subsystem;
    public final Joystick leftStick;

    public ClimbCommand(ClimbSubsystem subsystem, Joystick leftStick) {
        this.m_subsystem = subsystem;
        this.leftStick = leftStick;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double val = this.leftStick.getY();
        if (val > 0.4D) {
            m_subsystem.driveWinch(-0.75D);
        } else if (val < -0.4D) {
            m_subsystem.driveWinch(0.75D);
        } else {
            m_subsystem.driveWinch(0.0D);
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.stopWinch();
    }
}
