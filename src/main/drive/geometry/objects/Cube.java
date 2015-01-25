package drive.geometry.objects;

import drive.geometry.Geometry;

public class Cube extends Geometry {
	public Cube(String material) {
		super();
		loadRootPart("cube", material);
	}
	public Cube() {
		super();
		loadRootPart("cube", "cube");
	}
}
