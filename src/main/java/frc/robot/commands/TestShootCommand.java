package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BackspinSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class TestShootCommand extends SequentialCommandGroup {
    private final ShootCommand m_shootCommand;
    public TestShootCommand(boolean _percentMode, boolean isBasic, ShooterSubsystem subsystem, IntakeSubsystem intake, IndexSubsystem index, BackspinSubsystem backspin) {
        double speed = SmartDashboard.getNumber("TestShooterSpeed", 0);
        double backspinSpeed = SmartDashboard.getNumber("TestBackspinSpeed", 0);
        this.m_shootCommand = new ShootCommand(speed, backspinSpeed, _percentMode, isBasic, subsystem, intake, index, backspin);
        andThen(m_shootCommand);
    }
}
