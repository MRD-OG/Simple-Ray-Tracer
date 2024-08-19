public class Ray {
	
	public Vector position, direction;
	
	public Ray(Vector position, Vector direction) {
		this.position = position;
		this.direction = direction;
	}
	
	public Vector getAt(double t) {
	
		return position.add(direction.mul(t));
	
	}
	
	public Vector trace(Environment environment, Scene scene, int recursionsLeft) {
		
		if (recursionsLeft == 0)
			return new Vector(1, 1, 1);
		
		Intersection intersection = scene.getIntersection(this);
		
		if (intersection.shape != null) {
			
			if (intersection.shape instanceof Light)
				return ((Light) intersection.shape).colour;
			
			// calculate the reflected ray
			// calculate the prop
			
			Vector normal = intersection.shape.getNormal(scene, intersection.point, intersection.rayDirection);
			Vector reflectedPoint = intersection.shape.correctPosition(intersection.point, normal);
			Vector reflectedDirection = intersection.rayDirection.getUnit().reflect(normal);
			
			
			// create the reflected ray
			Ray reflectedRay = new Ray(reflectedPoint, reflectedDirection);
			
			// trace the reflected ray
			Vector reflectedColour = reflectedRay.trace(environment, scene, recursionsLeft - 1);
			
			double reflectance = intersection.shape.material.calculateReflectance(Math.min(Math.max(0, this.direction.mul(-1).dot(normal)), 1));
			
			//return reflectedColour;
			Vector surfaceColour = intersection.shape.material.colour.mul(1 - reflectance);
			Vector reflectionColour = reflectedColour.mul(reflectance);
			
			Vector lightingAtPoint = scene.calculateLighting(reflectedPoint);
			
			return surfaceColour.hadamard(lightingAtPoint).add(reflectionColour).max(surfaceColour.mul(0.1));
		}
		
		return environment.getSkyColour(this.direction);
	}

}