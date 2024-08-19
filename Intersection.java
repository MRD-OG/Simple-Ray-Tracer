public class Intersection {
	
	public double distance = Double.POSITIVE_INFINITY;
	public Vector point, rayPosition, rayDirection;
	public Shape shape;

	public Intersection() {
		this.shape = null;
		this.point = null;
		this.rayPosition = null;
		this.rayDirection = null;
	}

	public Intersection(Shape shape, Vector point, Vector rayPosition, Vector rayDirection) {
		this.shape = shape;
		this.point = point;
		this.rayPosition = rayPosition;
		this.rayDirection = rayDirection;
		
		this.distance = point.sub(rayPosition).mod();
	}

}