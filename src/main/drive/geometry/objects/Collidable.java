package drive.geometry.objects;

import java.util.List;

import drive.geometry.Vertex;

public abstract class Collidable {
	public abstract boolean isColliding(Collidable other);
	public abstract List<Vertex> getVertices();
	public abstract boolean canBeMoved();
	public abstract double getMass();
	public abstract Vertex getMovementDirection();
	public abstract double getMovementVelocity();
}
