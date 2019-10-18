/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	CANSparkMax frontLeftMotor;
	CANSparkMax frontRightMotor;
	CANSparkMax backLeftMotor;
	CANSparkMax backRightMotor;

	MecanumDrive mecanumDrive;

	public DriveSubsystem() {
		frontLeftMotor = new CANSparkMax(RobotMap.SPARK_FRONT_LEFT, MotorType.kBrushless);
		frontRightMotor = new CANSparkMax(RobotMap.SPARK_FRONT_RIGHT, MotorType.kBrushless);
		backLeftMotor = new CANSparkMax(RobotMap.SPARK_BACK_LEFT, MotorType.kBrushless);
		backRightMotor = new CANSparkMax(RobotMap.SPARK_BACK_RIGHT, MotorType.kBrushless);

		mecanumDrive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
	}

	public void mecanumDrive(double y, double x, double z) {
		mecanumDrive.driveCartesian(y, x, z);
	}

	public MecanumDrive getMecanumDriveObject() {
		return mecanumDrive;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
