package com.jwm.drive.domain.car;

import com.jwm.drive.geometry.objects.Car;

public class CarMovement {

	public CarMovement(Car c) {
		this.car = c;
	}

	private static int moveCounter;

	public enum Move {
		COASTING, ACCELERATING, DECELERATING, STEADY
	}

	protected double carRotationAngle, currentSpeed, momentumAngle;

	public void process(Move type) {
		adjustSpeed(type);
		adjustPosition(type);
	}
	public double getCarAngle() {
		return carRotationAngle;
	}
	public double getCarSpeed() {
		return currentSpeed;
	}

	protected Car car;

	protected void adjustSpeed(Move type) {
		switch (type) {
		case ACCELERATING:
			if (this.currentSpeed >= car.MAX_SPEED)
				return;
			currentSpeed = currentSpeed + car.ACCELERATION;
			break;
		case COASTING:
			car.setForwardBackLeanAngle(0);
			if (currentSpeed <= 0) {
				currentSpeed = 0;
				return;
			}
			currentSpeed = currentSpeed + car.COAST_DECELERATION;
			break;
		case DECELERATING:
			if (this.currentSpeed <= 0)
				return;
			currentSpeed = currentSpeed + car.DECELERATION;
			break;
		case STEADY:
			break;
		}
	}

	private void adjustPosition(Move type) {

		double speedInGeometrySpace = currentSpeed * car.SPEED_TO_GEO_SPACE_FRAME;
		double wheelTurnAngle = car.getWheelAngle();
		double attemptedTurnAmount = car.getCarTurnAngle();

		/* sudden jumps in the wheel angle should slow the car down */
		// turning the wheel slows the car down too
		double wheelTurnSpeedAttenuation = 1;
		if (currentSpeed > 15) {
			double WHEEL_TURN_SPEED_ATTENUATION_FACTOR = 0.01;
			wheelTurnSpeedAttenuation = 1 - Math.abs(WHEEL_TURN_SPEED_ATTENUATION_FACTOR * wheelTurnAngle
					/ Car.FRONT_WHEEL_MAX_TURN_ANGLE);

		}
		double newCarAngle = carRotationAngle + attemptedTurnAmount;

		/* this should help to keep things simple */
		newCarAngle = newCarAngle % 360;
		if (newCarAngle < 0) {
			newCarAngle = 360.0 - newCarAngle;
		}

		setBodyRoll(currentSpeed, wheelTurnAngle);

		double xMovement = -speedInGeometrySpace * Math.sin(Math.toRadians(newCarAngle));
		double yMovement = 0; // car doesn't go up and down (yet!)
		double zMovement = -speedInGeometrySpace * Math.cos(Math.toRadians(newCarAngle));
		car.increaseTranslation(xMovement, yMovement, zMovement);

		carRotationAngle = newCarAngle;
		currentSpeed = currentSpeed * wheelTurnSpeedAttenuation;
		moveCounter++;
		if (moveCounter % 50 == 0) {
			// System.out.println("Speed:" + currentSpeed);
		}
	}

	private void setBodyRoll(double speed, double turnAngle) {
		int direction = (turnAngle > 0) ? 1 : -1;
		double fudgeFactor = 0.01;
		double bodyRollAngle = speed * turnAngle * fudgeFactor;
		if (Math.abs(bodyRollAngle) > car.MAX_BODY_ROLL_ANGLE) {
			bodyRollAngle = direction * car.MAX_BODY_ROLL_ANGLE;
		}
		car.setBodyRollAngle(-bodyRollAngle);
	}
}
