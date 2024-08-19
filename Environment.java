import java.awt.image.BufferedImage;
import java.awt.Color;

public class Environment {
	
	BufferedImage skybox;
	Vector skyColourOne, skyColourTwo;
	Vector directionalLightColour, directionalLightDirection, directionalLightPosition;
	double directionalLightBrighntess, directionalLightSoftness;

	public Environment() {
		
		skybox = null;
		skyColourOne = new Vector(1, 1, 1);
		skyColourTwo = new Vector(1, 0, 0);
		
		//skyColourOne = new Vector(0, 0, 0.01);
		//skyColourTwo = new Vector(0, 0, 0.01);
		
		directionalLightColour = new Vector(1, 1, 1);
		directionalLightPosition = new Vector(3, 3, 6);
		directionalLightDirection = new Vector(-1, -1, -1);
		directionalLightBrighntess = 30;
		directionalLightSoftness = 0.5;
		
	}
	
	public Vector calculateLighting(Scene scene, Vector point) {
		
		// cast shadow rays
		Ray shadowRay = new Ray(point, directionalLightDirection.mul(-1).add(Vector.randomVector(scene).mul(directionalLightSoftness)).getUnit());
		if (scene.getIntersection(shadowRay).distance == Double.POSITIVE_INFINITY) {
			
			double distance = point.sub(directionalLightPosition).dot(directionalLightDirection.getUnit());
			
			if (distance < 0)
				return new Vector(0, 0, 0);
			
			double intermediate = distance / directionalLightBrighntess;
			double brightness = 1 / ( 1 + intermediate * intermediate);
			
			return directionalLightColour.mul(brightness);
			
		}
		
		return new Vector(0, 0, 0);
		
		
	}
	
    public Vector getSkyColour(Vector direction) {
        if (skybox != null) {
            float[] uv = direction.getUV();
            Color col = new Color(skybox.getRGB((int) (uv[0] * (skybox.getWidth() - 1)), skybox.getHeight() - 1 - (int) ((uv[1]) * (skybox.getHeight() - 1))));
            return new Vector(col.getRed() / 255.0, col.getGreen() / 255.0, col.getBlue() / 255.0);
        } else {
            double t = Math.abs(direction.y);
            return skyColourOne.lerp(skyColourTwo, t);
        }
    }

}