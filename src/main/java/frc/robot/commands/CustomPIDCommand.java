/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class CustomPIDCommand extends Command {
	private double fl_t;
	private double fr_t;
	private double bl_t;
	private double br_t;

	private double fl_i;
	private double fr_i;
	private double bl_i;
	private double br_i;
	
	private double maxPower = 0.3; 

	private double lastFL = 0;
	private long lastTime = 0;

	public CustomPIDCommand(double fl, double fr, double bl, double br) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		this.fl_t = fl;
		this.fr_t = fr;
		this.bl_t = bl;
		this.br_t = br;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		fl_i = Robot.driveSubsystem.getFrontLeftEncoder();
		fr_i = Robot.driveSubsystem.getFrontRightEncoder();
		bl_i = Robot.driveSubsystem.getBackLeftEncoder();
		br_i = Robot.driveSubsystem.getBackRightEncoder(); 

		lastFL = fl_i;
		lastTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// double dx = Robot.driveSubsystem.getFrontLeftEncoder() - lastFL;
		double dt = ((double) (System.currentTimeMillis() - lastTime)) / 1000.0;

		double fl_p = calculatePower(Robot.driveSubsystem.getFrontLeftEncoder() - fl_i, fl_t, maxPower);
		
		
		// double fr_p = -calculatePower(-Robot.driveSubsystem.getFrontRightEncoder()+fr_i, -fr_t, maxPower);
		// double bl_p = -calculatePower(-Robot.driveSubsystem.getBackLeftEncoder()+bl_i, -bl_t, maxPower);
		// double br_p = calculatePower(Robot.driveSubsystem.getBackRightEncoder()-br_i, br_t, maxPower);

		

		// Robot.driveSubsystem.drive(fl_p, fr_p, bl_p, br_p);
		// Robot.driveSubsystem.drive(fl_p, 0,0,0);

		

		// double vel = 60*dx/dt;
		double vel = Robot.driveSubsystem.getFrontLeftVelocity();


		System.out.println(System.currentTimeMillis() + "," + vel + "," + Robot.driveSubsystem.getBackRightVelocity() + "," + Robot.driveSubsystem.getBackRightPower());
		
		Robot.driveSubsystem.setFL(fl_p);
		Robot.driveSubsystem.driveVelExceptFL(-vel, -vel, vel);

		lastFL = Robot.driveSubsystem.getFrontLeftEncoder();

		lastTime = System.currentTimeMillis();
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveSubsystem.getFrontLeftEncoder() > fl_i + fl_t;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveSubsystem.stop();
	}

	private double calculatePower(double x, double t, double m){
		double ff = 0.07;

		double ret =  Math.min(-(1.0/(5.0*t))*(x)*(x-t) + ff, m);
		if (ret > 1) {
			return 1;
		} else if (ret < -1) {
			return -1;
		} else {
			return ret;
		}
	}

	
}
