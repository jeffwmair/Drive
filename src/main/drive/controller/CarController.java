package drive.controller;

import drive.domain.car.CarMovement.Move;
import drive.geometry.objects.Car;
import drive.geometry.objects.Car;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.controller.Controller;

public class CarController extends Controller {
	private Car c;

	public CarController(Car c) {
		super(c);
		this.c = c;
	}
	public void leftMouseDown() {
		c.setMovement(Move.ACCELERATING);
	}
	public void rightMouseDown() {
		c.setMovement(Move.DECELERATING);
	}
	public void leftMouseUp() {
		c.setMovement(Move.COASTING);
	}
	public void rightMouseUp() {
		c.setMovement(Move.COASTING);
	}
	public void keyPress(int keyCode) {
		if (keyCode == 49) {
			c.toggleHoodOpenClose();
		}
		else if ((char)keyCode == 'r') {
			c.getCamera().toggleAutoRotate();
		}
		else if ((char)keyCode == 't') {
			c.getCamera().toggleAutoTrack();
		}
	}
	public void setMousePosition(double xPos, double percent) {
		c.setFrontWheelAngle(percent);
	}
	public void mouseWheelMoved(int wheelRotation) {
		Camera cam = c.getCamera();
		cam.setZoom(wheelRotation);
	}
	public void cmdMouseWheelMoved(int wheelMoved) {
		Camera cam = c.getCamera();
		double angleChange = wheelMoved;
		cam.incrementAngle(angleChange);
	}
	public void shiftMouseWheelMoved(int wheelMoved) {
		Camera cam = c.getCamera();
		double angleChange = wheelMoved;
		cam.incrementVerticalAngle(angleChange);
	}
}
