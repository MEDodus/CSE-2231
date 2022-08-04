package raytracer.components.examples;

public class Lambertian extends Material {
	
	public Color albedo;
	
	public Lambertian(Color c) {
		this.albedo = c;
	}

	@Override
	public boolean scatter(Ray rIn, HitRecord rec, Color attenuation, Ray scattered) {
		
		Vector scatterDirection = rec.normal.add(Vector.randomUnitVector());
		
		// Catch degenerate scatter direction
		if (scatterDirection.nearZero()) {
			scatterDirection = new Vector(rec.normal);
		}
		
		scattered.setOrigin(rec.p);
		scattered.setDirection(scatterDirection);
		// attenuation = albedo;
		attenuation.setX(this.albedo.getX());
		attenuation.setY(this.albedo.getY());
		attenuation.setZ(this.albedo.getZ());
		return true;
	}
	
}
