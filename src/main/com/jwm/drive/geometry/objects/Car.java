package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// todo: see above making Car package-private; just use public factories

public class Car extends Geometry {

	private static Logger log = LogManager.getLogger(Car.class);

	private double SPEED_TO_GEO_SPACE_FRAME = 0.005; // verified Nov 17 2014
	private static final double FRONT_WHEEL_MAX_TURN_ANGLE = 35.0;
	private final float TIRE_FRONT_TRANSLATE = -2.85f;
	private final float TIRE_HORIZONTAL_TRANSLATE = 0.6f;
	private CarHood hood = null;
	private Geometry frame = null;
	private List<CarTire> allTires;
	private List<CarTire> frontTires;
	private Move movement;
	private double speed;
	private final double MAX_SPEED = 150;
	private final double ACCELERATION = 0.25;
	private final double DECELERATION = -1;
	private final double COAST_DECELERATION = -0.1;
	private final double MAX_BODY_ROLL_ANGLE = 3;

	/**
	 * Issues: body roll - frame is rotation along the right side, not in the
	 * middle shortcut to align the camera with the rear of the car; otherwise,
	 * allow to stay in one orientation as long as the car is in view?
	 * 
	 */

	public Car() {
		super();

		if (log.isDebugEnabled()) {
			log.debug("New car");
		}

		frame = new Geometry("frame", "frame");
		children.add(frame);
		Geometry body = new Geometry("body", "paint_white");
		frame.addChild(body);
		body.addChild(new Geometry("vinylroof", "vinylroof"));
		hood = new CarHood();
		body.addChild(hood);
//		body.addChild(new Geometry("door", "paint"));
        body.addChild(new Geometry("red_plastic", "red_plastic"));
//		body.addChild(new CarTrunk());
		body.addChild(new Geometry("rearwindow", "rearwindow"));
		body.addChild(new Geometry("chrome", "chrome"));
		body.addChild(new Geometry("wheelwells", "wheelwells"));
		putWheelsOnCar();
		movement = Move.COASTING;

		initCamera();
	}
	public void setSpeed(double speedKmph) {
		this.speed = speedKmph;
	}
	private String getSpeed() {
		DecimalFormat format = new DecimalFormat("0");
		return format.format(speed);
	}
	private double getMinTurnRadius() {
		double normalMinRadius = 0.1; // is this 100m ?
		double maxSpeedBeforeGripLoss = 20;
		double minTurnRadius = 0;
		if (speed < maxSpeedBeforeGripLoss) {
			minTurnRadius = normalMinRadius;
		} else {
			// once the car is going too fast for the tires to grip, the minimum
			// turn radius is reduced
			double momentumRadiusAdjustment = Math.pow(speed, 2) - maxSpeedBeforeGripLoss;
			minTurnRadius = normalMinRadius + momentumRadiusAdjustment;
		}

		return minTurnRadius;
	}
	private double getAngleOnTurnCircle(double radius, double velocity) {
		double distance = velocity * SPEED_TO_GEO_SPACE_FRAME;
		double circleCirumference = 2 * Math.PI * radius;
		double distanceOfCirclePct = distance / circleCirumference;
		double angleTravelledAlongCircle = 360 * distanceOfCirclePct;
		double carTurnAngle = 90 - angleTravelledAlongCircle;
		return carTurnAngle;
	}
	private double getCarTurnAngle() {
		/*
		 * if the wheel angle is less than what would cause us to bump into the
		 * minimum turn radius, then we just set the new car angle using the
		 * wheel angle.
		 * 
		 * Otherwise, the car turn angle must not be greater than what will go
		 * along the min turn radius circle.
		 */

		// found this with trial and error. this defines the car turning radius
		double rotationFactor = 0.6;
		double targetTurnAngle = getWheelAngle() * rotationFactor;
		double turnCircleMinRadius = getMinTurnRadius();
		double maxTurnAngle = getAngleOnTurnCircle(turnCircleMinRadius, speed);

		if (Math.abs(targetTurnAngle) > Math.abs(maxTurnAngle)) {
			System.out.println("maxTurnAngle");
			return maxTurnAngle;
		} else {
			return targetTurnAngle * speed * 0.004;
		}
	}
	public void setMovement(Move movement) {
		this.movement = movement;
		Rotation r = frame.getRotation(RotationDirection.endToEnd);
		if (movement == Move.ACCELERATING) {

		} else if (movement == Move.DECELERATING) {
			// r.setAngle(-2);
		} else {
			// r.setAngle(0);
		}
	}
	public void setFrontWheelAngle(double percent) {
		for (CarTire frontTire : frontTires) {
			frontTire.setWheelAngleInPct(percent);
		}
	}
	private void setBodyRollAngle(double angle) {
		frame.setRotation(angle, RotationDirection.leftAndRight);
	}
	private void resetForwardBackLeanAngle() {
		frame.setRotation(0, RotationDirection.endToEnd);
	}
	private void setOverheadRotationAngle(double angle) {
		getRotation(RotationDirection.overhead).setAngle(angle);
	}
	public void toggleHoodOpenClose() {
		if (hood.isOpen()) {
			closeHood();
		} else {
			openHood();
		}
	}
	private void openHood() {
		hood.open();
	}
	private void closeHood() {
		hood.close();
	}
	protected void applyLogic() {

		process(movement);
		double overheadAngle = getCarAngle();
		setOverheadRotationAngle(overheadAngle);
		speed = getCarSpeed();
		setWheelSpeed(speed);

		List<String> stats = new ArrayList<>();
		stats.add("Movement:" + this.toString());
		stats.add(getSpeed() + " km/h");
		stats.add("Wheel angle:" + (int) getWheelAngle());
		stats.add("Overhead angle: " + (int) overheadAngle);
		stats.add("Min turn radius: " + (int) getMinTurnRadius());
		writeInfoToListeners(stats);
	}
	private void setWheelSpeed(double speed) {
		for (CarTire tire : allTires) {
			tire.setWheelSpeed(speed);
		}
	}
	private void putWheelsOnCar() {

		CarTire tireFrontLeft;
		CarTire tireFrontRight;
		CarTire tireRearLeft;
		CarTire tireRearRight;

		tireFrontLeft = new CarTire();
		tireFrontRight = new CarTire();
		tireRearLeft = new CarTire();
		tireRearRight = new CarTire();

		tireFrontLeft.setOverallTranslation(TIRE_HORIZONTAL_TRANSLATE, 0, TIRE_FRONT_TRANSLATE);
		tireFrontRight.setOverallTranslation(-TIRE_HORIZONTAL_TRANSLATE, 0, TIRE_FRONT_TRANSLATE);
		tireRearLeft.setOverallTranslation(TIRE_HORIZONTAL_TRANSLATE, 0, 0);
		tireRearRight.setOverallTranslation(-TIRE_HORIZONTAL_TRANSLATE, 0, 0);

		children.add(tireFrontLeft);
		children.add(tireFrontRight);
		children.add(tireRearLeft);
		children.add(tireRearRight);

		allTires = new ArrayList<>();
		allTires.add(tireFrontLeft);
		allTires.add(tireFrontRight);
		allTires.add(tireRearLeft);
		allTires.add(tireRearRight);

		frontTires = new ArrayList<>();
		frontTires.add(tireFrontLeft);
		frontTires.add(tireFrontRight);
	}
	private double getWheelAngle() {
		return frontTires.get(0).getTurnAngle();
	}

		private static int moveCounter;

	public enum Move {
		COASTING, ACCELERATING, DECELERATING, STEADY
	}

	private double carRotationAngle;
	private double currentSpeed;

	private void process(Move type) {
		adjustSpeed(type);
		adjustPosition(type);
	}
	private double getCarAngle() {
		return carRotationAngle;
	}
	private double getCarSpeed() {
		return currentSpeed;
	}

	private void adjustSpeed(Move type) {
		switch (type) {
		case ACCELERATING:
			if (this.currentSpeed >= MAX_SPEED)
				return;
			currentSpeed = currentSpeed + ACCELERATION;
			break;
		case COASTING:
			resetForwardBackLeanAngle();
			if (currentSpeed <= 0) {
				currentSpeed = 0;
				return;
			}
			currentSpeed = currentSpeed + COAST_DECELERATION;
			break;
		case DECELERATING:
			if (this.currentSpeed <= 0)
				return;
			currentSpeed = currentSpeed + DECELERATION;
			break;
		case STEADY:
			break;
		}
	}

	private void adjustPosition(Move type) {

		double speedInGeometrySpace = currentSpeed * SPEED_TO_GEO_SPACE_FRAME;
		double wheelTurnAngle = getWheelAngle();
		double attemptedTurnAmount = getCarTurnAngle();

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
		increaseTranslation(xMovement, yMovement, zMovement);

		carRotationAngle = newCarAngle;
		currentSpeed = currentSpeed * wheelTurnSpeedAttenuation;
		moveCounter++;
		if (moveCounter % 50 == 0) {
			// System.out.println("Speed:" + currentSpeed);
		}
	}

	private void setBodyRoll(double speed, double turnAngle) {
		int direction = (turnAngle > 0) ? 1 : -1;
		double fudgeFactor = 0.005;
		double bodyRollAngle = speed * turnAngle * fudgeFactor;
		if (Math.abs(bodyRollAngle) > MAX_BODY_ROLL_ANGLE) {
			bodyRollAngle = direction * MAX_BODY_ROLL_ANGLE;
		}
		setBodyRollAngle(-bodyRollAngle);
	}
}
