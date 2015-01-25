package drive.geometry.objects;

import drive.geometry.Geometry;

import org.omg.CORBA.PRIVATE_MEMBER;

import drive.geometry.Rotation;
import drive.geometry.Rotation.RotationDirection;

public class CarTire extends Geometry {
	private double spinSpeedInKmph;
	private final double FRONT_WHEEL_MAX_TURN_ANGLE = 35.0;
	private Rotation tireSpinRotation, tireTurnAngle;

	public CarTire() {
		super();
		loadRootPart("tire", "tire");
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
			Marker m = new Marker();
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
		double angle = FRONT_WHEEL_MAX_TURN_ANGLE * percent;
		tireTurnAngle.setAngle(-angle);
	}
	private final double SPEED_CONVERSION = 0.65;
	protected void applyLogic() {
		tireSpinRotation.increaseAngle(-spinSpeedInKmph*SPEED_CONVERSION);
	}
}
