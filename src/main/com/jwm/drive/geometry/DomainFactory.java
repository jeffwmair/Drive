package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.geometry.shapes.Cube;
import com.jwm.j3dfw.geometry.shapes.Plane;
import com.jwm.j3dfw.production.Camera;

import java.util.ArrayList;
import java.util.List;

public class DomainFactory {
	private static Car c = null;

	public static List<Geometry> getSpeedCalibrationRoadAndCar() {
		List<Geometry> geo = new ArrayList<>();
		/*
		 * 1km of road; should take the car 36 seconds @ 100km/h Need to
		 * temporarily set the CarController so that the mouse click sets it
		 * speed to 100 and Movement to Steady. And adjust car setMovement to
		 * ignore other inputs. Just make sure the car is going steady.
		 */
		for (int i = 0; i < 100; i++) {
			Plane roadSection10Meters = new Plane();
			roadSection10Meters.setOverallTranslation(0, 0, i * -10.05);
			geo.add(roadSection10Meters);
		}
		Car c = new Car();
		geo.add(c);
		return geo;
	}

	/**
	 * Get all the geometry items for the scene
 	 * @return
     */
	public static GeometryList getGeometryObjects() {
		GeometryList geo = new GeometryList();
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
		return geo;
	}

	/**
	 * Get the main camera to be used
	 * @return
     */
	public static Camera getMainCamera() {
		if (c == null) {
			throw new RuntimeException("Car not yet initialized, so can't get the camera!");
		}
		return c.getCamera();
	}

}
