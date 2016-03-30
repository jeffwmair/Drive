package com.jwm.drive.domain.car;

public class WheelDriveImpl {

	private final double C_rollingResistance = 15;
	private double velocity, wheel_force, radius;

	public WheelDriveImpl(double wheelRadius) {
		this.radius = wheelRadius;
	}

	// @Override
	// public double getSlipPercent() {
	// return slipPct;
	// }

	public double getVelocity() {
		return velocity;
	}
	public double getWheelForce() {
		return wheel_force;
	}

	public void update(double massAtWheel, double brakeForce, double surfaceFriction, double accelerationForce,
			double steeringAngle) {

		/*
		 * Fnet = f_traction + f_brake + f_rolling_resistance + f_drag
		 */

		// we'll assume that the actual acceleration force is passed in here
		double f_traction = accelerationForce;
		double f_tractionMAX = surfaceFriction * massAtWheel * 9.8;

		// we'll assume that the actual braking force is passed in here
		double f_brake = brakeForce;

		// as velocity increases, so does rolling resistance
		double f_rollingResistance = -C_rollingResistance * velocity;

		// net force
		wheel_force = Math.min(f_tractionMAX, f_traction) + f_brake + f_rollingResistance;

		// Angular rotation: F = m.a.r -> a = F / m.r

		double angularAcceleration = wheel_force / (massAtWheel * this.radius);
//		double angularVelocity = 
		// double acceleration = f_net / massAtWheel;
		//
		// velocity = velocity + DomainUtils.update_time_in_sec * acceleration;

	}
}
