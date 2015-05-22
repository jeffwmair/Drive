package drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;

public class Marker extends Geometry {
	public Marker() {
		super();
		loadRootPart("squaremarker", "squaremarker");
		setOffsetFromOrigin(0, 0, 0);
	}
}
