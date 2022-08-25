package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BackspinSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootBackspinCommand extends ShootCommand {

    public ShootBackspinCommand(double speed, boolean requiresReadyTrigger, ShooterSubsystem subsystem, IntakeSubsystem intake, IndexSubsystem index, BackspinSubsystem backspin) {
        super(speed, speed * Constants.Backspin.kBackspin, false, requiresReadyTrigger, subsystem, intake, index, backspin);
    }
    
}
