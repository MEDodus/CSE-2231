package raytracer.components.examples;

public abstract class Hittable {

    public HitRecord rec;

    public abstract boolean hit(Ray r, double tMin, double tMax, HitRecord rec);
}
