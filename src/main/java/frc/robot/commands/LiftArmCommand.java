package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class LiftArmCommand extends CommandBase{
    private final IntakeSubsystem m_subsystem;

    public LiftArmCommand(IntakeSubsystem subsystem) {
        this.m_subsystem = subsystem;
    }
}
