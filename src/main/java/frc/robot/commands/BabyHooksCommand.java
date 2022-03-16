package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class BabyHooksCommand extends CommandBase{
    private final ClimbSubsystem m_subsystem;
    private final double speed;

    public BabyHooksCommand(double speed, ClimbSubsystem subsystem) {  // btw pls keep speed very low k thx luv u <3
        this.m_subsystem = subsystem;
        this.speed = speed;
    }

    @Override
    public void execute() {
        m_subsystem.moveBabyHooks(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.moveBabyHooks(0.0D);
    }
}
