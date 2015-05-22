package drive;

import java.awt.Frame;
import com.jwm.j3dfw.utils.MainFrame;
import com.jwm.j3dfw.geometry.Geometry;
import com.jwm.j3dfw.geometry.GeometryList;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import drive.geometry.objects.Car;
import drive.geometry.objects.DomainFactory;
import drive.geometry.objects.Plane;
import com.jwm.j3dfw.production.Camera;
import com.jwm.j3dfw.production.Scene;

public class Main {
	
	public static void main(String[] args) {
		GeometryList parts = DomainFactory.getCarOnGranularRoad();
	 	MainFrame frame = new MainFrame(parts);
	}
}
