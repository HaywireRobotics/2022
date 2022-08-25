package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase{
    private final CANSparkMax armMotor;
    private final CANSparkMax intakeMotor;

    private final RelativeEncoder armEncoder;
    private final SparkMaxPIDController armPID;

    public IntakeSubsystem() {
        this.armMotor = new CANSparkMax(Constants.Intake.armPort, MotorType.kBrushless);
        this.intakeMotor = new CANSparkMax(Constants.Intake.intakePort, MotorType.kBrushless); // may be subject to change
        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setIdleMode(IdleMode.kBrake);

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

    public double getPosition() {
        return armEncoder.getPosition();
    }
    
    // move arm, but not if it is on the edges of what is allowed
    public void moveArm(double speed) {
        double angle = armEncoder.getPosition();
        if (speed < 0 && angle > Constants.Intake.minAngle) {
            armMotor.set(speed);
        }
        if (speed > 0 && angle < Constants.Intake.maxAngle) {
            armMotor.set(speed);
        }
    }

    public void stopArm() {
        armMotor.set(0.0D);
    }
    
}
