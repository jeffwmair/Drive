package drive.domain.interfaces;

public interface Wheel {
	void update(double massAtWheel, double brakeForce, double surfaceFriction, double accelerationForce, double steeringAngle);
	double getSlipPercent();
	double getVelocity();
}
