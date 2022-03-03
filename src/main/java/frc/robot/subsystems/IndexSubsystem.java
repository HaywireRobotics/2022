package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class IndexSubsystem extends SubsystemBase{
    private final CANSparkMax indexMotor;

    public IndexSubsystem() {
        this.indexMotor = new CANSparkMax(Constants.Index.indexPort, MotorType.kBrushless);
        this.indexMotor.restoreFactoryDefaults();
    }

    public void driveIndex(double speed) {
        indexMotor.set(speed);
    }
}
