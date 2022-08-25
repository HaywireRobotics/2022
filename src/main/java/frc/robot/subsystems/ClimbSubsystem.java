// aka IndustrialFishingPoleSubsystem

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase{
    public final CANSparkMax leftWinch;
    public final CANSparkMax rightWinch;
    public final CANSparkMax babyHooks;

    public ClimbSubsystem() {
        this.leftWinch = new CANSparkMax(Constants.Climber.leftWinchPort, MotorType.kBrushless);
        this.rightWinch = new CANSparkMax(Constants.Climber.rightWinchPort, MotorType.kBrushless);
        this.babyHooks = new CANSparkMax(Constants.Climber.babyHooksPort, MotorType.kBrushless);

        this.leftWinch.setIdleMode(IdleMode.kBrake);
        this.rightWinch.setIdleMode(IdleMode.kBrake);
        this.babyHooks.setIdleMode(IdleMode.kBrake);
    }

    public void driveWinch(double speed) { // which one is negative will probly need to change lol
        leftWinch.set(-speed);
        rightWinch.set(speed);
    }

    public void stopWinch() {
        leftWinch.stopMotor();
        rightWinch.stopMotor();
    }

    public void moveBabyHooks(double speed) {  // pls keep speed very low k thanks luv you bye <3
        babyHooks.set(speed);
    }
}
