package raytracer.components.examples;
	
public abstract class Material {
	
	public HitRecord rec;

	// Overridden in extended classes
	public abstract boolean scatter(final Ray rIn, final HitRecord rec, Color attenuation, Ray scattered);
}
