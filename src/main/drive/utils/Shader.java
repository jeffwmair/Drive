package drive.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.media.opengl.GL2;

public class Shader {

	private static String readFromFile(String filename) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();

		} catch (Exception ex) {
			return null;
		}
	}

	public static void initShaders(GL2 gl, String vertexShaderFile, String fragmentShaderFile) {
		int v = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		int f = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

		String vsrc = readFromFile(vertexShaderFile);
		gl.glShaderSource(v, 1, new String[] { vsrc }, (int[]) null, 0);
		gl.glCompileShader(v);

		String fsrc = readFromFile(fragmentShaderFile);
		gl.glShaderSource(f, 1, new String[] { fsrc }, (int[]) null, 0);
		gl.glCompileShader(f);

		int shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, v);
		gl.glAttachShader(shaderprogram, f);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);

		gl.glUseProgram(shaderprogram);

		// timeUniform = gl.glGetUniformLocation(shaderprogram, "time");

	}
}
