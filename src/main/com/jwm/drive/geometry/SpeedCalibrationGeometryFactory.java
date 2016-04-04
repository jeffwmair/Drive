package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.geometry.shapes.Plane;
import com.jwm.j3dfw.production.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 2016-04-03.
 */
public class SpeedCalibrationGeometryFactory implements GeometryFactory {

    private final List<Geometry> geo;
    private final Camera mainCamera;

    public SpeedCalibrationGeometryFactory() {
        geo = new ArrayList<>();
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

        mainCamera = c.getCamera();
    }

    @Override
    public List<Geometry> buildGeometryItems() {
        return geo;
    }

    @Override
    public Camera getMainCamera() {
        return mainCamera;
    }
}
