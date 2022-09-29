package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommandsHotterSister extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final XboxController m_controller;

    public DriveCommandsHotterSister(DriveSubsystem subsystem, XboxController controller) {
        m_subsystem = subsystem;
        m_controller = controller;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double leftYValue = m_controller.getLeftY();
        double rightYValue = m_controller.getRightY();

        // m_controller.setRumble(GenericHID.RumbleType.kLeftRumble, leftYValue);

        m_controller.setRumble(GenericHID.RumbleType.kRightRumble, (Math.abs(rightYValue) + Math.abs(leftYValue)) / 2);
    }

    @Override
    public void end(boolean interrupted) {
        m_controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0.0);
        m_controller.setRumble(GenericHID.RumbleType.kRightRumble, 0.0);
    }

    @Override
    public boolean isFinished() {
        m_controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0.0);
        m_controller.setRumble(GenericHID.RumbleType.kRightRumble, 0.0);
        return false;
    }
}
