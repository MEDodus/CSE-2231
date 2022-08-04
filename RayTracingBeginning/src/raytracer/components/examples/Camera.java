package raytracer.components.examples;

public class Camera {
	
	/*
	 * Class instance variables we will take advantage of to position camera.
	 */
	private Point3D origin;
	
	private Point3D lowerLeftCorner;
	
	private Vector horizontal;
	
	private Vector vertical;
	

	public Camera() {
		
		double aspectRatio = 16.0 / 9.0;
		double viewportHeight = 2.0;
		double viewportWidth = aspectRatio * viewportHeight;
		double focalLength = 1.0;
		
		this.origin = new Point3D(0, 0, 0);
		this.horizontal = new Vector(viewportWidth, 0.0, 0.0);
		this.vertical = new Vector(0.0, viewportHeight, 0.0);
		this.lowerLeftCorner = origin.minus(horizontal.divideBy(2)).minus(vertical.divideBy(2)).minus(new Vector(0, 0, focalLength));
		
	}
	
	public final Ray getRay(double u, double v) {
		return new Ray(this.origin, this.lowerLeftCorner.add(horizontal.multiplyBy(u)).add(vertical.multiplyBy(v)).minus(this.origin));
	}
	
}
