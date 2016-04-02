package com.jwm.j3dfw.demo;

import com.jwm.j3dfw.controller.Controller;
import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.geometry.Geometry;

class ControllerFactoryDefaultImpl implements ControllerFactory {
	public Controller getInstance(Geometry g) {
		return new Controller(g);
	}
}
