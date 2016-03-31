package com.jwm.j3dfw.geometry.shapes;

import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Cube extends Geometry {
	private static Logger log = LogManager.getLogger(Cube.class);
	public Cube(String material) {
		super();
		loadRootPart("cube-rough", material);
	}
	public Cube() {
		super();
		loadRootPart("cube-rough", "cube");
	}
}
