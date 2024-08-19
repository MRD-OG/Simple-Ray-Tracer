public abstract class Shape {

	protected Vector position;
	protected Material material;
	
	public Shape() {
		
	}
	
	public abstract Intersection getIntersection(Ray ray);

	public abstract Vector getNormal(Scene scene, Vector point, Vector incidence);
	
	public abstract Vector correctPosition(Vector point, Vector normal);
}