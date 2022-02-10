// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// //import frc.robot.Constants.ShootAngle;
// import frc.robot.subsystems.ShootAngleSubsystem;

// public class AdjustShootAngle extends CommandBase {
//   /** Creates a new AdjustShootAngle. */

//   private final ShootAngleSubsystem m_subsystem;
//   private final Joystick leftWhoop;
//   /**
//   @param subsystem
//   */
//   public AdjustShootAngle(ShootAngleSubsystem subsystem, Joystick _leftWhoop) {
//     // Use addRequirements() here to declare subsystem dependencies.
//     leftWhoop = _leftWhoop;
//     m_subsystem = subsystem;

//     //addRequirements(subsystem);
    
//   }

//   // Called when the command is initially scheduled.
//   // @Override
//   // public void initialize() {}

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     int angle = 0;
//     boolean up = this.leftWhoop.getRawButton(4);
//     boolean down = this.leftWhoop.getRawButton(5);
//     if (up == true){
//       angle = 1;
//     }else{
//       angle = 0;
//     }

//     if (down == true){
//       angle = -1;
//     }else{
//       angle = 0;
//     }

    

//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {}

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }
