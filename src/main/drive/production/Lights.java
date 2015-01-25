package drive.production;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.fixedfunc.GLLightingFunc;

public class Lights {
	private static float spotlightAngle;
	private static FloatBuffer model_ambient_buff;
	private static FloatBuffer light_diffuse0_buff;
	private static FloatBuffer light_specular0_buff;
	private static FloatBuffer light_position0_buff;
	private static FloatBuffer light_spot0_dir_buff;
	private static boolean useSpotlight;
	static {
		/*
		 * From openGl Superbible 4th: light position contains x, y, and z that
		 * spec either the lights actual position in the scene or the direction
		 * from which the light is coming.
		 * 
		 * The last value indicates that the light is actually present at this
		 * location. By default, the light radiates equally in all directions
		 * from this location, but you can change this default to make it a
		 * spotlight.
		 * 
		 * Setting to 0 creates directional light source: all rays strike at the
		 * same angle. This is like the sun which is (essentially) infinitely
		 * far away.
		 * 
		 * Setting to 1 puts the light at the location of the xyz points.
		 */
		float modelLight = 1.0f;
		float diffLevel = 3f;
		float specLevel = 0.25f;
		spotlightAngle = 8;
		useSpotlight = false;
		float spotlightParam = 1;
		if (!useSpotlight)
			spotlightParam = 0;
		float light_position0[] = { 0, 5, 5, spotlightParam };
		float light_direction0[] = { 0.2f, -1, -1 };
		float light_diffuse0[] = { diffLevel, diffLevel, diffLevel, 1.0f };
		float light_specular0[] = { specLevel, specLevel, specLevel, 1.0f };
		float model_ambient[] = { modelLight, modelLight, modelLight, 1.0f };
		model_ambient_buff = FloatBuffer.wrap(model_ambient);
		light_diffuse0_buff = FloatBuffer.wrap(light_diffuse0);
		light_specular0_buff = FloatBuffer.wrap(light_specular0);
		light_position0_buff = FloatBuffer.wrap(light_position0);
		light_spot0_dir_buff = FloatBuffer.wrap(light_direction0);
	}

	public static void setupLighting(GL2 gl) {
		// Global settings.
		gl.glEnable(GL2.GL_MULTISAMPLE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH_HINT);
		gl.glHint(GL2.GL_MULTISAMPLE_FILTER_HINT_NV, GL2.GL_NICEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, model_ambient_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, light_diffuse0_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, light_specular0_buff);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, light_position0_buff);
		if (useSpotlight) {
			gl.glLightfv(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, light_spot0_dir_buff);
			gl.glLightf(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_CUTOFF, spotlightAngle);
		}
	}
}
