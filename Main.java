import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Main {

	public static void main(String[] args) {
		
		int imageWidth = 800;
		int imageHeight = 800;
		
		JFrame frame = new JFrame("Ray Tracer");
		frame.setBounds(100, 100, imageWidth + 16, imageHeight + 40);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Vector.randomise();
		
		// create an environment
		Environment environment = new Environment();
		
		Light light1 = new Light();
		light1.position = new Vector(1, 1, 6);
		light1.colour = new Vector(1, 1, 1);
		//light1.direction = new Vector(-1, -1, -1);
		//light1.softness = 0.5;
		light1.brightness = 5;
		light1.radius = 0.1;


		Light light2 = new Light();
		light2.position = new Vector(-3, 3, 6);
		light2.colour = new Vector(0, 0, 1);
		light2.direction = new Vector(1, -1, -1);
		light2.softness = 0.5;
		light2.brightness = 50;
		
		// create a scene
		Scene scene = new Scene();
		
		// create a renderer
		Renderer renderer = new Renderer();
		
		
		scene.addShape(light1);
		//scene.addShape(light2);
		
		// create materials
		Material whiteMirror = new Material();
		whiteMirror.colour = new Vector(1, 1, 1);
		whiteMirror.roughness = 0;
		whiteMirror.reflectance = 0.9;
		
		
		Material wallMaterial = new Material();
		wallMaterial.colour = new Vector(1, 1, 1);
		wallMaterial.roughness = 1;
		wallMaterial.reflectance = 0.5;
		
		Material yellowLambertian = new Material();
		yellowLambertian.colour = new Vector(1, 1, 0);
		yellowLambertian.roughness = 1;
		yellowLambertian.indexOfRefraction = 1.52;
		yellowLambertian.reflectance = 0.5;
		
		Material greenLambertian = new Material();
		greenLambertian.colour = new Vector(0, 1, 0);
		greenLambertian.roughness = 1;
		greenLambertian.reflectance = 0.7;
		
		Material blueGlass = new Material();
		blueGlass.colour = new Vector(0, 0, 1);
		blueGlass.roughness = 0;
		blueGlass.indexOfRefraction = 1.52;
		blueGlass.isDielectric = true;
		
		// create shapes
		Sphere sphere = new Sphere();
		sphere.position = new Vector(-1, 0, 5);
		sphere.radius = 0.5;
		sphere.material = blueGlass;
		
		Sphere sphere3 = new Sphere();
		sphere3.position = new Vector(0, 0.5, 6);
		sphere3.radius = 0.5;
		sphere3.material = whiteMirror;
		
		Sphere sphere4 = new Sphere();
		sphere4.position = new Vector(1, 0, 7);
		sphere4.radius = 0.5;
		sphere4.material = greenLambertian;
		
		// WALLS
		
		Sphere left = new Sphere();
		left.position = new Vector(103, 0, 0);
		left.radius = 99.5;
		left.material = wallMaterial;
		
		Sphere right = new Sphere();
		right.position = new Vector(-103, 0, 0);
		right.radius = 99.5;
		right.material = wallMaterial;
		
		Sphere ceiling = new Sphere();
		ceiling.position = new Vector(0, 103, 0);
		ceiling.radius = 99.5;
		ceiling.material = wallMaterial;
		
		Sphere floor = new Sphere();
		floor.position = new Vector(0, -100, 0);
		floor.radius = 99.5;
		floor.material = yellowLambertian;
		
		Sphere back = new Sphere();
		back.position = new Vector(0, 0, 112);
		back.radius = 99.5;
		back.material = wallMaterial;
	
		// add shapes to scene
		scene.addShape(sphere);
		//scene.addShape(sphere3);
		scene.addShape(sphere4);

		scene.addShape(left);
		scene.addShape(right);
		scene.addShape(floor);
		scene.addShape(ceiling);
		scene.addShape(back);

	
		// give environment and scene to the renderer
		renderer.setEnvironment(environment);
		renderer.setScene(scene);
		renderer.setDimensions(imageWidth, imageHeight);
		
		
		// iterate the renderer
		// get image from renderer callback function
		
			
		while (true) {
			
			renderer.iterate();
			frame.setTitle("Ray Tracer - Iteration " + renderer.getIteration());
			displayImage(frame, renderer.callback());
			
			try {
				Thread.sleep(50);
			} catch (Exception ignored) {
				
			}	
			
		}
		
	}
	
	
    private static void displayImage(JFrame f, BufferedImage image) {
        Graphics g = f.getGraphics();
        g.drawImage(image, 8, 32, f);
    }

}