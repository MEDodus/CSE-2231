package raytracer.components.examples;

public class HitRecord {

	public Point3D p;
	public Vector normal;
	public Material mat;
	public double t;
	public boolean frontFace;

	public HitRecord() {
		this.p = new Point3D();
		this.normal = new Vector();
		this.t = 0.0;
		this.frontFace = false;
	}

	public HitRecord(Point3D p, Vector normal, double t) {
		this.p = new Point3D(p);
		this.normal = new Vector(normal);
		this.t = t;
	}

	public HitRecord(HitRecord rec) {
		this.p = new Point3D(rec.p);
		this.normal = new Vector(rec.normal);
		this.t = rec.t;
		this.frontFace = rec.frontFace;
	}

	public void printP() {
		System.out.println("POINT3D: X: " + this.p.getX() + " Y: " + this.p.getY() + " Z: " + this.p.getZ());
	}

	public void printNormal() {
		System.out.println(
				"NORMAL: X: " + this.normal.getX() + " Y: " + this.normal.getY() + " Z: " + this.normal.getZ());
	}

	public void printT() {
		System.out.println("T: " + this.t);
	}

	public void setFaceNormal(final Ray r, final Vector outwardNormal) {
		this.frontFace = Utility.dot(r.direction(), outwardNormal) < 0;
		if (this.frontFace) {
			this.normal = new Vector(outwardNormal);
		} else {
			outwardNormal.multiplyByFactor(-1);
			this.normal = new Vector(outwardNormal);
		}
	}
}
