// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
// import com.revrobotics.ControlType;
// import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
// import edu.wpi.first.wpilibj.SpeedController;
// import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final CANSparkMax leftFront;
  private final CANSparkMax leftBack;
  private final CANSparkMax rightFront;
  private final CANSparkMax rightBack;
  private final DifferentialDrive myRobot;

  private final RelativeEncoder leftEncoder;
  private final RelativeEncoder rightEncoder;
  private final SparkMaxPIDController leftController;
  private final SparkMaxPIDController rightController;
  private final AHRS gyro = new AHRS();
  private final DifferentialDriveOdometry odometry;

  private Boolean forward;

  public DriveSubsystem() {
    this.leftFront = new CANSparkMax(Constants.Drive.leftFrontPort, MotorType.kBrushless);
    this.leftBack = new CANSparkMax(Constants.Drive.leftBackPort, MotorType.kBrushless);
    this.rightFront = new CANSparkMax(Constants.Drive.rightFrontPort, MotorType.kBrushless);
    this.rightBack = new CANSparkMax(Constants.Drive.rightBackPort, MotorType.kBrushless);
    this.leftFront.restoreFactoryDefaults();
    this.leftBack.restoreFactoryDefaults();
    this.rightFront.restoreFactoryDefaults();
    this.rightBack.restoreFactoryDefaults();
    this.leftBack.follow(this.leftFront);
    this.rightBack.follow(this.rightFront);
    this.myRobot = new DifferentialDrive(this.leftFront, this.rightFront);
    this.forward = true;

    this.leftEncoder = this.leftFront.getEncoder();
    this.rightEncoder = this.rightFront.getEncoder();
    this.leftController = this.leftFront.getPIDController();
    this.rightController = this.rightFront.getPIDController();

    this.leftController.setP(Constants.Drive.kP);
    this.rightController.setP(Constants.Drive.kP);
    this.leftController.setD(Constants.Drive.kD);
    this.rightController.setD(Constants.Drive.kD);
    this.leftController.setFF(Constants.Drive.kFF);
    this.rightController.setFF(Constants.Drive.kFF);

    this.odometry = new DifferentialDriveOdometry(gyro.getRotation2d().times(-1.0));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void tankDrive(double leftPower, double rightPower) {
    if (forward) {
      this.myRobot.tankDrive(-leftPower, rightPower, true);
    } else {
      this.myRobot.tankDrive(rightPower, -leftPower, true);
    }
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftFront.setVoltage(leftVolts);
    leftBack.setVoltage(leftVolts);
    rightFront.setVoltage(rightVolts);
    rightBack.setVoltage(rightVolts);
  }

  public void invertDrivetrain() {
    forward = !forward;
  }

  public void toggleIdle() {
    if (leftFront.getIdleMode() == IdleMode.kBrake) {
       leftFront.setIdleMode(IdleMode.kCoast);
       leftBack.setIdleMode(IdleMode.kCoast);
       rightFront.setIdleMode(IdleMode.kCoast);
       rightBack.setIdleMode(IdleMode.kCoast);
    } else {
       leftFront.setIdleMode(IdleMode.kBrake);
       leftBack.setIdleMode(IdleMode.kBrake);
       rightFront.setIdleMode(IdleMode.kBrake);
       rightBack.setIdleMode(IdleMode.kBrake);
    }
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public Translation2d getTranslation() {
    return odometry.getPoseMeters().getTranslation();
  }

 // returns the wheel speeds
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    gyro.reset();
    // gyro.setAngleAdjustment(adjustment);
    odometry.resetPosition(pose, gyro.getRotation2d());
    System.out.println("Resetting Odometry: " + odometry.getPoseMeters());
  }
  public void flipOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(getPose(), gyro.getRotation2d().plus(new Rotation2d(3.14159268)));
    System.out.println("Offsetting Odometry: " + odometry.getPoseMeters());
  }

  public void resetOdo() {
    resetOdometry(new Pose2d(0.0, 0.0, new Rotation2d(0.0)));
  }

  public void zeroHeading() {
    gyro.reset();
  }

  public void resetEncoders() {
    leftEncoder.setPosition(0.0);
    rightEncoder.setPosition(0.0);
  }

  public double rightEncoderPos() {
    return rightEncoder.getPosition();
  }
  public double leftEncoderPos() {
    return leftEncoder.getPosition();
  }
  public void setWheelVelocity(double left, double right) {
    // System.out.println("left: " + left + "right" + right);
    forward = true;
    leftController.setReference(left, CANSparkMax.ControlType.kVelocity);
    rightController.setReference(-right, CANSparkMax.ControlType.kVelocity);
  }
  public void setReverseWheelVelocity(double left, double right) {
    // System.out.println("left: " + left + "right" + right);
    forward = false;
    leftController.setReference(-right, CANSparkMax.ControlType.kVelocity);
    rightController.setReference(left, CANSparkMax.ControlType.kVelocity);
  }
  public double getHeading() {
    return gyro.getRotation2d().times(-1.0).getDegrees();
  }

  public double getTurnRate() {
    return gyro.getRate();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
