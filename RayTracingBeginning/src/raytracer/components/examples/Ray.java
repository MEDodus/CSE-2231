package raytracer.components.examples;

public class Ray {

    private Point3D orig;
    private Vector dir;

    public Ray(Point3D origin, Vector direction) {
        this.orig = origin;
        this.dir = direction;
    }

    public Ray(Ray r) {
        this.orig = new Point3D(r.origin());
        this.dir = new Vector(r.direction());
    }

    public Point3D origin() {
        return this.orig;
    }

    public Vector direction() {
        return this.dir;
    }

    public Point3D at(double t) {
        Vector dirCop = new Vector(this.dir);
        dirCop.multiplyByFactor(t);
        Point3D origCop = new Point3D(this.orig);
        return Utility.addVectors(origCop, dirCop);
    }
    
    public void setOrigin(Point3D o) {
    	this.orig.setX(o.getX());
    	this.orig.setY(o.getY());
    	this.orig.setZ(o.getZ());
    }
    
    public void setDirection(Vector v) {
    	this.dir.setX(v.getX());
    	this.dir.setY(v.getY());
    	this.dir.setZ(v.getZ());
    }

}
