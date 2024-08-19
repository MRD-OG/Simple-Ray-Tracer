import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Renderer {

	Environment environment;
	Scene scene;
	
	int iteration = 0;
	int width, height;
	int maxDepth = 5;
	
	Vector[][] pixels;
	
	Camera camera = new Camera();

	public Renderer() {
		
	}
	
	public void setDimensions(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
		
		pixels = new Vector[this.width][this.height];
		
		for (int x = 0; x < this.width; x++) {
				
			for (int y = 0; y < this.height; y++) {
				
				pixels[x][y] = new Vector(0, 0, 0);
				
			}
		}
	}
	
	public void setEnvironment(Environment newEnvironment) {
		this.environment = newEnvironment;
	}
	
	public void setScene(Scene newScene) {
		this.scene = newScene;
	}
	
	public void iterate() {
		
		iteration++;
		
		for (int x = 0; x < width; x++) {
			
			for (int y = 0; y < height; y++) {
				// generate rays
                Ray ray = camera.generateRay(environment, scene, x, y, false, width, height);

                // trace each ray
                Vector tracedColour = ray.trace(environment, scene, maxDepth);

				// add the colour to the pixels array, if the iteration is 0, 
				//		the base colour in the array is multiplied by 0
				Vector currentPixelColour = pixels[x][y].mul(Math.min(1, iteration - 1));
                pixels[x][y] = currentPixelColour.add(tracedColour.gammaCorrect(2));
			}
			
		}
		
	}
	
	public int getIteration() {
		return this.iteration;
	}
	
	public BufferedImage callback() {
		
		
        BufferedImage returnImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                returnImage.setRGB(x, y, pixels[x][y].mul(1f / iteration).toColour());
            }
        }

        return returnImage;
	}

}