package drive.geometry.objects;

import drive.geometry.Geometry;

public class CarFrame extends Geometry {
	public CarFrame() {
		super();
		loadRootPart("frame", "frame");
		setOffsetFromOrigin(0, 0, 0);
	}
}
