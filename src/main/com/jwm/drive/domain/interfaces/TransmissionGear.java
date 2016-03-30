package com.jwm.drive.domain.interfaces;

public interface TransmissionGear {
    void applyTorque(double torque);
	double getRatio();
    double getSpeed();
    double getLoad();
}
