package raytracer.components.examples;

public class Point3D extends Vector {

    /*
     * Note about class hierarchy. This class in my opinion is unecessary,
     * however, for sake of my understanding going through and learning about
     * ray tracing I have left it in as a stepping stone towards my
     * understanding. The Vector class features all necessary functionality for
     * Point3D and Color objects.
     */
    public Point3D() {
        super();
    }

    public Point3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Point3D(Point3D p) {
        super(p);
    }
    
    public Point3D divideBy(double factor) {
    	return new Point3D(this.getX() / factor, this.getY() / factor, this.getZ() / factor);
    }
    
    public Point3D minus(Vector v) {
    	Point3D u = new Point3D();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() - v.getX());
    		u.setY(this.getY() - v.getY());
    		u.setZ(this.getZ() - v.getZ());
    	}
    	return u;
    }
    
    public Point3D add(Vector v) {
    	Point3D u = new Point3D();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() + v.getX());
    		u.setY(this.getY() + v.getY());
    		u.setZ(this.getZ() + v.getZ());
    	}
    	return u;
    }

	public Point3D minus(double factor) {
		Point3D u = new Point3D();
		for (int i = 0; i < this.size(); i++) {
			u.setX(this.getX() - factor);
			u.setY(this.getY() - factor);
			u.setZ(this.getZ() - factor);
		}
		return u;
	}
}
