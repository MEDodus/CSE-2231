package raytracer.components.examples;

public class Metal extends Material {

	public Color albedo;
	
	public Metal(Color c) {
		this.albedo = c;
	}
	
	@Override
	public boolean scatter(Ray rIn, HitRecord rec, Color attenuation, Ray scattered) {
		
		Vector reflected = Vector.reflect(Utility.unitVector(rIn.direction()), rec.normal);
		scattered.setOrigin(rec.p);
		scattered.setDirection(reflected);
		// attenuation = albedo;
		attenuation.setX(this.albedo.getX());
		attenuation.setY(this.albedo.getY());
		attenuation.setZ(this.albedo.getZ());
		return (Utility.dot(scattered.direction(), rec.normal) > 0);
	}

}
