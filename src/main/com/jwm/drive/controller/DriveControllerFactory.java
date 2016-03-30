package com.jwm.drive.controller;

import com.jwm.j3dfw.controller.Controller;
import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.geometry.Geometry;

import com.jwm.drive.geometry.objects.Car;
import com.jwm.drive.geometry.objects.CarTireDebug;

public class DriveControllerFactory implements ControllerFactory {
	public Controller getInstance(Geometry g) {
		if (g instanceof Car) {
			return new CarController((Car) g);
		}
		else if (g instanceof CarTireDebug) {
			return new CarTireDebugController((CarTireDebug) g);
		} else {
			return new Controller(g);
		}
	}
}
