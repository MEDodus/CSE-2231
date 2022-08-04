package raytracer.components.examples;

import java.util.ArrayList;
import java.util.List;

public class HittableList {

    public List<Hittable> objects;

    public HittableList() {
        this.objects = new ArrayList<>();
    }

    public HittableList(Hittable object) {
        this.objects.add(object);
    }

    public void add(Hittable object) {
        this.objects.add(object);
    }

    public int size() {
        return this.objects.size();
    }

    public Hittable get(int idx) {
        return this.objects.get(idx);
    }

    public HitRecord getHitRecord(int idx) {
        return this.objects.get(idx).rec;
    }

    public boolean hit(final Ray r, double tMin, double tMax, HitRecord rec) {

        boolean hitAnything = false;
        double closest = tMax;

        for (Hittable object : this.objects) {
            HitRecord temp = new HitRecord(object.rec);
            
            if (object.hit(r, tMin, closest, temp)) {
            	hitAnything = true;
            	closest = temp.t;
            	rec.p = new Point3D(temp.p);
            	rec.t = temp.t;
            	rec.normal = new Vector(temp.normal);
            	rec.mat = temp.mat;
            }
        }
        return hitAnything;
    }
}
