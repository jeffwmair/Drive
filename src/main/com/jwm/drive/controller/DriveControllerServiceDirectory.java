package com.jwm.drive.controller;

import com.jwm.drive.geometry.Car;
import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;

public class DriveControllerServiceDirectory implements ControllerDirectory {

	private ControllerService controllerService = new ControllerService();
	private CarControllerService carControllerService = new CarControllerService();

	public ControllerService getInstance(Geometry g) {
		if (g instanceof Car) {
			return carControllerService;
		} else {
			return controllerService;
		}
	}
}
