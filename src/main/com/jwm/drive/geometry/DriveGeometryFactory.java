package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.geometry.shapes.Plane;
import com.jwm.j3dfw.production.Camera;

import java.util.ArrayList;
import java.util.List;

public class DriveGeometryFactory implements GeometryFactory {
	private static Car c = null;

	private final List<Geometry> geo;
	private final Camera mainCamera;

	public DriveGeometryFactory() {
		geo = new ArrayList<>();
		double roadSeparation = 10.05;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				Plane roadSection10Meters = new Plane();
				roadSection10Meters.setOverallTranslation(j * roadSeparation, 0, i * -roadSeparation);
				geo.add(roadSection10Meters);
			}
		}
		c = new Car();
		geo.add(c);

		mainCamera = c.getCamera();
	}


	/**
	 * Get all the geometry items for the scene
	 * @return
	 */
	@Override
	public List<Geometry> buildGeometryItems() {
		return geo;
	}

	/**
	 * Get the main camera to be used
	 * @return
	 */
	@Override
	public Camera getMainCamera() {
		return mainCamera;
	}

}
