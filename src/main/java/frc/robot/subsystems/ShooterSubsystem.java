package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ShooterSubsystem extends SubsystemBase{
    private final CANSparkMax shootMotor;
    private final RelativeEncoder shootEncoder;
    private final SparkMaxPIDController shootPID;

    private final Integer pointsUntilReady;
    private List<Double> lastData;
    private double averageValue;

    private double setPoint;
    private double marginOfError;

    public ShooterSubsystem() {
        this.shootMotor = new CANSparkMax(Constants.Shooter.shooterPort, MotorType.kBrushless);
        this.shootMotor.restoreFactoryDefaults();

        // set up encoder and PID controller
        this.shootEncoder = shootMotor.getEncoder();
        this.shootPID = shootMotor.getPIDController();
        this.shootPID.setP(Constants.Shooter.kP);
        this.shootPID.setI(Constants.Shooter.kI);
        this.shootPID.setD(Constants.Shooter.kD);
        this.shootPID.setIZone(Constants.Shooter.kIZone);
        this.shootPID.setFF(Constants.Shooter.kFF);
        this.shootPID.setOutputRange(Constants.Shooter.kMinOutput, Constants.Shooter.kMaxOutput);

        // set up data logging
        this.pointsUntilReady = 75;
        this.lastData = (List<Double>)(new ArrayList<Double>());

        this.setPoint = 0.0D; // given value in ShootCommand when run in velocity mode
        this.marginOfError = 40.0D;
    }

    public void runShootMotorPercent(double speed) {
        shootMotor.set(speed);
    }

    public double getShooterSpeed() {
        return shootEncoder.getVelocity();
    }

    public final boolean isReady() {
        boolean isOK1 = averageValue >= setPoint - marginOfError && averageValue <= setPoint + marginOfError;
        double speed = getShooterSpeed();
        boolean isOK2 = speed >= setPoint - marginOfError && speed <= setPoint + marginOfError;
        return isOK1 && isOK2;
    }

    public final void setSetPoint(double _setPoint) {
        this.setPoint = _setPoint;
    }

    public final void runPID() {
        shootPID.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    }
    
    public void periodic() {
        addDatapoint(lastData, shootEncoder.getVelocity());
        averageValue = calculateAverage(lastData);
        SmartDashboard.putNumber("ShooterSpeed", shootEncoder.getVelocity());
        SmartDashboard.putNumber("ShooterAverageSpeed", averageValue);
    }

    private double calculateAverage(List <Double> marks) {
        double sum = 0;
        if(!marks.isEmpty()) {
          for (Double mark : marks) {
              sum += mark;
          }
          return sum / marks.size();
        }
        return sum;
    }

    private final List<Double> addDatapoint(List<Double> list, Double datapoint) {
        list.add(datapoint.doubleValue());
        if (list.size() <= this.pointsUntilReady) {
           return list;
        } else {
           list.remove(0);
           return list;
        }
     }
}
