package com.jwm.drive;

import com.jwm.drive.controller.DriveControllerServiceDirectory;
import com.jwm.drive.geometry.SpeedCalibrationGeometryFactory;
import com.jwm.j3dfw.controller.ControllerDirectory;
import com.jwm.j3dfw.geometry.GeometryFactory;
import com.jwm.j3dfw.util.MainFrame;

/**
 * Main entry point for testing/calibrating vehicle speed
 * Created by Jeff on 2016-04-03.
 */
public class MainSpeedCalibration {

    public static void main(String[] args) {
        int targetFps = 60;
        int frameWidth = 800;
        int frameHeight = 800;

        GeometryFactory geoFactory = new SpeedCalibrationGeometryFactory();
        ControllerDirectory cf = new DriveControllerServiceDirectory();
        MainFrame.startMainFrame(geoFactory, cf, targetFps, frameWidth, frameHeight);
    }
}
