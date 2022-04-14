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
    private final boolean isBasic;
    private final Timer shootTimer;

    public ShootCommand(double speed, boolean _percentMode, boolean isBasic, ShooterSubsystem subsystem, IntakeSubsystem intake, IndexSubsystem index) {
        this.m_subsystem = subsystem;
        this.m_intake = intake;
        this.m_index = index;
        this.shooterSpeed = speed;
        this.percentMode = _percentMode;
        this.isBasic = isBasic;
        this.shootTimer = new Timer();
        shootTimer.reset();
        addRequirements(index);
    }

    @Override
    public void initialize() {
        // if (!percentMode) {
            // this.m_subsystem.setSetPoint(-shooterSpeed);
        // }
        shootTimer.reset();
        shootTimer.stop();
    };

    @Override
    public void execute() {
        m_subsystem.setSetPoint(-shooterSpeed);
        boolean isReady = false;
        if (isBasic) {
            isReady = m_subsystem.isReadySpeed();
        } else {
            isReady = m_subsystem.isReadyDriver();
        }

        if (percentMode) {
            m_subsystem.runShootMotorPercent(-shooterSpeed);
        } else {
            m_subsystem.runPID();
            // when the shooter is running at the setpoint speed, the intake and index will drive to shoot the balls
            if (isReady && shootTimer.hasElapsed(.3)) {
                m_index.driveIndex(0.4D);
                m_intake.driveIntake(0.4D);
            // } else if (m_subsystem.isReady() && shootTimer.hasElapsed(.2)) {
            //     m_index.driveIndex(0.4D);
            } else if (isReady) {
                m_index.driveIndex(0.4D);
                shootTimer.start();
            } else {
                shootTimer.stop();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.runShootMotorPercent(0.0D);
        m_index.driveIndex(0.0D);
        m_intake.driveIntake(0.0D);
        shootTimer.reset();
        shootTimer.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
