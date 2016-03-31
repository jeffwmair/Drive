package com.jwm.j3dfw.demo;

import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.controller.ControllerFactoryDefaultImpl;
import com.jwm.j3dfw.demo.geometry.RotatingPlane;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.geometry.Rotation;
import com.jwm.j3dfw.geometry.Vertex;
import com.jwm.j3dfw.geometry.shapes.Cube;
import com.jwm.j3dfw.production.DefaultCamera;
import com.jwm.j3dfw.util.MainFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Random;

public class DemoBuildings {
	
	private static Logger log = LogManager.getLogger(DemoBuildings.class);
	public static void main(String[] args) {
		GeometryList parts = new GeometryList();
		RotatingPlane plane = new RotatingPlane();
		parts.add(plane);

		Random rand = new Random(System.currentTimeMillis());

		// y -> vertical height 
		// z -> distance from the camera; 
		double z = -3.0;
		double xRange = 3.0;
		double zRange = 5.0;
		for (int i = 0; i < 50; i++) {
			Cube tower = new Cube();
			tower.setRotation(180.0, Rotation.RotationDirection.endToEnd);
			// move the tower placement
			double randomX = getRandom(-xRange, xRange, rand);
			double randomZ = getRandom(-zRange, zRange, rand);
			randomZ -= 7.0;
			double randomHeight = getRandom(1.0, 4.5, rand);
			tower.setOverallTranslation(randomX, -0.95, randomZ);
			tower.setScale(0.25, randomHeight, 0.25);
			plane.addChild(tower);
		}
		/*
		Geometry geo = parts.firstOrNull(Geometry.class);
		geo.initCamera();
		TargetCamera cam = geo.getCamera();
		cam.toggleAutoTrack();
		*/
		DefaultCamera cam = new DefaultCamera();
		Vertex camPos = cam.getPosition();
		Vertex camTarget = cam.getTarget();
		camTarget.setY(camTarget.getY()+20);
		camPos.setY(20);

		ControllerFactory cf = new ControllerFactoryDefaultImpl();

		int targetFps = 60;
		int frameWidth = 800;
		int frameHeight = 800;
		MainFrame.startMainFrame(parts, cf, cam, targetFps, frameWidth, frameHeight);
	}

	private static double getRandom(double min, double max, Random r) {
		double randDbl = r.nextDouble();
		return scaleVal(min, max, randDbl);
	}
	private static double scaleVal(double min, double max, double val) {
		double newRange = max-min;
		double oldRange = 1.0; // java nextDouble...
		return newRange * val + min;
	}
}