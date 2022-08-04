package raytracer.components.examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Main {

	public static double hitSphere(Point3D center, double radius, Ray r) {

		Vector oc = Utility.subtractV(r.origin(), center);
		// double a = VectorUtil.dot(r.direction(), r.direction());
		double a = r.direction().lengthSqr();
		// double b = 2.0 * VectorUtil.dot(oc, r.direction());
		double halfB = Utility.dot(oc, r.direction());
		// double c = VectorUtil.dot(oc, oc) - (radius * radius);
		double c = oc.lengthSqr() - (radius * radius);
		// double discriminant = (b * b) - (4 * a * c);
		double discriminant = (halfB * halfB) - (a * c);
		if (discriminant < 0) {
			return -1.0;
		} else {
			return (-halfB - Math.sqrt(discriminant)) / a;
		}
	}

	// New rayColor function
//	public static Color rayColor(final Ray r, final HittableList world, int depth) {
//
//		// If we've exceeded the ray bounce limit, no more light is gathered.
//		if (depth <= 0) {
//			return new Color(0, 0, 0);
//		}
//		
//		HitRecord rec = new HitRecord();
//		if (world.hit(r, 0.001, Utility.INFINITY, rec)) {
//			Point3D target = rec.p.add(rec.normal).add(Vector.randomUnitVector());
//			// Point3D target = rec.p.add(Vector.randomInHemisphere(rec.normal));
//			return rayColor(new Ray(rec.p, target.minus(rec.p)), world, depth - 1).multiplyBy(0.5);
//		}
//		Vector unitDirection = Utility.unitVector(r.direction());
//		double t = 0.5 * (unitDirection.getY() + 1.0);
//		Color c = new Color(1.0, 1.0, 1.0).multiplyBy(1.0 - t).add(new Color(0.5, 0.7, 1.0).multiplyBy(t));
//		return c;
//	}
	
	public static Color rayColor(final Ray r, final HittableList world, int depth) {

		// If we've exceeded the ray bounce limit, no more light is gathered.
		if (depth <= 0) {
			return new Color(0, 0, 0);
		}
		
		HitRecord rec = new HitRecord();
		if (world.hit(r, 0.001, Utility.INFINITY, rec)) {
			Ray scattered = new Ray(rec.p, rec.normal);
			Color attenuation = new Color();
			if (rec.mat.scatter(r, rec, attenuation, scattered)) {
				return attenuation.mult(rayColor(scattered, world, depth - 1));
			}
			return new Color(0, 0, 0);
		}
		Vector unitDirection = Utility.unitVector(r.direction());
		double t = 0.5 * (unitDirection.getY() + 1.0);
		Color c = new Color(1.0, 1.0, 1.0).multiplyBy(1.0 - t).add(new Color(0.5, 0.7, 1.0).multiplyBy(t));
		return c;
	}

	public static void main(String[] args) throws IOException {

		long time = System.nanoTime();

		// Image

		final double aspectRatio = 16.0 / 9.0;
		final int imageWidth = 400; // CHANGE BACK TO 400 AFTER DEBUGGING
		final int imageHeight = (int) (imageWidth / aspectRatio);
		final int samplesPerPixel = 100;
		final int maxDepth = 50;

		// World
		
		HittableList world = new HittableList();
//		world.add(new Sphere(new Point3D(0, 0, -1), 0.5));
//		world.add(new Sphere(new Point3D(0, -100.5, -1), 100));
		
		Lambertian ground = new Lambertian(new Color(0.8, 0.8, 0.0));
		Lambertian center = new Lambertian(new Color(0.7, 0.3, 0.3));
		Metal left = new Metal(new Color(0.8, 0.8, 0.8));
		Metal right = new Metal(new Color(0.8, 0.6, 0.2));
		
		world.add(new Sphere(new Point3D(0.0, -100.5, -1.0), 100.0, ground));
		world.add(new Sphere(new Point3D(0.0, 0.0, -1.0), 0.5, center));
		world.add(new Sphere(new Point3D(-1.0, 0.0, -1.0), 0.5, left));
		world.add(new Sphere(new Point3D(1.0, 0.0, -1.0), 0.5, right));

		// Camera
		
		Camera cam = new Camera();

		double viewportHeight = 2.0;
		double viewportWidth = aspectRatio * viewportHeight;
		double focalLength = 1.0;

		// PrintWriter to output to .ppm file.

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/test-20.ppm")));

		// Render

		out.println("P3");
		out.println(imageWidth + " " + imageHeight);
		out.println("255");

		for (int j = imageHeight - 1; j >= 0; j--) {
			System.err.println("\rScanlines remaining: " + j + " ");
			for (int i = 0; i < imageWidth; i++) {
				Color pixelColor = new Color(0, 0, 0);
				for (int s = 0; s < samplesPerPixel; s++) {
					double u = (i + Utility.random()) / (imageWidth - 1);
					double v = (j + Utility.random()) / (imageHeight - 1);
					Ray r = cam.getRay(u, v);
					pixelColor.addVector(rayColor(r, world, maxDepth));
				}
				Utility.writeColor(out, pixelColor, samplesPerPixel);
			}
		}
		out.close();

		System.err.println("\nDone.\n");

		time = System.nanoTime() - time;
		long seconds = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
		System.out.println(time + " in seconds is " + seconds);
	}
}
