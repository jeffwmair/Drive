package com.jwm.j3dfw.geometry;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;

/**
 * A material contains colour components; determines how a Geometry looks
 * 
 * @author Jeff
 *
 */
public class Material {

	// todo: replace with getters
	public FloatBuffer ambient;
	public FloatBuffer diffuse;
	public FloatBuffer specular;
	public FloatBuffer shinyness;

	public Material(float[] amb, float[] diff, float[] spec, float[] shiny) {
		ambient = Buffers.newDirectFloatBuffer(amb);
		diffuse = Buffers.newDirectFloatBuffer(diff);
		specular = Buffers.newDirectFloatBuffer(spec);
		shinyness = Buffers.newDirectFloatBuffer(shiny);
	}
}
