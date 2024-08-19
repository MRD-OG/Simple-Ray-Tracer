import java.util.ArrayList;
import java.util.Random;

public class Scene {
	
	ArrayList<Shape> sceneShapes = new ArrayList<>();
	Random random = new Random(0);

	public Scene() {
	
	}

	public void addShape(Shape shape) {
		sceneShapes.add(shape);
	}

	public void removeShape(Shape shape) {
		sceneShapes.remove(shape);
	}
	
	public double getRandom() {
		return random.nextDouble();
	}
	
	public void resetRandom() {
		random.setSeed(0);
	}
	
	public Intersection getIntersection(Ray ray) {
		
		Intersection closestIntersection = new Intersection();
		
		for (Shape shape : sceneShapes) {
			
			Intersection currentIntersection = shape.getIntersection(ray);
			
			
			if (currentIntersection.distance < closestIntersection.distance)
				closestIntersection = currentIntersection;
		}
		
		return closestIntersection;
		
	}
	
	public Vector calculateLighting(Vector point) {
		
		Vector lighting = new Vector(0, 0, 0);
		
			
		for (Shape shape : sceneShapes) {
				
			if (shape instanceof Light) {
				
				// convert shape to light
				Light light = (Light) shape;
						
				// get max light value at this point
				lighting = lighting.max(light.getLight(this, point));
				
			}
		}

		
		return lighting;
		
	}

}