package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class ShootCommand extends CommandBase {
    private final ShooterSubsystem m_subsystem;
    private final IntakeSubsystem m_intake;
    private final IndexSubsystem m_index;
    private final BackspinSubsystem m_backspin;
    private final double shooterSpeed;
    private final double backspinSpeed;
    private final boolean percentMode;
    private final boolean requiresReadyTrigger;
    private final Timer shootTimer;

    public ShootCommand(double speed, double backspinSpeed, boolean _percentMode, boolean requiresReadyTrigger, ShooterSubsystem subsystem, IntakeSubsystem intake, IndexSubsystem index, BackspinSubsystem backspin) {
        this.m_subsystem = subsystem;
        this.m_intake = intake;
        this.m_index = index;
        this.m_backspin = backspin;
        this.shooterSpeed = speed;
        this.backspinSpeed = backspinSpeed;
        this.percentMode = _percentMode;
        this.requiresReadyTrigger = requiresReadyTrigger; // basic mode is when the ready trigger is not needed in order to shoot
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
        m_backspin.setSetPoint(-backspinSpeed);
        boolean isReady = false;
        if (requiresReadyTrigger) {
            isReady = m_subsystem.isReadyDriver();
        } else {
            isReady = m_subsystem.isReadySpeed();
        }

        if (percentMode) {
            m_subsystem.runShootMotorPercent(-shooterSpeed);
            m_backspin.runBackspinMotorPercent(-backspinSpeed);
        } else {
            m_subsystem.runPID();
            m_backspin.runPID();
            // m_backspin.driveBackspin(backspinSpeed); this is the old way
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
        m_backspin.runBackspinMotorPercent(0.0D);
        shootTimer.reset();
        shootTimer.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
