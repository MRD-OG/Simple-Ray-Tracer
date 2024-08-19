public class Light extends Shape {

	protected Vector position = new Vector(0, 0, 0);
	protected Vector colour = new Vector(0, 0, 0);
	protected Vector direction;
	protected double brightness = 1;
	protected double softness = 0;
	protected double radius = 0;
	
	public Vector getLight(Scene scene, Vector point) {
		
		
		// cast shadow ray
		Ray shadowRay;
		
		Vector rayDirection;
		
		if (this.direction != null)
			rayDirection = this.direction.mul(-1).add(Vector.randomVector(scene).mul(this.softness));
		else {
			// calculate point on sphere surface
			
			// TODO
			// Calculate a random point on the point light's surface
			double distance = this.position.sub(point).mod();
			double l = this.radius * Math.sqrt(distance * distance - this.radius*this.radius) / distance;

			Vector randomVector = Vector.randomVector(scene).mul(scene.getRandom() * l);
			Vector normal = point.sub(this.position).getUnit().sub(randomVector).getUnit();

			// add a bit of bias
			Vector pointOnSurface = this.position.add(normal.mul(this.radius + 0.0000001));
			
			rayDirection = pointOnSurface.sub(point);
		}
			
		
		double distance = 0;
		double brightness = 0;
		
		shadowRay = new Ray(point, rayDirection.getUnit());
		Vector lightToPoint = point.sub(this.position);
		
		if (scene.getIntersection(shadowRay).distance >= lightToPoint.mod() /* HERE  MAY NEED A TWEAK*/ ) {

			// does the shadow ray intersect anything in the scene?
			
			if (this.direction != null) {
				// directional light
				distance = lightToPoint.dot(this.direction.getUnit());
			} else {
				// point light
				distance = Math.abs(lightToPoint.mod() - this.radius);
			}
			
			if (distance < 0)
				return new Vector(0, 0, 0);
			
			double intermediate = (distance * distance)/ (this.brightness * this.brightness);
			brightness = 1 / ( 1 + intermediate);
		}
		
		
		return this.colour.mul(brightness);			
	}
	
	@Override
	public Intersection getIntersection(Ray ray) {
		
		if (this.direction == null)
			return new Intersection();
		
		double x, y, xy, root1, root2;
        Vector dif = ray.position.sub(this.position);

        x = -dif.dot(ray.direction);
        y = dif.dot(dif) - this.radius * this.radius;

        xy = x*x - y;

        if (xy > 0) {
            xy = Math.sqrt(xy);

            // get the smallest positive first root - (root 1 is always closer so prioritise its computation)
            root1 = x - xy;
            if (root1 >= 0) return new Intersection(this, ray.getAt(root1), ray.position, ray.direction);
            root2 = x + xy;
            if (root2 >= 0) return new Intersection(this, ray.getAt(root2), ray.position, ray.direction);
        }

        return new Intersection();
		
	}
	
	@Override
	public Vector getNormal(Scene scene, Vector point, Vector incidence) {
		return null;
	}

	@Override
	public Vector correctPosition(Vector point, Vector normal) {
		
		// get the vector to the surface of the shape
		Vector centerToPoint = point.sub(this.position).getUnit().mul(this.radius);
		// add to the center of the shape, offset by a small amount of the normal vector
		return this.position.add(centerToPoint).add(normal.mul(0.001));
	}
	
}