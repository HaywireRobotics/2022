package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
    private final ShooterSubsystem m_subsystem;
    private final double shooterSpeed;
    private final boolean percentMode;
    private boolean reachedSpeed;

    public ShootCommand(ShooterSubsystem subsystem, double speed, boolean _percentMode) {
        this.m_subsystem = subsystem;
        this.shooterSpeed = speed;
        this.percentMode = _percentMode;
        this.reachedSpeed = false;
    }

    @Override
    public void initialize() {
        // if (!percentMode) {
            // this.m_subsystem.setSetPoint(-shooterSpeed);
        // }
    };

    @Override
    public void execute() {
        if (percentMode) {
            m_subsystem.runShootMotorPercent(-shooterSpeed);
        } else {
            m_subsystem.setSetPoint(-shooterSpeed);
            m_subsystem.runPID();
            if (m_subsystem.isReady() && !reachedSpeed) {
                System.out.println("---- READY TO SHOOT ----");
                this.reachedSpeed = true;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.runShootMotorPercent(0.0D);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
