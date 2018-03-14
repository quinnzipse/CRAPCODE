/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6758.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {
/*	Spark frontLeft = new Spark(0);
	Spark frontRight = new Spark(1);
	Spark backLeft = new Spark(2);
	Spark backRight = new Spark(3);*/
	
	WPI_TalonSRX frontRight = new WPI_TalonSRX(0);
	WPI_TalonSRX backRight = new WPI_TalonSRX(1);
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(2);
	WPI_TalonSRX backLeft = new WPI_TalonSRX(3);
	
	public SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);
	public SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);
	
	DifferentialDrive driveTrain = new DifferentialDrive(left, right);
	Joystick stick = new Joystick(0);
	Compressor compressor = new Compressor();
	Solenoid solenoid = new Solenoid(0);
	
	private boolean safe = true;
	private double mainSpeed = .8, twistSpeed = .65;
	
	@Override
	public void robotInit() {
		compressor.setClosedLoopControl(true);
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {
		driveTrain.arcadeDrive(stick.getY()*mainSpeed, stick.getTwist()*twistSpeed);
		
		RobotController.getUserButton();
		
		if(stick.getRawButtonPressed(2)) {
			if(safe) safe = false;
			else safe = true;
		}
		
		solenoid.set(stick.getTrigger() && safe);
	}

	@Override
	public void testPeriodic() {
	}
}
