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


public class BackspinSubsystem extends SubsystemBase{
    private final CANSparkMax backspinMotor;
    private final RelativeEncoder backspinEncoder;
    private final SparkMaxPIDController backspinPID;

    private final Integer pointsUntilReady;
    private List<Double> lastData;
    private double averageValue;
    private boolean driverIsReady;

    private double setPoint;
    private double marginOfError;

    public BackspinSubsystem() {
        this.backspinMotor = new CANSparkMax(Constants.Backspin.backspinPort, MotorType.kBrushless);
        this.backspinMotor.restoreFactoryDefaults();

        // set up encoder and PID controller
        this.backspinEncoder = backspinMotor.getEncoder();
        this.backspinPID = backspinMotor.getPIDController();
        this.backspinPID.setP(Constants.Backspin.kP);
        this.backspinPID.setI(Constants.Backspin.kI);
        this.backspinPID.setD(Constants.Backspin.kD);
        this.backspinPID.setIZone(Constants.Backspin.kIZone);
        this.backspinPID.setFF(Constants.Backspin.kFF);
        this.backspinPID.setOutputRange(Constants.Backspin.kMinOutput, Constants.Backspin.kMaxOutput);

        // set up data logging
        this.pointsUntilReady = 75;
        this.lastData = (List<Double>)(new ArrayList<Double>());

        this.setPoint = 0.0D; // given value in ShootCommand when run in velocity mode
        this.marginOfError = 40.0D;
        this.driverIsReady = false;
    }

    public void runBackspinMotorPercent(double speed) {
        backspinMotor.set(speed);
    }

    public double getBackspinSpeed() {
        return backspinEncoder.getVelocity();
    }

    public final boolean isReadySpeed() {
        boolean isOK1 = averageValue >= setPoint - marginOfError && averageValue <= setPoint + marginOfError;
        double speed = getBackspinSpeed();
        boolean isOK2 = speed >= setPoint - marginOfError && speed <= setPoint + marginOfError;
        return isOK1 && isOK2;
    }

    public final boolean isReadyDriver() {
        return isReadySpeed() && driverIsReady;
    }

    public final void driverReady(boolean val) {
        driverIsReady = val;
    }

    public final void setSetPoint(double _setPoint) {
        this.setPoint = _setPoint;
    }

    public final void runPID() {
        backspinPID.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    }
    
    public void periodic() {
        addDatapoint(lastData, backspinEncoder.getVelocity());
        averageValue = calculateAverage(lastData);
        SmartDashboard.putNumber("BackspinSpeed", backspinEncoder.getVelocity());
        SmartDashboard.putNumber("BackspinAverageSpeed", averageValue);
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
