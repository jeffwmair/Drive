package com.jwm.j3dfw.demo.buildings;

import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.controller.ControllerService;
import com.jwm.j3dfw.geometry.Geometry;

class ControllerDirectoryDefaultImpl implements ControllerDirectory {
	private ControllerService controllerService = new ControllerService();
	public ControllerService getInstance(Geometry g) {
		return controllerService;
	}
}
