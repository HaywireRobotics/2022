package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase{
    private final CANSparkMax armMotor;
    private final WPI_VictorSPX intakeMotor;

    private final RelativeEncoder armEncoder;
    private final SparkMaxPIDController armPID;

    public IntakeSubsystem() {
        this.armMotor = new CANSparkMax(Constants.Intake.armPort, MotorType.kBrushless);
        this.intakeMotor = new WPI_VictorSPX(Constants.Intake.intakePort); // may be subject to change
        this.armMotor.restoreFactoryDefaults();

        this.armEncoder = armMotor.getEncoder();
        this.armPID = armMotor.getPIDController();

        this.armPID.setP(Constants.Intake.kP);
        this.armPID.setI(Constants.Intake.kI);
        this.armPID.setD(Constants.Intake.kD);
        this.armPID.setIZone(Constants.Intake.kIZone);
        this.armPID.setFF(Constants.Intake.kFF);
        this.armPID.setOutputRange(Constants.Intake.kMinOutput, Constants.Intake.kMaxOutput);
    }

    public void driveIntake(double speed) {
        intakeMotor.set(speed);
    }

    // move arm to certain angle, will need strict range and conversion
    public void moveArm(double position) { // position needs to be a number of rotations
        armPID.setReference(position, ControlType.kPosition);
    }
    
}
