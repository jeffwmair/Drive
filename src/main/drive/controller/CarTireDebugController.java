package drive.controller;

import com.jwm.j3dfw.controller.Controller;

import drive.geometry.objects.CarTireDebug;

public class CarTireDebugController extends Controller {
	private CarTireDebug t;

	public CarTireDebugController(CarTireDebug t) {
		super(t);
		this.t = t;
	}
	public void leftMouseDown() {
		// c.setMovement(Move.ACCELERATING);
	}
	public void rightMouseDown() {
		// c.setMovement(Move.DECELERATING);
	}
	public void leftMouseUp() {
		// c.setMovement(Move.COASTING);
	}
	public void rightMouseUp() {
		// c.setMovement(Move.COASTING);
	}
	public void keyPress(int keyCode) {
		if (keyCode == 49) {
			t.setWheelSpeed(0);
		}
		if (keyCode >= 50 && keyCode <= 57) {
			int level = keyCode - 48;
			double speed = level * 10;
			t.setWheelSpeed(speed);
		}
	}
	public void setMousePosition(double xPos, double percent) {
		// c.setFrontWheelAngle(percent);
		t.setWheelAngleInPct(percent);
	}
}
