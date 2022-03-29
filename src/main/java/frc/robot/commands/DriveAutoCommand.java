package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveAutoCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final double leftSpeed;
    private final double rightSpeed;

    public DriveAutoCommand(DriveSubsystem subsystem, double leftSpeed, double rightSpeed) {
        this.m_subsystem = subsystem;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    @Override
    public void execute() {
        m_subsystem.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    public void end(boolean interupted) {
        m_subsystem.tankDrive(0.0D, 0.0D);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
