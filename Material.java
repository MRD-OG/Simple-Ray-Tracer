public class Material {
	
	public Vector colour;
	public double roughness;
	public double indexOfRefraction;
	public boolean isDielectric = false;
	public double reflectance;

	public Material() {
	
	
	
	}

    public double calculateReflectance(double cosTheta) {
		
		if (roughness == 1) {
			
			return reflectance / Math.PI;
			
		}
		
		if (!isDielectric)
			return this.reflectance;
		
        // Using Schlick's approximation.
        double r = (1 - indexOfRefraction) / (1 + indexOfRefraction);
        r *= r;
        return r + (1 - r) * Math.pow((1 - cosTheta), 5);
    
	}

}