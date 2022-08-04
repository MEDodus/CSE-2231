package raytracer.components.examples;

import java.io.PrintWriter;

/**
 * Class with utility functions for {@code Vector} and its extended classes
 * {@code Color} and {@code Point3D}.
 *
 * @author Michael Dodus
 *
 */
public class Utility {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Utility() {

    }

    /**
     * Prints the x, y, and z values associated in the 3D vector class
     * {@code Vector}.
     *
     * @param out
     *            PrintWriter to write to a file.
     * @param v
     *            Vector with x,y,z values to be printed.
     */
    public static void printXYZ(PrintWriter out, Vector v) {
        out.println(v.getX() + " " + v.getY() + " " + v.getZ());
    }

    /**
     * Vector addition by adding two vectors with one another.
     *
     * @param u
     *            {@code Vector} to be added.
     * @param v
     *            {@code Vector} to be added.
     * @return {@code Vector} a reference to a new Vector object containing the
     *         sum of the passed in Vector arguments.
     */
    public static Vector addVectors(Vector u, Vector v) {
        Vector s = new Vector(u);
        s.addVector(v);
        return s;
    }

    /**
     * Overloaded method same as Vector variant, for use with Colors.
     *
     * @param u
     * @param v
     * @return
     */
    public static Color addVectors(Color u, Color v) {
        Color s = new Color(u);
        s.addVector(v);
        return s;
    }

    /**
     * Overloaded method same as Vector variant, for use with Vector and Point3D
     */
    public static Point3D addVectors(Point3D u, Vector v) {
        Point3D s = new Point3D(u);
        s.addVector(v);
        return s;
    }

    /**
     * Overloaded method same as Vector variant, for use with Color and Vector.
     */
    public static Color addVectors(Color u, Vector v) {
        Color c = new Color(u);
        c.addVector(v);
        return c;
    }

    /**
     * Vector subtraction by subtracting two vectors with one another.
     *
     * @param u
     *            {@code Vector} to be subtracted from.
     * @param v
     *            {@code Vector} which will do the subtraction.
     * @return {@code Vector} a reference to a new Vector object containing the
     *         difference of the passed in Vector arguments.
     */
    public static Vector subtractV(Vector u, Vector v) {
        Vector s = new Vector(u);
        s.subtractVector(v);
        return s;
    }

    /**
     * Vector multiplication by calculating the dot-product of two vectors with
     * one another.
     *
     * @param u
     *            {@code Vector} to be multiplied.
     * @param v
     *            {@code Vector} to be multiplied.
     * @return {@code Vector} a reference to a new Vector object containing the
     *         dot-product of two passed in Vector arguments.
     */
    public static Vector multiplyV(Vector u, Vector v) {
        Vector s = new Vector(u);
        s.multiplyVector(v);
        return s;
    }

    /**
     * Vector multiplication by calculating the dot-product with a constant
     * factor and a vector.
     *
     * @param t
     *            {@code double} with constant value to be multiplied by.
     * @param v
     *            {@code Vector} to apply constant factor to.
     * @return {@code Vector} a reference to a new Vector object containing the
     *         dot-product of the vector argument with the double argument.
     */
    public static Vector multiplyT(final double t, Vector v) {
        Vector s = new Vector();
        s.addVector(v);
        s.multiplyByFactor(t);
        return s;
    }

    //NOTE: Missing operator* and operator/ from guide.

    public static Vector divide(final double t, Vector v) {
        double oneOverT = 1 / t;
        Vector s = new Vector(v);
        s.multiplyByFactor(oneOverT);
        return s;
    }

    public static Vector unitVector(Vector v) {
        Vector s = new Vector(v);
        double length = s.length();
        s.divideByFactor(length);
        return s;
    }

    /**
     * Vector multiplication function to get the total of two vectors being
     * multiplied by one another.
     *
     * @param u
     *            {@code Vector} to be multiplied.
     * @param v
     *            {@code Vector} to be multiplied.
     * @return double containing the sum of all vector multiplications.
     */
    public static double dot(Vector u, Vector v) {
        double x = u.getX() * v.getX();
        double y = u.getY() * v.getY();
        double z = u.getZ() * v.getZ();
        return x + y + z;
    }

    /**
     * Calculates the cross-product of two vectors.
     *
     * @param u
     *            {@code Vector} to be multiplied.
     * @param v
     *            {@code Vector} to be multiplied.
     * @return {@code Vector} a reference to a new Vector object containing the
     *         cross-product result of the two Vector arguments passed in.
     */
    public static Vector crossProduct(Vector u, Vector v) {
        double x = (u.getY() * v.getZ()) - (u.getZ() * v.getY());
        double y = (u.getZ() * v.getX()) - (u.getX() * v.getZ());
        double z = (u.getX() * v.getY()) - (u.getY() * v.getX());
        return new Vector(x, y, z);
    }

    /**
     * Writes pixel color values in format "r g b" to file.
     *
     * @param out
     *            {@code PrintWriter} to write to.
     * @param c
     *            {@code Color} containing pixel color information.
     */
    public static void writeColor(PrintWriter out, Color c, int samplesPerPixel) {
    	
    	double r = c.getX();
    	double g = c.getY();
    	double b = c.getZ();
    	
    	// Divide the color by the number of samples ad gamma-correct for gamma = 2.0.
    	double scale = 1.0 / samplesPerPixel;
    	r = Math.sqrt(scale * r);
    	g = Math.sqrt(scale * g);
    	b = Math.sqrt(scale * b);
    	
    	// Write the translated [0, 255] value of each color component.
    	out.print((int) (256 * Utility.clamp(r, 0.0, 0.999)) + " ");
    	out.print((int) (256 * Utility.clamp(g, 0.0, 0.999)) + " ");
    	out.print((int) (256 * Utility.clamp(b, 0.0, 0.999)));
    	out.println();
    	out.flush();
    	
    }
    
    // Constants

    public static final double INFINITY = Double.POSITIVE_INFINITY;
    public static final double PI = Math.PI;

    // Utility Functions

    public static double degreesToRadians(double degrees) {
        return degrees * PI / 180.0;
    }
    
    public static double random() {
    	return Math.random();
    }
    
    public static double getRandom(double min, double max) {
    	return min + (max - min) * random();
    }
    
    public static double clamp(double x, double min, double max) {
    	if (x < min) return min;
    	if (x > max) return max;
    	return x;
    }

}
