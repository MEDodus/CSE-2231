package raytracer.components.examples;

public class Color extends Vector {

    /*
     * Note about class hierarchy. This class in my opinion is unecessary,
     * however, for sake of my understanding going through and learning about
     * ray tracing I have left it in as a stepping stone towards my
     * understanding. The Vector class features all necessary functionality for
     * Point3D and Color objects.
     */
    public Color() {
        super();
    }

    public Color(double x, double y, double z) {
        super(x, y, z);
    }

    public Color(Color c) {
        super(c);
    }

    public Color multiplyBy(double factor) {
    	return new Color(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }
    
    public Color add(Vector v) {
    	Color u = new Color();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() + v.getX());
    		u.setY(this.getY() + v.getY());
    		u.setZ(this.getZ() + v.getZ());
    	}
    	return u;
    }
    
    public Color mult(Vector v) {
    	Color u = new Color();
    	for (int i = 0; i < this.size(); i++) {
    		u.setX(this.getX() * v.getX());
    		u.setY(this.getY() * v.getY());
    		u.setZ(this.getZ() * v.getZ());
    	}
    	return u;
    }
}
