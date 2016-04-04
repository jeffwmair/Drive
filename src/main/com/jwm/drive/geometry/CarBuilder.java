package com.jwm.drive.geometry;

import com.jwm.j3dfw.geometry.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 2016-04-03.
 */
public class CarBuilder {

	public static Car buildCar() {


		List<Geometry> children = new ArrayList<>();
		Geometry frame = new Geometry("frame", "frame");
		children.add(frame);
		Geometry body = new Geometry("body", "paint_white");
		frame.addChild(body);
		body.addChild(new Geometry("vinylroof", "vinylroof"));
		Geometry hood = new CarHood();
		body.addChild(hood);
		body.addChild(new Geometry("red_plastic", "red_plastic"));
		body.addChild(new Geometry("rearwindow", "rearwindow"));
		body.addChild(new Geometry("chrome", "chrome"));
		body.addChild(new Geometry("wheelwells", "wheelwells"));

		CarTire tireFrontLeft;
		CarTire tireFrontRight;
		CarTire tireRearLeft;
		CarTire tireRearRight;

		tireFrontLeft = new CarTire();
		tireFrontRight = new CarTire();
		tireRearLeft = new CarTire();
		tireRearRight = new CarTire();

		float TIRE_HORIZONTAL_TRANSLATE = 0.6f;
		float TIRE_FRONT_TRANSLATE = -2.85f;
		tireFrontLeft.setOverallTranslation(TIRE_HORIZONTAL_TRANSLATE, 0, TIRE_FRONT_TRANSLATE);
		tireFrontRight.setOverallTranslation(-TIRE_HORIZONTAL_TRANSLATE, 0, TIRE_FRONT_TRANSLATE);
		tireRearLeft.setOverallTranslation(TIRE_HORIZONTAL_TRANSLATE, 0, 0);
		tireRearRight.setOverallTranslation(-TIRE_HORIZONTAL_TRANSLATE, 0, 0);

		children.add(tireFrontLeft);
		children.add(tireFrontRight);
		children.add(tireRearLeft);
		children.add(tireRearRight);

		List<CarTire> allTires = new ArrayList<>();
		allTires.add(tireFrontLeft);
		allTires.add(tireFrontRight);
		allTires.add(tireRearLeft);
		allTires.add(tireRearRight);

		List<CarTire> frontTires = new ArrayList<>();
		frontTires.add(tireFrontLeft);
		frontTires.add(tireFrontRight);


		Car c = new Car(children, frame, allTires, frontTires);
		return c;

	}
}
