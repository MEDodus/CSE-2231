package raytracer.components.examples;

public class Sphere extends Hittable {

    public Point3D center;
    public double radius;
    public Material mat;

    public Sphere(Point3D center, double r, Material m) {
        this.center = new Point3D(center);
        this.radius = r;
        this.rec = new HitRecord(this.center, new Vector(), r);
        this.mat = m;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord rec) {
        // r.origin() - center
        Vector oc = new Vector(r.origin());
        oc.subtractVector(this.center);

        double a = r.direction().lengthSqr();
        double halfB = Utility.dot(oc, r.direction());
        double c = oc.lengthSqr() - (this.radius * this.radius);

        double discriminant = (halfB * halfB) - (a * c);

        if (discriminant < 0) {
            return false;
        }

        double discriminantSqrt = Math.sqrt(discriminant);

        // Find he nearest root that is in range
        double root = (-halfB - discriminantSqrt) / a;

        if (root < tMin || tMax < root) {
            root = (-halfB + discriminantSqrt) / a;
            if (root < tMin || tMax < root) {
                return false;
            }
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vector outwardNormal = new Point3D(rec.p).minus(this.center).minus(this.radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.mat = this.mat; // Change this so reference is not passed but obj value is changed.

        return true;
    }

}
