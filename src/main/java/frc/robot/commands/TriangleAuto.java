/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class TriangleAuto extends Command {
	double dy; 
	double dx;
	double init;
	double rot;

	public TriangleAuto(double dy, double dx) {
		this.dy = dy; 
		this.dx = dx; 

		rot = 10;
		init = Robot.driveSubsystem.getFrontLeftEncoder();
		
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		double vel = 10;
		Robot.driveSubsystem.driveTicks(vel, -vel, -vel, vel);
		System.out.println("Called drivevelocity");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
		// return Math.abs(Robot.driveSubsystem.getFrontLeftEncoder() - rot) < 0.5;
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
}
