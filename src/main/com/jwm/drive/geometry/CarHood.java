package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Transition;

class CarHood extends Geometry {

	private boolean isOpen;

	public CarHood() {
		super("hood", "paint_white");
	}

	void open() {
		int hoodOpenSpeed = 100;
		transitionRotation(0, 50, Rotation.RotationDirection.endToEnd, hoodOpenSpeed, Transition.TransitionType.EASE_IN_OUT_SIN);
		isOpen = true;
	}

	void close() {
		int hoodCloseSpeed = 150;
		transitionRotation(50, 0, Rotation.RotationDirection.endToEnd, hoodCloseSpeed, Transition.TransitionType.EASE_IN_OUT_SIN);
		isOpen = false;
	}

	boolean isOpen() {
		return isOpen;
	}
}
