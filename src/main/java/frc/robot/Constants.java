// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // public static final class Joysticks{
    //     public static final Joystick driverRightStick = new Joystick(1);
    //     public static final Joystick driverLeftStick = new Joystick(0);
    //     public static final Joystick manipulatorRightStick = new Joystick(2);
    //     public static final Joystick manipulatorLeftStick = new Joystick(3);
    // }

    public static final class Drive {
        public static final int rightFrontPort = 26;
        public static final int rightBackPort = 23;
        public static final int leftFrontPort = 21;
        public static final int leftBackPort = 22;

        // update these bois later
        // it says meters but is kinda sorta inches but not really lol
        // public static final double ksVolts = 0.15846;
        // public static final double kvVoltsSecondsPerMeter = 0.073738;
        // public static final double kaVoltSecondsSquarePerMeter = 0.013564;
        public static final double ksVolts = 0.173;
        public static final double kvVoltsSecondsPerMeter = 2.78;
        public static final double kaVoltSecondsSquarePerMeter = 0.509;
        public static final double kP = 2.5e-9;
        // public static final double kP = 6e-5;
        public static final double kD = 0.0D;
        public static final double kFF = 0.000015*10*10*0.15;        
        // public static final double kFF = 0.000015;        
        // public static final double kTrackwidthMeters = .5842; // check here if circlessss
        public static final double kTrackwidthMeters = .5842/7.5; // check here if circlessss
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
        public static final double kMaxSpeedMetersPerSecond = 1.5*33;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0.5*33;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        // public static final double kEncoderDistancePerPulse = (0.478 / 0.342) * 1.2*0.85694 * 1.125;   
        // public static final double kEncoderDistancePerPulse = (18.85 / 42) * (5/3) ;
        public static final double kEncoderDistancePerPulse = 5.0 * (14.0/40.0);
    }


    public static final class Shooter {
        public static final int shooterPort = 27;

        // parameters for PID controller
        public static final double kP = 6.0e-5;
        public static final double kI = 5.5e-7;
        public static final double kD = 0.0D;
        public static final double kIZone = 0.0D;
        public static final double kFF = 0.000018;
        public static final double kMaxOutput = 1.0D;
        public static final double kMinOutput = -1.0D;
        public static final int kMaxVel = 6000; // rpm, but neos can only go ~5750
        public static final int kMaxAcc = 2000;

    }

    public static final class Backspin {
        public static final int backspinPort = 1;
        public static final double kBackspin = (88/47) * 1.3;

        // parameters for PID controller
        public static final double kP = 6.0e-5;
        public static final double kI = 5.5e-7;
        public static final double kD = 0.0D;
        public static final double kIZone = 0.0D;
        public static final double kFF = 0.000018;
        public static final double kMaxOutput = 1.0D;
        public static final double kMinOutput = -1.0D;
        public static final int kMaxVel = 10500; // rpm, neo minis can only go 11000
        public static final int kMaxAcc = 2000;
    }
    public static final class Intake {
        public static final int armPort = 25;
        public static final int intakePort = 20; // neo mini
        // PID values for arm position feedback
        public static final double kP = 0.1D;
        public static final double kI = 1e-4;
        public static final double kD = 1;
        public static final double kIZone = 0;
        public static final double kFF = 0;
        public static final double kMaxOutput = .01D;
        public static final double kMinOutput = -.01D;
        public static final double maxAngle = 0.3;
        public static final double minAngle = -11.4;
    }

    public static final class Index {
        public static final int indexPort = 24;
    }

    public static final class Climber {
        public static final int rightWinchPort = 30;
        public static final int leftWinchPort = 31;
        public static final int babyHooksPort = 17;
    }

}
