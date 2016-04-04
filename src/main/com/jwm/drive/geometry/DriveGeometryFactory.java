package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.geometry.shapes.Plane;
import com.jwm.j3dfw.production.Camera;

import java.util.ArrayList;
import java.util.List;

public class DriveGeometryFactory implements GeometryFactory {
	private static Car c = null;


	/**
	 * Get all the geometry items for the scene
 	 * @return
     */
	@Override
	public List<Geometry> buildGeometryItems() {
		List<Geometry> geometryList = new ArrayList<>();
		double roadSeparation = 10.05;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				Plane roadSection10Meters = new Plane();
				roadSection10Meters.setOverallTranslation(j * roadSeparation, 0, i * -roadSeparation);
				geometryList.add(roadSection10Meters);
			}
		}
		c = new Car();
		geometryList.add(c);
		return geometryList;
	}

	/**
	 * Get the main camera to be used
	 * @return
     */
	@Override
	public Camera getMainCamera() {
		if (c == null) {
			throw new RuntimeException("Car not yet initialized, so can't get the camera!");
		}
		return c.getCamera();
	}

}
