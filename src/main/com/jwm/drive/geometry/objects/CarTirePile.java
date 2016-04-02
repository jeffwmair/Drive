package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.Rotation.RotationDirection;

class CarTirePile extends Geometry {

	public CarTirePile() {
		super();
		createTires(4);
	}
	public CarTirePile(int numTires) {
		super();
		createTires(numTires);
	}
	private void createTires(int numTires) {
		for (int i = 0; i < numTires; i++) {
			CarTire t = new CarTire();
			t.setOverallTranslation(i * 0.205, 0, 0);
			children.add(t);
		}
		setRotation(90, RotationDirection.leftAndRight);
	}
}
