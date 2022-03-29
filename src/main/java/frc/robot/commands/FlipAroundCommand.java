package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class FlipAroundCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final double buffer;
    private final double setPoint;
    private final double speed;
    
    public FlipAroundCommand(DriveSubsystem subsystem, double degrees, double speed) {
        this.m_subsystem = subsystem;
        this.m_subsystem.zeroHeading();
        this.setPoint = degrees;
        this.speed = speed;
        this.buffer = 2;
    }

    @Override
    public void execute() {
        m_subsystem.tankDrive(speed, -speed);
    }

    @Override
    public void end(boolean interupted) {
        m_subsystem.tankDrive(0.0D, 0.0D);
    }

    @Override
    public boolean isFinished() {
        double val = m_subsystem.getHeading();
        if (setPoint < 0) {
            return val < setPoint + buffer && val > setPoint - buffer;
        } else {
            return val < setPoint + buffer && val < setPoint - buffer;
        }
    }    
}
