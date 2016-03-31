package com.jwm.j3dfw.geometry;

public class Vertex {
	double x, y, z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }

	public void setY(double val) {
		this.y = val;
	}

	public void setX(double val) {
		this.x = val;
	}

	public void setZ(double val) {
		this.z = val;
	}

	public double getOverheadAngleToOtherVertex(Vertex other) {
		double x = other.x - this.x;
		double z = other.z - this.z;
		double theta = Math.asin(x / z);
		double theta_degrees = Math.toDegrees(theta);
		return theta_degrees;
	}

	@Override
	public String toString() {
		return "Vertex x:"+x+", y:" + y + ", z:" + z;
	}
}
