package com.jwm.drive;

import com.jwm.drive.controller.DriveControllerServiceDirectory;
import com.jwm.drive.geometry.Car;
import com.jwm.drive.geometry.DomainFactory;
import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.util.MainFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

	private static Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("Starting application");
		GeometryList parts = DomainFactory.getCarOnGranularRoad();
		ControllerDirectory cf = new DriveControllerServiceDirectory();
		Camera cam = null;

		// TODO: something better than this
		for (Geometry p : parts) {
			if (p instanceof Car) {
				cam = p.getCamera();
			} 
		}
		if (cam == null) {
			throw new RuntimeException("Camera not found!");
		}

		int targetFps = 60;
		int frameWidth = 800;
		int frameHeight = 800;

		MainFrame.startMainFrame(parts, cf, cam, targetFps, frameWidth, frameHeight);
	}
}
