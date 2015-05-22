package drive.controller;

import drive.geometry.objects.Car;
import drive.geometry.objects.CarTireDebug;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.controller.Controller;

public class ControllerFactory {
	public static Controller getInstance(Geometry g) {
		if (g instanceof Car) {
			return new CarController((Car) g);
		}
		else if (g instanceof CarTireDebug) {
			return new CarTireDebugController((CarTireDebug) g);
		} else {
			return new Controller(g);
		}
	}
}
