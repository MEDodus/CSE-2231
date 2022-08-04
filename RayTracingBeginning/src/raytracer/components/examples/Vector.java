package raytracer.components.examples;

import java.util.ArrayList;
import java.util.List;

public class Vector {

    private List<Double> list;

    public Vector() {
        this.list = new ArrayList<>();
        this.list.add(0.0); // x
        this.list.add(0.0); // y
        this.list.add(0.0); // z
    }

    public Vector(double x, double y, double z) {
        this.list = new ArrayList<>();
        this.list.add(x);
        this.list.add(y);
        this.list.add(z);
    }

    public Vector(Vector v) {
        this.list = new ArrayList<>();
        this.list.add(v.getX());
        this.list.add(v.getY());
        this.list.add(v.getZ());
    }

    public double getX() {
        return this.list.get(0);
    }

    public double getY() {
        return this.list.get(1);
    }

    public double getZ() {
        return this.list.get(2);
    }

    public double getValue(int idx) {
        return this.list.get(idx);
    }

    public List<Double> getXYZ() {
        return this.list;
    }

    public void setX(double set) {
        this.list.set(0, set);
    }

    public void setY(double set) {
        this.list.set(1, set);
    }

    public void setZ(double set) {
        this.list.set(2, set);
    }

    public void addVector(Vector v) {
        double xPlus = this.list.get(0) + v.getXYZ().get(0);
        double yPlus = this.list.get(1) + v.getXYZ().get(1);
        double zPlus = this.list.get(2) + v.getXYZ().get(2);
        this.list.set(0, xPlus);
        this.list.set(1, yPlus);
        this.list.set(2, zPlus);
    }

    public void subtractVector(Vector v) {
        double xSub = this.list.get(0) - v.getXYZ().get(0);
        double ySub = this.list.get(1) - v.getXYZ().get(1);
        double zSub = this.list.get(2) - v.getXYZ().get(2);
        this.list.set(0, xSub);
        this.list.set(1, ySub);
        this.list.set(2, zSub);
    }

    public void substractT(double t) {
        double xSub = this.list.get(0) - t;
        double ySub = this.list.get(1) - t;
        double zSub = this.list.get(2) - t;
        this.list.set(0, xSub);
        this.list.set(1, ySub);
        this.list.set(2, zSub);
    }

    public void multiplyVector(Vector v) {
        double xTimes = this.list.get(0) * v.getXYZ().get(0);
        double yTimes = this.list.get(1) * v.getXYZ().get(1);
        double zTimes = this.list.get(2) * v.getXYZ().get(2);
        this.list.set(0, xTimes);
        this.list.set(1, yTimes);
        this.list.set(2, zTimes);
    }

    public void multiplyByFactor(double t) {
        this.list.set(0, this.list.get(0) * t);
        this.list.set(1, this.list.get(1) * t);
        this.list.set(2, this.list.get(2) * t);
    }

    public void divideByFactor(double t) {
        this.list.set(0, this.list.get(0) / t);
        this.list.set(1, this.list.get(1) / t);
        this.list.set(2, this.list.get(2) / t);
    }

    public double length() {
        return Math.sqrt(this.lengthSqr());
    }

    public double lengthSqr() {
        double x = this.list.get(0) * this.list.get(0);
        double y = this.list.get(1) * this.list.get(1);
        double z = this.list.get(2) * this.list.get(2);
        return x + y + z;
    }

    public int size() {
        return this.list.size();
    }
    
    public Vector add(Vector v) {
    	Vector u = new Vector();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() + v.getX());
    		u.setY(this.getY() + v.getY());
    		u.setZ(this.getZ() + v.getZ());
    	}
    	return u;
    }
    
    public Vector minus(Vector v) {
    	Vector u = new Vector();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() - v.getX());
    		u.setY(this.getY() - v.getY());
    		u.setZ(this.getZ() - v.getZ());
    	}
    	return u;
    }
    
    public Vector mult(Vector v) {
    	Vector u = new Vector();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() * v.getX());
    		u.setY(this.getY() * v.getY());
    		u.setZ(this.getZ() * v.getZ());
    	}
    	return u;
    }
    
    public Vector div(Vector v) {
    	Vector u = new Vector();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() / v.getX());
    		u.setY(this.getY() / v.getY());
    		u.setZ(this.getZ() / v.getZ());
    	}
    	return u;
    }
    
    public Vector divideBy(double factor) {
    	return new Vector(this.getX() / factor, this.getY() / factor, this.getZ() / factor);
    }
    
    public Vector multiplyBy(double factor) {
    	return new Vector(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }
    
    public static Vector random() {
    	return new Vector(Utility.random(), Utility.random(), Utility.random());
    }
    
    public static Vector random(double min, double max) {
    	return new Vector(Utility.getRandom(min, max), Utility.getRandom(min, max), Utility.getRandom(min, max));
    }
    
    public static Vector randomInUnitSphere() {
    	while (true) {
    		Vector p = Vector.random(-1, 1);
    		if (p.lengthSqr() >= 1) continue;
    		return p;
    	}
    }
    
    public static Vector randomUnitVector() {
    	return Utility.unitVector(randomInUnitSphere());
    }

    public static Vector randomInHemisphere(final Vector normal) {
    	Vector inUnitSphere = Vector.randomInUnitSphere();
    	if (Utility.dot(inUnitSphere, normal) > 0.0) { // In the same hemisphere as the normal
    		return inUnitSphere;
    	} else {
    		return inUnitSphere.multiplyBy(-1);
    	}
    }
    
    public boolean nearZero() {
    	// Return true if the vector is close to zero in all dimensions.
    	final double s = 1e-8;
    	return (Math.abs(this.getX()) < s) && (Math.abs(this.getY()) < s) && (Math.abs(this.getZ()) < s);
    }
    
    public static Vector reflect(final Vector v, final Vector n) {
    	return v.minus(n.multiplyBy(Utility.dot(v, n) * 2));
    }
}
