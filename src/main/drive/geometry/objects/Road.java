package drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;

public class Road extends Geometry {
	public Road() {
		super();
		loadRootPart("pavement", "pavement");
	}
}
