package drive;

import java.awt.Frame;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import drive.frames.MapFrame;
import drive.geometry.Geometry;
import drive.geometry.GeometryList;
import drive.geometry.objects.Car;
import drive.geometry.objects.DomainFactory;
import drive.geometry.objects.Plane;
import drive.production.Camera;
import drive.production.Scene;

public class Main {
	
	public static void main(String[] args) {
		run();	
	}
	
	private static void test() {
		
	}
	private static void run() {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setSampleBuffers(true);
		GLCanvas canvas = new GLCanvas(caps);

//		List<Geometry> parts = DomainFactory.getSpeedCalibrationRoadAndCar();
		GeometryList parts = DomainFactory.getCarOnGranularRoad();
		Camera cam = null;
		Car targetObject = null;
		for (Geometry p : parts) {
			if (p instanceof Car) {
				targetObject = (Car)p;
				cam = p.getCamera();
			} else if (p instanceof Plane && cam == null) {
//				cam = p.getCamera();
			}
		}

		MapFrame frameMap = new MapFrame(targetObject, cam);
		frameMap.setSize(400, 400);
		frameMap.setLocation(1000, 0);
		frameMap.setVisible(true);

		Scene scene = new Scene(parts, cam, frameMap);
		EventListener listener = new EventListener(scene, parts, canvas);
		canvas.addMouseMotionListener(listener);
		canvas.addMouseWheelListener(listener);
		canvas.addMouseListener(listener);
		canvas.addKeyListener(listener);
		canvas.addGLEventListener(scene);

		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();

		Frame frame = new Frame();
		frame.setSize(800, 800);
		frame.add(canvas);
		frame.setVisible(true);

	}
}
