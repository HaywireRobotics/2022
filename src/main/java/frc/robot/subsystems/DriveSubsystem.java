// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
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

  public Boolean inverted;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void tankDrive(double leftPower, double rightPower) {
    // this.myRobot.tankDrive(-leftPower, rightPower, true);

    if (!inverted) {
      this.myRobot.tankDrive(-leftPower, rightPower, true);
    } else {
      this.myRobot.tankDrive(rightPower, -leftPower, true);
    }
  }

  public void invertDrivetrain() {
    inverted = !inverted;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

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
    //this.myRobot = new DifferentialDrive(this.leftBack, this.rightBack);
    this.inverted = false;
  }
}
