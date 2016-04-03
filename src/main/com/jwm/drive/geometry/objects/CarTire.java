package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;

public class CarTire extends Geometry {
	private double spinSpeedInKmph;
	final private Rotation tireSpinRotation, tireTurnAngle;

	public CarTire() {
		super("tire", "tire");
		setOffsetFromOrigin(0, 0, 0);
		tireTurnAngle = getRotation(RotationDirection.overhead);
		tireTurnAngle.setAngle(0);
		tireSpinRotation = getRotation(RotationDirection.endToEnd);
		addMarkers();
	}
	public double getTurnAngle() {
		return getRotation(RotationDirection.overhead).getAngle();
	}
	private void addMarkers() {
		for (int i = 0; i < 36; i++) {
			Geometry m = new Geometry("squaremarker", "squaremarker");
			m.getPostTranslateRotation().setValues(20 * i, 1, 0, 0);
			double xTrans = -0.679;
			if (i >= 18) {
				xTrans = -0.89;
			}
			m.setOverallTranslation(xTrans, 0.22, 0);
			addChild(m);
		}
	}
	public void setWheelSpeed(double kmph) {
		spinSpeedInKmph = kmph;
	}
	public void setWheelAngleInPct(double percent) {
		double FRONT_WHEEL_MAX_TURN_ANGLE = 35.0;
		double angle = FRONT_WHEEL_MAX_TURN_ANGLE * percent;
		tireTurnAngle.setAngle(-angle);
	}
	protected void applyLogic() {
		double SPEED_CONVERSION = 0.65;
		tireSpinRotation.increaseAngle(-spinSpeedInKmph*SPEED_CONVERSION);
	}
}
