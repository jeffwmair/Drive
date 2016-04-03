package com.jwm.drive.controller;

import com.jwm.drive.geometry.Car;
import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;

class CarControllerService extends ControllerService {

	@Override
	public void leftMouseDown(Geometry geometry) {
		Car c = (Car)geometry;
		c.setMovement(Car.Move.ACCELERATING);
	}
	@Override
	public void rightMouseDown(Geometry geometry) {
		Car c = (Car)geometry;
		c.setMovement(Car.Move.DECELERATING);
	}
	@Override
	public void leftMouseUp(Geometry geometry) {
		Car c = (Car)geometry;
		c.setMovement(Car.Move.COASTING);
	}
	@Override
	public void rightMouseUp(Geometry geometry) {
		Car c = (Car)geometry;
		c.setMovement(Car.Move.COASTING);
	}
	@Override
	public void keyPress(Geometry geometry, int keyCode) {
		Car c = (Car)geometry;
		if (keyCode == 49) {
			c.toggleHoodOpenClose();
		}
		else if ((char)keyCode == 'r') {
			c.getCamera().toggleAutoRotate();
		}
		else if ((char)keyCode == 't') {
			c.getCamera().toggleAutoTrack();
		}
	}
	@Override
	public void setMousePosition(Geometry geometry, double xPos, double percent) {
		Car c = (Car)geometry;
		c.setFrontWheelAngle(percent);
	}
}
