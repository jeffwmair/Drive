package com.jwm.drive;

import com.jwm.drive.controller.DriveControllerServiceDirectory;
import com.jwm.drive.geometry.DriveGeometryFactory;
import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.util.MainFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

	private static Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("Starting application");

		GeometryFactory geoFactory = new DriveGeometryFactory();
		ControllerDirectory cf = new DriveControllerServiceDirectory();
		MainFrame.startMainFrameWithDefaults(geoFactory, cf);
	}
}
