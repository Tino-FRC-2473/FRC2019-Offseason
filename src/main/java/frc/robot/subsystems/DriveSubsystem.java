/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
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

	// MecanumDrive mecanumDrive;

	public DriveSubsystem() {
		frontLeftMotor = new CANSparkMax(RobotMap.SPARK_FRONT_LEFT, MotorType.kBrushless);
		frontRightMotor = new CANSparkMax(RobotMap.SPARK_FRONT_RIGHT, MotorType.kBrushless);
		backLeftMotor = new CANSparkMax(RobotMap.SPARK_BACK_LEFT, MotorType.kBrushless);
		backRightMotor = new CANSparkMax(RobotMap.SPARK_BACK_RIGHT, MotorType.kBrushless);
		
		

		frontRightMotor.setInverted(true);
		backRightMotor.setInverted(true);

		resetEncoder(frontLeftMotor);
		resetEncoder(frontRightMotor);
		resetEncoder(backLeftMotor);
		resetEncoder(backRightMotor);
		
		initPID(frontLeftMotor);
		initPID(frontRightMotor);
		initPID(backLeftMotor);
		initPID(backRightMotor);

		// mecanumDrive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
	}

	public void drive(double fl, double fr, double bl, double br){
		frontLeftMotor.set(fl);
		frontRightMotor.set(fr); 
		backLeftMotor.set(bl);
		backRightMotor.set(br);

	}

	public void setFL(double fl) {
		frontLeftMotor.set(fl);
	}

	public void mecanumDrive(double y, double x, double z) {
		// mecanumDrive.driveCartesian(y, x, z);
		z *= 0.3;

		double flPower = y + x + z;
		double frPower = y - x - z;
		double blPower = y - x + z;
		double brPower = y + 0.95 * x - z;

		double absMax = Math.max(Math.max(Math.abs(flPower), Math.abs(frPower)), 
								 Math.max(Math.abs(blPower), Math.abs(brPower)));

		if (absMax > 1) {
			flPower /= absMax;
			frPower /= absMax;
			blPower /= absMax;
			brPower /= absMax;
		}

		frontLeftMotor.set( 1.00 * flPower);
		frontRightMotor.set(1.00 * frPower);
		backLeftMotor.set(  1.00 * blPower);
		backRightMotor.set( 1.00 * brPower);
	}

	public void stop() {
		frontLeftMotor.set(0);
		frontRightMotor.set(0);
		backLeftMotor.set(0);
		backRightMotor.set(0);
	}

	// public MecanumDrive getMecanumDriveObject() {
	// 	return mecanumDrive;
	// }

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void driveTicks(double fl, double fr, double bl, double br) {
		double fl_current = frontLeftMotor.getEncoder().getPosition();
		double fr_current = frontRightMotor.getEncoder().getPosition();
		double bl_current = backLeftMotor.getEncoder().getPosition();
		double br_current = backRightMotor.getEncoder().getPosition();

		// System.out.println("About to set references");
		frontLeftMotor.getPIDController().setReference(fl_current + fl, ControlType.kPosition); 
		frontRightMotor.getPIDController().setReference(fr_current + fr, ControlType.kPosition); 
		backLeftMotor.getPIDController().setReference(bl_current + bl, ControlType.kPosition); 
		backRightMotor.getPIDController().setReference(br_current + br, ControlType.kPosition); 
		// System.out.println("Finished setting references");
	}

	public void driveVelExceptFL(double fr, double bl, double br) {
		// double fr_current = frontRightMotor.getEncoder().getPosition();
		// double bl_current = backLeftMotor.getEncoder().getPosition();
		// double br_current = backRightMotor.getEncoder().getPosition();

		// System.out.println("About to set references");
		frontRightMotor.getPIDController().setReference(fr, ControlType.kVelocity); 
		backLeftMotor.getPIDController().setReference(bl, ControlType.kVelocity); 
		backRightMotor.getPIDController().setReference(br, ControlType.kVelocity); 
		// System.out.println("Finished setting references");
	}

	public void driveVelocity(double fl, double fr, double bl, double br) {
		frontLeftMotor.getPIDController().setReference(fl, ControlType.kVelocity); 
		frontRightMotor.getPIDController().setReference(fr, ControlType.kVelocity); 
		backLeftMotor.getPIDController().setReference(bl, ControlType.kVelocity); 
		backRightMotor.getPIDController().setReference(br, ControlType.kVelocity); 
	}

	private void resetEncoder(CANSparkMax motor) {
		motor.getEncoder().setPosition(0);
	}

	public double getFrontLeftEncoder() {
		return frontLeftMotor.getEncoder().getPosition();
	}

	public double getFrontLeftVelocity() {
		return frontLeftMotor.getEncoder().getVelocity();
	}

	public double getFrontRightEncoder() {
		return frontRightMotor.getEncoder().getPosition();
	}

	public double getBackLeftEncoder() {
		return backLeftMotor.getEncoder().getPosition();
	}

	public double getBackRightEncoder() {
		return backRightMotor.getEncoder().getPosition();
	}

	public double getBackRightVelocity() {
		return backRightMotor.getEncoder().getVelocity();
	}

	public double getBackRightPower() {
		return backRightMotor.getAppliedOutput();
	}

	private void initPID(CANSparkMax motor) {
		// double p = 0.1;
		// double i = 0;
		// double d = 0;
		// double max = 0.3;

		double ff = 0;

		double p = 5e-3;
		double i = 0;
		double d = 0;
		double max = 0.3;

		motor.getPIDController().setP(p);
		motor.getPIDController().setI(i);
		motor.getPIDController().setD(d);
		motor.getPIDController().setOutputRange(-max, max);

		motor.getPIDController().setFF(ff);
	}

	public void printPowers() {
		double fl_power = frontLeftMotor.get();
		double fr_power = frontRightMotor.get();
		double bl_power = backLeftMotor.get();
		double br_power = backRightMotor.get();

		System.out.printf("%f,%f,%f,%f\n", fl_power, fr_power, bl_power, br_power);
	}
}
