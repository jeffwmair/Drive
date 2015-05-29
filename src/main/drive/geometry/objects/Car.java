package drive.geometry.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;
import com.jwm.j3dfw.geometry.Transition.TransitionType;

import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.TargetCamera;

import drive.domain.car.CarMovement;
import drive.domain.car.CarMovement.Move;

public class Car extends Geometry {

	private static Logger log = LogManager.getLogger(Car.class);

	public double SPEED_TO_GEO_SPACE_FRAME = 0.005; // verified Nov 17 2014
	public static final double FRONT_WHEEL_MAX_TURN_ANGLE = 35.0;
	final float TIRE_FRONT_TRANSLATE = -2.85f;
	final float TIRE_HORIZONTAL_TRANSLATE = 0.6f;
	private CarHood hood = null;
	private CarFrame frame = null;
	private CarTire tireFrontLeft;
	private CarTire tireFrontRight;
	private CarTire tireRearLeft;
	private CarTire tireRearRight;
	private List<CarTire> allTires;
	private List<CarTire> frontTires;
	private boolean hoodIsOpen = false;
	private Move movement;
	private CarMovement carMovement;
	private double speed;
	public final double MAX_SPEED = 150;
	public final double ACCELERATION = 0.25;
	public final double DECELERATION = -1;
	public final double COAST_DECELERATION = -0.1;
	public final double MAX_BODY_ROLL_ANGLE = 3;

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

		frame = new CarFrame();
		children.add(frame);
		CarBody body = new CarBody();
		frame.addChild(body);
		body.addChild(new CarRoof());
		hood = new CarHood();
		body.addChild(hood);
//		body.addChild(new CarDoors());
        body.addChild(new TailLights());
//		body.addChild(new CarTrunk());
		body.addChild(new CarRearWindow());
		body.addChild(new CarChrome());
		body.addChild(new CarWheelWells());
		putWheelsOnCar();
		movement = Move.COASTING;
		carMovement = new CarMovement(this);
		initCamera();
		TargetCamera cam = getCamera();
	}
	public void setSpeed(double speedKmph) {
		this.speed = speedKmph;
	}
	public String getSpeed() {
		DecimalFormat format = new DecimalFormat("0");
		return format.format(speed);
	}
	public double getMinTurnRadius() {
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
	public double getCarTurnAngle() {
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
	public void setBodyRollAngle(double angle) {
		frame.setRotation(angle, RotationDirection.leftAndRight);
	}
	public void setForwardBackLeanAngle(double angle) {
		frame.setRotation(angle, RotationDirection.endToEnd);
	}
	private void setOverheadRotationAngle(double angle) {
		getRotation(RotationDirection.overhead).setAngle(angle);
	}
	public void toggleHoodOpenClose() {
		if (hoodIsOpen) {
			closeHood();
		} else {
			openHood();
		}
	}
	private void openHood() {
		int hoodOpenSpeed = 100;
		hood.transitionRotation(0, 50, RotationDirection.endToEnd, hoodOpenSpeed, TransitionType.EASE_IN_OUT_SIN);
		hoodIsOpen = true;
	}
	private void closeHood() {
		int hoodCloseSpeed = 150;
		hood.transitionRotation(50, 0, RotationDirection.endToEnd, hoodCloseSpeed, TransitionType.EASE_IN_OUT_SIN);
		hoodIsOpen = false;
	}
	protected void applyLogic() {
		carMovement.process(movement);

		double overheadAngle = carMovement.getCarAngle();
		setOverheadRotationAngle(overheadAngle);
		speed = carMovement.getCarSpeed();
		setWheelSpeed(speed);

		List<String> stats = new ArrayList<String>();
		stats.add("Movement:" + carMovement.toString());
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

		allTires = new ArrayList<CarTire>();
		allTires.add(tireFrontLeft);
		allTires.add(tireFrontRight);
		allTires.add(tireRearLeft);
		allTires.add(tireRearRight);

		frontTires = new ArrayList<CarTire>();
		frontTires.add(tireFrontLeft);
		frontTires.add(tireFrontRight);
	}
	public double getWheelAngle() {
		return frontTires.get(0).getTurnAngle();
	}
}
