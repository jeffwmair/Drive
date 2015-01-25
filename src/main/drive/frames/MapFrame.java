package drive.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import drive.geometry.Geometry;
import drive.geometry.GeometryListener;
import drive.geometry.Rotation.RotationDirection;
import drive.geometry.Vertex;
import drive.geometry.objects.Car;
import drive.production.Camera;

@SuppressWarnings("serial")
public class MapFrame extends JFrame implements GeometryListener {
	private int startX = 5;
	private int startY = 25;
	private int xCenter;
	private int yCenter;
	private Car car;
	private Camera camera;
	private List<String> carInfo;

	public MapFrame(Car car, Camera cam) {
		this.car = car;
		this.camera = cam;
		carInfo = new ArrayList<String>();
		this.car.registerInfoListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		int sz;
		int w = getWidth();
		int h = getHeight();
		sz = Math.min(w, h) - 20;
		xCenter = (sz / 2) + startX;
		yCenter = (sz / 2) + startY;

		Graphics2D g2 = (Graphics2D) g;

		drawAxes(g2, sz);
		g2.setColor(Color.BLUE);
		Vertex center = car.getCenter();
		double rotationAngle = car.getRotation(RotationDirection.overhead).getAngle();
		int xPos = xCenter + (int) center.x;
		int yPos = yCenter + (int) center.y;
		GeneralPath carDrawing = getTriangle(xPos, yPos, rotationAngle);
		g2.draw(carDrawing);
		g2.fill(carDrawing);

		GeneralPath cam = getCamera(car, rotationAngle);
		g2.setColor(Color.RED);
		g2.draw(cam);
		g2.fill(cam);

		// this circle drawing isn't correct... should rotate with the car
		int circleRad = (int)car.getMinTurnRadius();
		int circleDiam = circleRad * 2;
		g2.drawOval(xPos - circleRad, yPos - circleRad, circleDiam, circleDiam);
		g2.setColor(Color.RED);

		g.setColor(Color.BLUE);
		drawCarStats(g2);
	}

	@Override
	public synchronized void writeInfo(List<String> info) {
		for (String s : info) {
			carInfo.add(s);
		}
	}

	private synchronized void drawCarStats(Graphics2D g) {
		int yOffset = 40;
		for (int i = 0; i < carInfo.size(); i++) {
			String s = carInfo.get(i);
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.drawString(s, 10, yOffset + 15 * (i));
		}
		carInfo.clear();
	}

	private void drawAxes(Graphics2D g2, int sz) {
		g2.setColor(Color.BLACK);
		g2.draw(getRectangle(sz, sz));
		g2.draw(getRectangle(sz / 2, sz));
		Rectangle rectHoriz = getRectangle(sz, sz / 2);
		rectHoriz.translate(0, sz / 2);
		g2.draw(rectHoriz);
	}
	private GeneralPath getTriangle(double xPos, double yPos, double rotationAngle) {

		int size = 10;
		GeneralPath triangle = new GeneralPath();
		// double xRelToCenter = xCenter + .x;
		// double yRelToCenter = yCenter + v.z;
		triangle.moveTo(0, 0);
		triangle.lineTo(-size, size);
		triangle.lineTo(size, size);
		triangle.lineTo(0, 0);
		AffineTransform trans = new AffineTransform();
		trans.translate(xPos, yPos);
		trans.rotate(Math.toRadians(-rotationAngle));
		triangle.transform(trans);
		return triangle;
	}
	private GeneralPath getCamera(Geometry target, double targetAngle) {
		Vertex cameraLocation = camera.getPosition();
		Vertex cameraTarget = camera.getTarget().getCenter();
		double xPos = xCenter + cameraLocation.x;
		double yPos = yCenter + cameraLocation.y;
		double angle = cameraLocation.getOverheadAngleToOtherVertex(cameraTarget);
		GeneralPath triangle = getTriangle(xPos, yPos, angle);
		return triangle;
	}

	private Rectangle getRectangle(int w, int h) {
		return new Rectangle(startX, startY, w, h);
	}

}
