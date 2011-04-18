import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PistolImage {
	BufferedImage leftPistol, rightPistol;
	public PistolImage()
	{ 
		try {
			java.net.URL imageURL = PistolImage.class
					.getResource("/image/leftPistol.jpg");
			leftPistol = ImageIO.read(imageURL);
		} catch (IOException ioe) {System.out.println("Error: Failed to load image.");
		}
		try {
			java.net.URL imageURL = PistolImage.class
					.getResource("/image/rightPistol.jpg");
			rightPistol = ImageIO.read(imageURL);
		} catch (IOException ioe) {System.out.println("Error: Failed to load image.");
		}
	}
	public BufferedImage getLeftPistol() {
		return leftPistol;
	}
	public BufferedImage getRightPistol() {
		return rightPistol;
	}
}
