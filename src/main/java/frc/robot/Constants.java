// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final int rightFrontPort = 23;
        public static final int rightBackPort = 26;
        public static final int leftFrontPort = 21;
        public static final int leftBackPort = 22;

    }

    public static final class ShootAngle {
        public static final int angleMotor = 2;
    }

    public static final class Shooter {
        public static final int shooterPort = 27;

        // parameters for PID controller
        public static final double kP = 5e-5;
        public static final double kI = 1e-6;
        public static final double kD = 0.0D;
        public static final double kFF = 0.000156;
        public static final double kMaxOutput = 1.0D;
        public static final double kMinOutput = -1.0D;
        public static final int kMaxVel = 6000; // rpm
        public static final int kMaxAcc = 1500;

    }

}
