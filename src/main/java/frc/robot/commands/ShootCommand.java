package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
    private final ShooterSubsystem m_subsystem;
    private final IntakeSubsystem m_intake;
    private final IndexSubsystem m_index;
    private final double shooterSpeed;
    private final boolean percentMode;
    private final Timer shootTimer;

    public ShootCommand(double speed, boolean _percentMode, ShooterSubsystem subsystem, IntakeSubsystem intake, IndexSubsystem index) {
        this.m_subsystem = subsystem;
        this.m_intake = intake;
        this.m_index = index;
        this.shooterSpeed = speed;
        this.percentMode = _percentMode;
        this.shootTimer = new Timer();
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
            // when the shooter is running at the setpoint speed, the intake and index will drive to shoot the balls
            if (m_subsystem.isReady() && shootTimer.hasElapsed(.7)) {
                m_index.driveIndex(0.4D);
                m_intake.driveIntake(0.4D);
            } else if (m_subsystem.isReady()) {
                m_index.driveIndex(0.4D);
                shootTimer.start();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.runShootMotorPercent(0.0D);
        m_index.driveIndex(0.0D);
        m_intake.driveIntake(0.0D);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
