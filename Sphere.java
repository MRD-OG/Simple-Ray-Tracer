public class Sphere extends Shape {
	
	protected double radius;

	public Sphere() {
	
	
	}
	
	@Override
	public Intersection getIntersection(Ray ray) {
		
		double x, y, xy, root1, root2;
        Vector dif = ray.position.sub(this.position);

        x = -dif.dot(ray.direction);
        y = dif.dot(dif) - this.radius * this.radius;

        xy = x*x - y;

        if (xy >= 0) {
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
		
		Vector normal = point.sub(this.position).getUnit();
		
		// if surface is approached from behind
		if (normal.dot(incidence) > 0)
			normal = normal.mul(-1);
		
		
		// calculate a random normal vector: unit(n - random) - to prevent 0 vectors or 0 division
		Vector randomNormal = normal.sub(Vector.randomVector(scene).mul(0.999)).getUnit();
		
		// lerp between the true normal (n) and the random vector based on roughness value
		Vector calculatedNormal = normal.lerp(randomNormal, this.material.roughness).getUnit();
		
		return calculatedNormal;
	}

	@Override
	public Vector correctPosition(Vector point, Vector normal) {
		
		// get the vector to the surface of the shape
		Vector centerToPoint = point.sub(this.position).getUnit().mul(this.radius);
		// add to the center of the shape, offset by a small amount of the normal vector
		return this.position.add(centerToPoint).add(normal.mul(0.001));
		
	}


}
