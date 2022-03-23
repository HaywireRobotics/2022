package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase{
    public final ClimbSubsystem m_subsystem;
    public final Joystick leftStick;
    public final JoystickButton manipulatorButton;

    public ClimbCommand(ClimbSubsystem subsystem, Joystick leftStick, JoystickButton manipulatorButton) {
        this.m_subsystem = subsystem;
        this.leftStick = leftStick;
        this.manipulatorButton = manipulatorButton;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double val = this.leftStick.getY();
        if (val > 0.3D) {
            m_subsystem.driveWinch((val - 0.3) / 1.0);
        } else if (val < -0.3D) {
            m_subsystem.driveWinch((val + 0.3) / 1.0);
        } else {
            m_subsystem.driveWinch(0.0D);
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.stopWinch();
    }
}
