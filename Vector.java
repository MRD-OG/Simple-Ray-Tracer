import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

public class Vector {

	public double x, y, z;
	
	public static ArrayList<Vector> randomVectors = new ArrayList<>();
	
	public static void randomise() {
		
        Random r = new Random();

        for (int i = 0; i < 10000; i++) {
            try {
                randomVectors.add(new Vector(r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5).getUnit());
            } catch (ArithmeticException a) {
                // zero division
                randomVectors.add(new Vector(0.1, r.nextDouble() - 0.5, r.nextDouble() - 0.5).getUnit());
            }
        }
		
	}
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}
	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}
	public Vector mul(double v) {
		return new Vector(x * v, y * v, z * v);
	}
	public Vector hadamard(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}
	public Vector project(Vector n) {
		// assume normal vector is always a unit vector
		return this.sub(n.mul(this.dot(n)));
	}
	public Vector reflect(Vector normal) {
		return this.sub(normal.mul(this.dot(normal) * 2)).getUnit();
	}
	public Vector cross(Vector v) {
		return new Vector (
			this.y * v.z - this.z * v.y,
			this.z * v.x - this.x * v.z,
			this.x * v.y - this.y * v.x
		);
	}
	public double dot(Vector v) {
		return x * v.x + y * v.y + z * v.z;
	}
	public double mod() {
		return Math.sqrt(this.dot(this));
	}
	public double modSquared() {
		return this.dot(this);
	}
	public Vector getUnit() {
		return this.mul(1 / this.mod());
	}
	public int toColour() {
		return new Color((float) x, (float) y, (float) z).getRGB();
	}
	public Vector gammaCorrect(double v) {
		return new Vector(Math.pow(x, 1 / v), Math.pow(y, 1 / v), Math.pow(z, 1 / v));
	}
	public static Vector randomVector(Scene scene) {
        int index = (int) (scene.getRandom() * randomVectors.size());
        return randomVectors.get(index);		
	}
    public float[] getUV() {
        float u, v;
        u = (float) (0.5 + Math.atan2(z, x) / (2 * Math.PI));
        v = (float) (0.5 + Math.asin(y) / Math.PI);

        return new float[]{u, v};
    }
    public Vector max(Vector v) {
        return new Vector(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
    }
    public Vector lerp(Vector v, double t) {
        return mul(1 - t).add(v.mul(t));
    }
}