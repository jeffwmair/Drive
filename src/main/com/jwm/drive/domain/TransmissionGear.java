package com.jwm.drive.domain;

interface TransmissionGear {
    void applyTorque(double torque);
	double getRatio();
    double getSpeed();
    double getLoad();
}
