package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

// I needed to put this in a command so that it could set the ready variable
// back to false when the button was released by just using a JoystickButton.whileHeld
public class ReadyToShootCommand extends CommandBase {
    private final ShooterSubsystem m_subsystem;

    public ReadyToShootCommand(ShooterSubsystem subsystem) {
        this.m_subsystem = subsystem;
    }

    @Override
    public void execute() {
        m_subsystem.driverReady(true);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.driverReady(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
