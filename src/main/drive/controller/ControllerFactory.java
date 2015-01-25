package drive.controller;

import drive.geometry.Geometry;
import drive.geometry.objects.Car;
import drive.geometry.objects.CarTireDebug;

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
