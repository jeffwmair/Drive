package drive;

import java.awt.Frame;

import com.jwm.j3dfw.utils.MainFrame;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import drive.controller.DriveControllerFactory;
import drive.geometry.objects.Car;
import drive.geometry.objects.DomainFactory;
import drive.geometry.objects.Plane;

import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;
import com.jwm.j3dfw.controller.ControllerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("Starting application");
		GeometryList parts = DomainFactory.getCarOnGranularRoad();
		ControllerFactory cf = new DriveControllerFactory();
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

		MainFrame frame = new MainFrame(parts, cf, cam);
	}
}
