package com.jwm.drive.domain.car;

interface TransmissionGear {
    void applyTorque(double torque);
	double getRatio();
    double getSpeed();
    double getLoad();
}
