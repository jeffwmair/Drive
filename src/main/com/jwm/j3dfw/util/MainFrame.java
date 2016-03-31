package com.jwm.j3dfw.util;

import java.awt.Frame;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jogamp.opengl.util.FPSAnimator;
import com.jwm.j3dfw.controller.ControllerFactory;
import com.jwm.j3dfw.geometry.GeometryList;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;

/**
 * Main GUI frame.
 */
public class MainFrame {

	private static Logger log = LogManager.getLogger(MainFrame.class);
	private static MainFrame instance;

	/**
	 * Get a singleton instance
	 * @param geometryList
	 * @param controllerFactory
	 * @param cam
     * @return
     */
	public static synchronized void startMainFrame(GeometryList geometryList, ControllerFactory controllerFactory, Camera cam, int targetFps, int frameWidth, int frameHeight) {
		if (instance != null) {
			return;
		}

		instance = new MainFrame(geometryList, controllerFactory, cam, targetFps, frameWidth, frameHeight);
	}

	MainFrame(GeometryList parts, ControllerFactory controllerFactory, Camera cam, int targetFps, int frameWidth, int frameHeight) {
		log.info("New MainFrame");
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setSampleBuffers(true);
		GLCanvas canvas = new GLCanvas(caps);

		Scene scene = new Scene(parts, cam);
		EventListener listener = new EventListener(scene, parts, controllerFactory, canvas);
		canvas.addMouseMotionListener(listener);
		canvas.addMouseWheelListener(listener);
		canvas.addMouseListener(listener);
		canvas.addKeyListener(listener);
		canvas.addGLEventListener(scene);

		FPSAnimator animator = new FPSAnimator(canvas, targetFps);
		animator.start();

		Frame frame = new Frame();
		frame.setSize(frameWidth, frameHeight);
		frame.add(canvas);
		frame.setVisible(true);
	}

}
