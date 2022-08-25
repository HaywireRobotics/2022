package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistanceCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final double setPoint;
    private final double speed;
    private final double buffer = 1;

    public DriveDistanceCommand(DriveSubsystem subsystem, double setPoint, double speed) {
        this.m_subsystem = subsystem;
        this.setPoint = setPoint;
        this.speed = speed;
        this.m_subsystem.resetOdo();
    }

    @Override
    public void execute() {
        if (setPoint > 0) {
            m_subsystem.tankDrive(-speed, -speed);
        } else {
            m_subsystem.tankDrive(speed, speed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.tankDrive(0.0D, 0.0D);
    }

    @Override
    public boolean isFinished() {
        double val = m_subsystem.getTranslation().getX();
        return val > setPoint - buffer && val < setPoint + buffer;
    }
}
