package com.jwm.drive.geometry.objects;

import com.jwm.j3dfw.geometry.Vertex;

import java.util.List;

public abstract class Collidable {
	public abstract boolean isColliding(Collidable other);
	public abstract List<Vertex> getVertices();
	public abstract boolean canBeMoved();
	public abstract double getMass();
	public abstract Vertex getMovementDirection();
	public abstract double getMovementVelocity();
}
