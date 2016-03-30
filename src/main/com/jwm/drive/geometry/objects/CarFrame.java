package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;

public class CarFrame extends Geometry {
	public CarFrame() {
		super();
		loadRootPart("frame", "frame");
		setOffsetFromOrigin(0, 0, 0);
	}
}
