public class Camera {

    Vector position = new Vector(0, 0, 0);

    Vector forward;
    Vector up;
    Vector right;

    Vector rotatedForward;
    Vector rotatedUp;
    Vector rotatedRight;

    double sensorRadius = 0.1;
    double focalLength = 6;

	public Camera() {
        forward = new Vector(0, 0, 1);
        right = new Vector(1, 0 ,0);
        up = new Vector(0, 1, 0); // */

        /*
        forward = new Vector(0, 1, 0);
        right = new Vector(1, 0 ,0);
        up = new Vector(0, 0, -1); // */

        rotatedForward = forward;
        rotatedRight = right;
        rotatedUp = up;
	}


    public Ray generateRay(Environment e, Scene s, double x, double y, boolean depthOfField, double width, double height) {

        double u = (x / width - 0.5);
        double v = - (y / height - 0.5) * (height / width);

        Vector viewPlanePoint = this.position.add(rotatedForward);

        // add a little randomness for anti-aliasing
        double xUnit = 1 / width;
        double yUnit = 1 / height;

        double xRandom = xUnit * (s.getRandom() - 0.5);
        double yRandom = yUnit * (s.getRandom() - 0.5);

        // create point on view plane
        viewPlanePoint = viewPlanePoint.add(rotatedRight.mul(u + xRandom));
        viewPlanePoint = viewPlanePoint.add(rotatedUp.mul(v + yRandom));
        viewPlanePoint = viewPlanePoint.mul(focalLength);

        Vector pos = position;
        Vector dir = viewPlanePoint.sub(pos).getUnit();

		
        // account for depth of field
        if (depthOfField) {
            pos = Vector.randomVector(s).project(rotatedForward).mul(s.getRandom() * sensorRadius);
            dir = viewPlanePoint.sub(pos).getUnit();
        }

        return new Ray(pos, dir);

    }

}