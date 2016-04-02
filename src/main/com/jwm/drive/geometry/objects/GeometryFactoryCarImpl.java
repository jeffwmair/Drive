package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Geometry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for building a car Geometry hierarchy
 * Created by Jeff on 2016-03-30.
 */
public class GeometryFactoryCarImpl implements GeometryFactory {

	private static Logger log = LogManager.getLogger(GeometryFactoryCarImpl.class);

    @Override
    public Geometry build() {
        Car car = new Car();

        if (log.isDebugEnabled()) {
			log.debug("GeometryFactoryCarImpl build()");
		}

		List<Geometry> childItems = new ArrayList<>();

		/*
		Geometry frame = new CarFrame();
		children.add(frame);
		CarBody body = new CarBody();
		frame.addChild(body);
		body.addChild(new CarRoof());
		hood = new CarHood();
		body.addChild(hood);
//		body.addChild(new Geometry("door", "paint"));
        body.addChild(new TailLights());
//		body.addChild(new Geometry("trunk", "paint"));
		body.addChild(new CarRearWindow());
		body.addChild(new CarChrome());
		body.addChild(new CarWheelWells());
		//putWheelsOnCar();
		CarMovement.Move movement = CarMovement.Move.COASTING;
		*/

        return car;
    }
}
