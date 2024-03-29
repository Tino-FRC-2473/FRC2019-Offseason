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
public class TeleopCommand extends Command {
	public TeleopCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double x = 0;
		double y = 0;

		double deadband = 0.1;

		if (Math.abs(Robot.oi.getJoystickX()) > deadband) {
			x = Robot.oi.getJoystickX();
			double sign = (x < 0) ? -1 : 1;
			x = Math.pow(Math.abs(x), 1.5);
			x *= sign;
		}

		if (Math.abs(Robot.oi.getJoystickY()) > deadband) {
			y = Robot.oi.getJoystickY();
			double sign = (y < 0) ? -1 : 1;
			y = Math.pow(Math.abs(y), 2);
			y *= sign;
		}

		Robot.driveSubsystem.mecanumDrive(-y, x, Robot.oi.getJoystickZ());
		// System.out.println(Robot.oi.getJoystickY() + " " + Robot.oi.getJoystickX() + " " + Robot.oi.getJoystickZ());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
